package zzx.jxc.purchase.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzx.jxc.VO.PurchaseFoodSelectListVO;
import zzx.jxc.VO.PurchaseOrderInfoVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.dto.PurchaseOrderDTO;
import zzx.jxc.enums.OrderStatusEnum;
import zzx.jxc.enums.ResultEnum;
import zzx.jxc.exception.SellException;
import zzx.jxc.foodCategory.dao.FoodCategoryDao;
import zzx.jxc.foodCategory.entity.FoodCategory;
import zzx.jxc.foodInfo.dao.FoodInfoDao;
import zzx.jxc.foodInfo.entity.FoodInfo;
import zzx.jxc.foodInfo.service.FoodInfoService;
import zzx.jxc.foodStock.service.FoodStockService;
import zzx.jxc.purchase.dao.PurchaseDao;
import zzx.jxc.purchase.dao.PurchaseDetailDao;
import zzx.jxc.purchase.entity.PurchaseDetail;
import zzx.jxc.purchase.entity.PurchaseMaster;
import zzx.jxc.purchase.service.PurchaseService;
import zzx.jxc.shouhuo.service.ShouhuoService;
import zzx.jxc.stock.service.StockService;
import zzx.jxc.supplier.entity.Supplier;
import zzx.jxc.supplier.service.SupplierService;
import zzx.jxc.util.DetailKeyUtil;
import zzx.jxc.util.ddbhUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private FoodInfoService foodInfoService;
    @Autowired
    private StockService stockService;
    @Autowired
    private PurchaseDetailDao purchaseDetailDao;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private FoodStockService foodStockService;
    @Autowired
    private FoodInfoDao foodInfoDao;
    @Autowired
    private FoodCategoryDao foodCategoryDao;
    @Autowired
    private ShouhuoService shouhuoService;

    @Override
    public Integer countByPurchaseIdLike() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String timeStr = format.format(date);
        Integer purchaseMasterCount = purchaseDao.countByPurchaseIdLike("%" + timeStr + "%");
        return purchaseMasterCount;
    }

    @Override
    @Transactional
    public PurchaseOrderDTO create(PurchaseOrderDTO purchaseOrderDTO) {
        String cgdd = ddbhUtil.xsdd(countByPurchaseIdLike(), "CGDD");
        BigDecimal orderAmount = new BigDecimal(0);

        //1. 查询商品 ( 数量,价格)
        for (OrderCartDTO orderCartDTO : purchaseOrderDTO.getPurchaseDetailList()) {
            FoodInfo oneById = foodInfoService.findOneById(orderCartDTO.getFoodId());
            if (oneById == null) {
                throw new SellException(ResultEnum.FOOD_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount = oneById.getPurchasePrice()
                    .multiply(new BigDecimal(orderCartDTO.getSaleQuantity()))
                    .add(orderAmount);
            //订单详情入库
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            BeanUtils.copyProperties(oneById, purchaseDetail);
            purchaseDetail.setDetailRemarks(oneById.getFoodDescription());
            purchaseDetail.setPurchaseId(cgdd);
            purchaseDetail.setDetailId(DetailKeyUtil.genUniqueKey());
            purchaseDetail.setStockId(orderCartDTO.getStockId());
            purchaseDetail.setFoodQuantity(orderCartDTO.getSaleQuantity());
            purchaseDetail.setFoodPrice(oneById.getPurchasePrice());
            purchaseDetail.setStockName(stockService.findOneById(orderCartDTO.getStockId()).getStockName());
            purchaseDetail.setDetailPrice(oneById.getPurchasePrice().multiply(new BigDecimal(orderCartDTO.getSaleQuantity())));
            purchaseDetailDao.save(purchaseDetail);
        }

        //3.写入销售订单数据库( SaleMaster 和 SaleDetail)
        PurchaseMaster purchaseMaster = new PurchaseMaster();
        Supplier supplier = supplierService.findOneById(purchaseOrderDTO.getSupplierId());
        BeanUtils.copyProperties(supplier, purchaseOrderDTO);
        BeanUtils.copyProperties(purchaseOrderDTO, purchaseMaster);
        purchaseMaster.setPurchaseId(cgdd);
        purchaseMaster.setPurchaseAmount(orderAmount);
        PurchaseMaster save = purchaseDao.save(purchaseMaster);
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_CREATE_FAIL);
        }
        return purchaseOrderDTO;
    }

    @Override
    public PurchaseOrderInfoVO findOne(String purchaseId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        PurchaseOrderInfoVO purchaseOrderInfoVO = new PurchaseOrderInfoVO();
        PurchaseMaster purchaseMasterByPurchaseId = purchaseDao.findPurchaseMasterByPurchaseId(purchaseId);
        Supplier supplier = supplierService.findOneById(purchaseMasterByPurchaseId.getSupplierId());
        BeanUtils.copyProperties(supplier, purchaseOrderInfoVO);
        purchaseOrderInfoVO.setSubmissionWay(purchaseMasterByPurchaseId.getSubmissionWay());
        String dateString = formatter.format(purchaseMasterByPurchaseId.getSubmissionDate());
        purchaseOrderInfoVO.setSubmissionDate(dateString);
        purchaseOrderInfoVO.setPurchaseRemarks(purchaseMasterByPurchaseId.getPurchaseRemarks());
        List<PurchaseDetail> allByPurchaseId = purchaseDetailDao.findAllByPurchaseId(purchaseId);
        purchaseOrderInfoVO.setDetailList(allByPurchaseId);
        return purchaseOrderInfoVO;
    }

    @Override
    public Page<PurchaseMaster> findList(PurchaseMaster purchaseMaster, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("purchaseId", ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("supplierName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("purchaseStatus", ExampleMatcher.GenericPropertyMatchers.contains());
//                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<PurchaseMaster> example = Example.of(purchaseMaster, exampleMatcher);
        Page<PurchaseMaster> purchaseDaoAll = purchaseDao.findAll(example, pageable);
        return purchaseDaoAll;
    }

    @Override
    public void cancel(PurchaseOrderDTO purchaseOrderDTO) {
        //判断订单状态
        PurchaseMaster purchaseMasterByPurchaseId = purchaseDao.findPurchaseMasterByPurchaseId(purchaseOrderDTO.getPurchaseId());
        if (!purchaseMasterByPurchaseId.getPurchaseStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        purchaseMasterByPurchaseId.setPurchaseStatus(OrderStatusEnum.AUDIT_NO_PASS.getCode());
        PurchaseMaster save = purchaseDao.save(purchaseMasterByPurchaseId);
        //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
    }

    @Override
    public PurchaseOrderDTO finish(PurchaseOrderDTO purchaseOrderDTO) {
        //判断订单状态
        PurchaseMaster purchaseMasterByPurchaseId = purchaseDao.findPurchaseMasterByPurchaseId(purchaseOrderDTO.getPurchaseId());
        if (!purchaseMasterByPurchaseId.getPurchaseStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        purchaseMasterByPurchaseId.setPurchaseStatus(OrderStatusEnum.AUDIT_PASS.getCode());
        PurchaseMaster save = purchaseDao.save(purchaseMasterByPurchaseId);; //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
        shouhuoService.create(purchaseMasterByPurchaseId);
        return null;
    }

    @Override
    public Page<PurchaseFoodSelectListVO> findListAll(Pageable pageable, String categoryId, String foodName) {
        FoodInfo foodInfo = new FoodInfo();
        foodInfo.setCategoryId(categoryId);
        foodInfo.setFoodName(foodName);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("foodName",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("categoryId",ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("foodStatus").withIgnorePaths("purchasePrice");//isFace字段不参与匹配
        //创建实例
        Example<FoodInfo> example = Example.of(foodInfo,exampleMatcher);
        List<FoodInfo> foodInfoList = foodInfoDao.findAll(example);
        List<PurchaseFoodSelectListVO> purchaseFoodSelectListVOS = new ArrayList<>();
        for (FoodInfo foodInfo1 : foodInfoList) {
            PurchaseFoodSelectListVO purchaseFoodSelectListVO = new PurchaseFoodSelectListVO();
            BeanUtils.copyProperties(foodInfo1, purchaseFoodSelectListVO);
            FoodCategory foodCategoryByCategoryId = foodCategoryDao.findFoodCategoryByCategoryId(foodInfo1.getCategoryId());
            purchaseFoodSelectListVO.setCategoryName(foodCategoryByCategoryId.getCategoryName());
            purchaseFoodSelectListVOS.add(purchaseFoodSelectListVO);
        }
        int size = purchaseFoodSelectListVOS.size();

        Page<PurchaseFoodSelectListVO> allList = new PageImpl<PurchaseFoodSelectListVO>(purchaseFoodSelectListVOS, pageable, size);
        return allList;
    }
}
