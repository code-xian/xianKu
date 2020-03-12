package zzx.jxc.sale.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzx.jxc.VO.SaleFoodSelectListVO;
import zzx.jxc.VO.SaleOrderInfoVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.dto.SaleOrderDTO;
import zzx.jxc.enums.OrderStatusEnum;
import zzx.jxc.enums.ResultEnum;
import zzx.jxc.exception.SellException;
import zzx.jxc.fahuo.service.FahuoService;
import zzx.jxc.foodCategory.dao.FoodCategoryDao;
import zzx.jxc.foodCategory.entity.FoodCategory;
import zzx.jxc.foodInfo.dao.FoodInfoDao;
import zzx.jxc.foodInfo.entity.FoodInfo;
import zzx.jxc.foodInfo.service.FoodInfoService;
import zzx.jxc.foodStock.Dao.FoodStockDao;
import zzx.jxc.foodStock.entity.FoodStock;
import zzx.jxc.foodStock.service.FoodStockService;
import zzx.jxc.sale.dao.SaleDao;
import zzx.jxc.sale.dao.SaleDetailDao;
import zzx.jxc.sale.entity.SaleDetail;
import zzx.jxc.sale.entity.SaleMaster;
import zzx.jxc.sale.service.SaleService;
import zzx.jxc.stock.dao.StockDao;
import zzx.jxc.stock.entity.Stock;
import zzx.jxc.stock.service.StockService;
import zzx.jxc.store.entity.Store;
import zzx.jxc.store.service.StoreService;
import zzx.jxc.util.DetailKeyUtil;
import zzx.jxc.util.ddbhUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleDao saleDao;
    @Autowired
    private SaleDetailDao saleDetailDao;
    @Autowired
    private FoodStockDao foodStockDao;
    @Autowired
    private FoodInfoDao foodInfoDao;
    @Autowired
    private FoodCategoryDao foodCategoryDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private FoodInfoService foodInfoService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private FoodStockService foodStockService;
    @Autowired
    private StockService stockService;
    @Autowired
    private FahuoService fahuoService;

    @Override
    public Integer countBySaleIdLike() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        Integer saleMasterCount = saleDao.countBySaleIdLike("%" + timeStr + "%");
        return saleMasterCount;
    }

    @Override
    public SaleOrderDTO create(SaleOrderDTO saleOrderDTO) {

        String xsdd = ddbhUtil.xsdd(countBySaleIdLike(),"XSDD");
        BigDecimal orderAmount = new BigDecimal(0);

        //1. 查询商品 ( 数量,价格)
        for (OrderCartDTO orderCartDTO : saleOrderDTO.getSaleDetailList()) {
            FoodInfo oneById = foodInfoService.findOneById(orderCartDTO.getFoodId());
            if (oneById == null) {
                throw new SellException(ResultEnum.FOOD_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount = oneById.getFoodPrice()
                    .multiply(new BigDecimal(orderCartDTO.getSaleQuantity()))
                    .add(orderAmount);
            //订单详情入库
            SaleDetail saleDetail = new SaleDetail();
            BeanUtils.copyProperties(oneById,saleDetail);
            saleDetail.setDetailRemarks(oneById.getFoodDescription());
            saleDetail.setSaleId(xsdd);
            saleDetail.setDetailId(DetailKeyUtil.genUniqueKey());
            saleDetail.setStockId(orderCartDTO.getStockId());
            saleDetail.setFoodQuantity(orderCartDTO.getSaleQuantity());
            saleDetail.setStockName(stockService.findOneById(orderCartDTO.getStockId()).getStockName());
            saleDetail.setDetailPrice(oneById.getFoodPrice() .multiply(new BigDecimal(orderCartDTO.getSaleQuantity())));
            saleDetailDao.save(saleDetail);
        }

        //3.写入销售订单数据库( SaleMaster 和 SaleDetail)
        SaleMaster saleMaster = new SaleMaster();
        Store store = storeService.findOneById(saleOrderDTO.getStoreId());
        BeanUtils.copyProperties(store,saleOrderDTO);
        BeanUtils.copyProperties(saleOrderDTO,saleMaster);
        saleMaster.setSaleId(xsdd);
        saleMaster.setPurchaseAmount(orderAmount);
        saleDao.save(saleMaster);
        return saleOrderDTO;

    }

    @Override
    public SaleOrderInfoVO findOne(String saleId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SaleOrderInfoVO saleOrderInfoVO = new SaleOrderInfoVO();
        SaleMaster saleMasterBySaleId = saleDao.findSaleMasterBySaleId(saleId);
        Store store = storeService.findOneById(saleMasterBySaleId.getStoreId());
        BeanUtils.copyProperties(store, saleOrderInfoVO);
        saleOrderInfoVO.setSubmissionWay(saleMasterBySaleId.getSubmissionWay());
        String dateString = formatter.format(saleMasterBySaleId.getSubmissionDate());
        saleOrderInfoVO.setSubmissionDate(dateString);
        saleOrderInfoVO.setSaleRemarks(saleMasterBySaleId.getSaleRemarks());
        List<SaleDetail> allBySaleId = saleDetailDao.findAllBySaleId(saleId);
        saleOrderInfoVO.setDetailList(allBySaleId);
        return saleOrderInfoVO;
    }

    @Override
    public Page<SaleMaster> findList(SaleMaster saleMaster, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("saleId",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("storeName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("saleStatus",ExampleMatcher.GenericPropertyMatchers.contains());
//                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<SaleMaster> example = Example.of(saleMaster,exampleMatcher);
        Page<SaleMaster> saleMasterList = saleDao.findAll(example, pageable);
        return saleMasterList;
    }

    @Override
    public void cancel(SaleOrderDTO saleOrderDTO) {
        //判断订单状态
        SaleMaster saleMasterBySaleId = saleDao.findSaleMasterBySaleId(saleOrderDTO.getSaleId());
        if (!saleMasterBySaleId.getSaleStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        saleMasterBySaleId.setSaleStatus(OrderStatusEnum.AUDIT_NO_PASS.getCode());
        SaleMaster save = saleDao.save(saleMasterBySaleId); //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
    }

    @Override
    @Transactional
    public SaleOrderDTO finish(SaleOrderDTO saleOrderDTO) throws RuntimeException{
        //判断订单状态
        SaleMaster saleMasterBySaleId = saleDao.findSaleMasterBySaleId(saleOrderDTO.getSaleId());
        if (!saleMasterBySaleId.getSaleStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        saleMasterBySaleId.setSaleStatus(OrderStatusEnum.AUDIT_PASS.getCode());
        SaleMaster save = saleDao.save(saleMasterBySaleId); //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
        //4.扣库存 在审核通过才扣库存
        List<OrderCartDTO> collect = saleOrderDTO.getSaleDetailList().stream().map(e -> new OrderCartDTO(e.getFoodId(), e.getSaleQuantity(), e.getStockId())).collect(Collectors.toList());
        foodStockService.decreaseStock(collect);
        fahuoService.create(saleMasterBySaleId);
        return null;
    }

    @Override
    public Page<SaleFoodSelectListVO> findListAll(String stockName, Pageable pageable, String categoryId, String foodName) {
        Stock stock = new Stock();
        stock.setStockName(stockName);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("stockName",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
//                .withMatcher("stockType",ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<Stock> example = Example.of(stock,exampleMatcher);
        List<Stock> stockList = stockDao.findAll(example);   //找到仓库id 来查所有食品
        List<String> collectStockIDList = stockList.stream().map(Stock::getStockId).collect(Collectors.toList());
//        List<FoodStock> allByStockIdIn = foodStockDao.findAllByStockIdIn(collectStockIDList); //所有的有库存的食品list
        List<SaleFoodSelectListVO> allSaleFoodSelectListVO = new ArrayList<>();
        for (String stockId:collectStockIDList) {
            if (stockDao.findStockByStockId(stockId).getStockStatus()!=0) {  //判断出库是否为废弃仓库
                continue;
            }
            List<FoodStock> allByStockPage = foodStockDao.findAllByStockId(stockId);
            List<String> foodIds = allByStockPage.stream().map(FoodStock::getFoodId).collect(Collectors.toList());
            List<FoodInfo> allByFoodIdIn = foodInfoDao.findAllByFoodIdIn(foodIds);
            List<SaleFoodSelectListVO> saleFoodSelectListVOList = new ArrayList<>();
            for (FoodInfo foodInfo : allByFoodIdIn) {
                if(!categoryId.isEmpty()||!categoryId.equals("")){
                    if (!categoryId.equals(foodInfo.getCategoryId())) {
                        continue;
                    }
                }
                if(!foodName.isEmpty()||!foodName.equals("")){
                    if (!foodInfo.getFoodName().contains(foodName)) {
                        continue;
                    }
                }
                SaleFoodSelectListVO saleFoodSelectListVO = new SaleFoodSelectListVO();
                BeanUtils.copyProperties(foodInfo, saleFoodSelectListVO);
                Integer foodStock = foodStockDao.findStockByFoodIdAndStockId(foodInfo.getFoodId(), stockId);
                saleFoodSelectListVO.setStock(foodStock);
                saleFoodSelectListVO.setStockId(stockId);
                saleFoodSelectListVO.setStockName(stockDao.findStockByStockId(stockId).getStockName());
                FoodCategory foodCategoryByCategoryId = foodCategoryDao.findFoodCategoryByCategoryId(foodInfo.getCategoryId());
                saleFoodSelectListVO.setCategoryName(foodCategoryByCategoryId.getCategoryName());
                saleFoodSelectListVOList.add(saleFoodSelectListVO);
            }
            allSaleFoodSelectListVO.addAll(saleFoodSelectListVOList);
        }
        int size = allSaleFoodSelectListVO.size();

        Page<SaleFoodSelectListVO> allList = new PageImpl<SaleFoodSelectListVO>(allSaleFoodSelectListVO,pageable,size);
        return allList;
    }
}
