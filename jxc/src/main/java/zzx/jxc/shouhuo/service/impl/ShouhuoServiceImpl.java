package zzx.jxc.shouhuo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzx.jxc.VO.PurchaseOrderInfoVO;
import zzx.jxc.VO.ShouhuoOrderInfoVO;
import zzx.jxc.enums.OrderStatusEnum;
import zzx.jxc.enums.ResultEnum;
import zzx.jxc.exception.SellException;
import zzx.jxc.purchase.dao.PurchaseDao;
import zzx.jxc.purchase.dao.PurchaseDetailDao;
import zzx.jxc.purchase.entity.PurchaseDetail;
import zzx.jxc.purchase.entity.PurchaseMaster;
import zzx.jxc.purchase.service.PurchaseService;
import zzx.jxc.ruku.service.RukuService;
import zzx.jxc.shouhuo.dao.ShouhuoDao;
import zzx.jxc.shouhuo.dao.ShouhuoDetailDao;
import zzx.jxc.shouhuo.entity.ShouhuoDetail;
import zzx.jxc.shouhuo.entity.ShouhuoMaster;
import zzx.jxc.shouhuo.service.ShouhuoService;
import zzx.jxc.util.DetailKeyUtil;
import zzx.jxc.util.ddbhUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShouhuoServiceImpl implements ShouhuoService {
    @Autowired
    private ShouhuoDao shouhuoDao;
    @Autowired
    private PurchaseDetailDao purchaseDetailDao;
    @Autowired
    private ShouhuoDetailDao shouhuoDetailDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private RukuService rukuService;
    
    @Override
    public Integer countByShouhuoIdLike() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        return shouhuoDao.countByShouhuoIdLike("%" + timeStr + "%");
    }

    @Override
    @Transactional
    public void create(PurchaseMaster purchaseMaster) {
        String shd = ddbhUtil.xsdd(countByShouhuoIdLike(),"SHD");

        //订单详情入库
        for(PurchaseDetail purchaseDetail :purchaseDetailDao.findAllByPurchaseId(purchaseMaster.getPurchaseId())){
            ShouhuoDetail shouhuoDetail = new ShouhuoDetail();
            BeanUtils.copyProperties(purchaseDetail, shouhuoDetail);
            shouhuoDetail.setDetailId(DetailKeyUtil.genUniqueKey());
            shouhuoDetail.setShouhuoId(shd);
            shouhuoDetailDao.save(shouhuoDetail);
        }

        //写入发货单数据库( FahuoMaster)
        ShouhuoMaster shouhuoMaster = new ShouhuoMaster();
        BeanUtils.copyProperties(purchaseDao.findPurchaseMasterByPurchaseId(purchaseMaster.getPurchaseId()),shouhuoMaster,"createTime","updateTime");
        shouhuoMaster.setShouhuoId(shd);
        shouhuoMaster.setShouhuoWay(purchaseDao.findPurchaseMasterByPurchaseId(purchaseMaster.getPurchaseId()).getSubmissionWay());
        shouhuoMaster.setShouhuoRemarks(purchaseDao.findPurchaseMasterByPurchaseId(purchaseMaster.getPurchaseId()).getPurchaseRemarks());
        shouhuoMaster.setSourceOrder(purchaseMaster.getPurchaseId());
        shouhuoDao.save(shouhuoMaster);
    }

    @Override
    public ShouhuoOrderInfoVO findOne(String shouhuoId) {
        ShouhuoOrderInfoVO shouhuoOrderInfoVO = new ShouhuoOrderInfoVO();
        ShouhuoMaster shouhuoMasterByShouhuoId = shouhuoDao.findShouhuoMasterByShouhuoId(shouhuoId);
        PurchaseOrderInfoVO purchaseServiceOne = purchaseService.findOne(shouhuoMasterByShouhuoId.getSourceOrder());
        //        BeanUtils.copyProperties() 直接copy数组会导致泛型错误
        BeanUtils.copyProperties(purchaseServiceOne,shouhuoOrderInfoVO,"detailList");
        List<ShouhuoDetail> shouhuoDetails = new ArrayList();
        for(PurchaseDetail purchaseDetail:purchaseServiceOne.getDetailList()){
            ShouhuoDetail shouhuoDetail = new ShouhuoDetail();
            shouhuoDetail.setDetailPrice(purchaseDetail.getDetailPrice());
            shouhuoDetail.setFoodId(purchaseDetail.getFoodId());
            shouhuoDetail.setFoodQuantity(purchaseDetail.getFoodQuantity());
            shouhuoDetail.setStockId(purchaseDetail.getStockId());
            shouhuoDetail.setFoodName(purchaseDetail.getFoodName());
            shouhuoDetail.setStockName(purchaseDetail.getStockName());
            shouhuoDetail.setDetailRemarks(purchaseDetail.getDetailRemarks());
            shouhuoDetail.setFoodPrice(purchaseDetail.getFoodPrice());
            shouhuoDetails.add(shouhuoDetail);
        }
        shouhuoOrderInfoVO.setDetailList(shouhuoDetails);
        return shouhuoOrderInfoVO;
    }

    @Override
    public Page<ShouhuoMaster> findList(ShouhuoMaster shouhuoMaster, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("shouhuoId",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("supplierName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("shouhuoStatus",ExampleMatcher.GenericPropertyMatchers.contains());
//                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<ShouhuoMaster> example = Example.of(shouhuoMaster,exampleMatcher);
        Page<ShouhuoMaster> shouhuoMasters = shouhuoDao.findAll(example, pageable);
        return shouhuoMasters;
    }

    @Override
    @Transactional
    public void cancel(String shouhuoId) {
        //判断订单状态
        ShouhuoMaster shouhuoMasterByShouhuoId = shouhuoDao.findShouhuoMasterByShouhuoId(shouhuoId);
        if (!shouhuoMasterByShouhuoId.getShouhuoStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        shouhuoMasterByShouhuoId.setShouhuoStatus(OrderStatusEnum.AUDIT_NO_PASS.getCode());
        ShouhuoMaster save = shouhuoDao.save(shouhuoMasterByShouhuoId); //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }

    }

    @Override
    @Transactional
    public void finish(String shouhuoId) {
        //判断订单状态
        ShouhuoMaster shouhuoMasterByShouhuoId = shouhuoDao.findShouhuoMasterByShouhuoId(shouhuoId);
        if (!shouhuoMasterByShouhuoId.getShouhuoStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        shouhuoMasterByShouhuoId.setShouhuoStatus(OrderStatusEnum.AUDIT_PASS.getCode());
        ShouhuoMaster save = shouhuoDao.save(shouhuoMasterByShouhuoId); //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
        rukuService.create(shouhuoMasterByShouhuoId);
    }
}
