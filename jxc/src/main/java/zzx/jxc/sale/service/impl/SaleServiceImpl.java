package zzx.jxc.sale.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import zzx.jxc.VO.SaleFoodSelectListVO;
import zzx.jxc.dto.SaleOrderDTO;
import zzx.jxc.foodCategory.dao.FoodCategoryDao;
import zzx.jxc.foodCategory.entity.FoodCategory;
import zzx.jxc.foodInfo.dao.FoodInfoDao;
import zzx.jxc.foodInfo.entity.FoodInfo;
import zzx.jxc.foodStock.Dao.FoodStockDao;
import zzx.jxc.foodStock.entity.FoodStock;
import zzx.jxc.sale.dao.SaleDao;
import zzx.jxc.sale.entity.SaleDetail;
import zzx.jxc.sale.entity.SaleMaster;
import zzx.jxc.sale.service.SaleService;
import zzx.jxc.stock.dao.StockDao;
import zzx.jxc.stock.entity.Stock;

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
    private FoodStockDao foodStockDao;
    @Autowired
    private FoodInfoDao foodInfoDao;
    @Autowired
    private FoodCategoryDao foodCategoryDao;
    @Autowired
    private StockDao stockDao;

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
        //1. 查询商品 ( 数量,价格)
        for (SaleDetail saleDetail : saleOrderDTO.getSaleDetailList()) {

        }
        //2.计算总价

        //3.写入销售订单数据库( SaleMaster 和 SaleDetail)

        //4.扣库存
        return null;
    }

    @Override
    public SaleOrderDTO findOne(String saleId) {
        return null;
    }

    @Override
    public Page<SaleMaster> findList(SaleMaster saleMaster, Pageable pageable) {
        return null;
    }

    @Override
    public SaleOrderDTO cancel(SaleOrderDTO saleOrderDTO) {
        return null;
    }

    @Override
    public SaleOrderDTO finish(SaleOrderDTO saleOrderDTO) {
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
        Page<Stock> stockList = stockDao.findAll(example, pageable);   //找到仓库id 来查所有食品
        List<String> collectStockIDList = stockList.stream().map(Stock::getStockId).collect(Collectors.toList());
//        List<FoodStock> allByStockIdIn = foodStockDao.findAllByStockIdIn(collectStockIDList); //所有的有库存的食品list
        List<SaleFoodSelectListVO> allSaleFoodSelectListVO = new ArrayList<>();
        for (String stockId:collectStockIDList) {
            Page<FoodStock> allByStockPage = foodStockDao.findAllByStockId(stockId, pageable);
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
                Integer foodStock = foodStockDao.findFoodStockByFoodIdAndStockId(foodInfo.getFoodId(), stockId);
                saleFoodSelectListVO.setStock(foodStock);
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
