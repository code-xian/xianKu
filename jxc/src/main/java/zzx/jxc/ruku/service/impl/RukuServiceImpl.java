package zzx.jxc.ruku.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzx.jxc.VO.RukuOrderInfoVO;
import zzx.jxc.VO.ShouhuoOrderInfoVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.enums.OrderStatusEnum;
import zzx.jxc.enums.ResultEnum;
import zzx.jxc.exception.SellException;
import zzx.jxc.foodStock.Dao.FoodStockDao;
import zzx.jxc.foodStock.service.FoodStockService;
import zzx.jxc.ruku.dao.RukuDao;
import zzx.jxc.ruku.dao.RukuDetailDao;
import zzx.jxc.ruku.entity.RukuDetail;
import zzx.jxc.ruku.entity.RukuMaster;
import zzx.jxc.ruku.service.RukuService;
import zzx.jxc.shouhuo.dao.ShouhuoDao;
import zzx.jxc.shouhuo.dao.ShouhuoDetailDao;
import zzx.jxc.shouhuo.entity.ShouhuoDetail;
import zzx.jxc.shouhuo.entity.ShouhuoMaster;
import zzx.jxc.shouhuo.service.ShouhuoService;
import zzx.jxc.stockLog.Dao.LogDao;
import zzx.jxc.stockLog.entity.StockLog;
import zzx.jxc.util.DetailKeyUtil;
import zzx.jxc.util.ddbhUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RukuServiceImpl implements RukuService {
    @Autowired
    private RukuDao rukuDao;
    @Autowired
    private RukuDetailDao rukuDetailDao;
    @Autowired
    private ShouhuoService shouhuoService;
    @Autowired
    private ShouhuoDao shouhuoDao;
    @Autowired
    private ShouhuoDetailDao shouhuoDetailDao;
    @Autowired
    private FoodStockService foodStockService;
    @Autowired
    private FoodStockDao foodStockDao;
    @Autowired
    private LogDao logDao;


    @Override
    public Integer countByRukuIdLike() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        return rukuDao.countByRukuIdLike("%" + timeStr + "%");
    }

    @Override
    @Transactional
    public void create(ShouhuoMaster shouhuoMaster) {
        String rkd = ddbhUtil.xsdd(countByRukuIdLike(),"RKD");
        String shouhuoId = shouhuoMaster.getShouhuoId();
        //订单详情入库
        for(ShouhuoDetail shouhuoDetail :shouhuoDetailDao.findAllByShouhuoId(shouhuoId)){
            RukuDetail rukuDetail = new RukuDetail();
            BeanUtils.copyProperties(shouhuoDetail, rukuDetail);
            rukuDetail.setDetailId(DetailKeyUtil.genUniqueKey());
            rukuDetail.setRukuId(rkd);
            rukuDetailDao.save(rukuDetail);
        }
        ShouhuoMaster shouhuoMasterByShouhuoId = shouhuoDao.findShouhuoMasterByShouhuoId(shouhuoId);
        //写入出库单数据库( ChukuMaster)
        RukuMaster rukuMaster = new RukuMaster();
//        BeanUtils.copyProperties() 直接copy数组会导致泛型错误
        BeanUtils.copyProperties(shouhuoMasterByShouhuoId,rukuMaster,"createTime","updateTime");
        rukuMaster.setRukuId(rkd);
        rukuMaster.setShouhuoWay(shouhuoMasterByShouhuoId.getShouhuoWay());
        rukuMaster.setRukuRemarks(shouhuoMasterByShouhuoId.getShouhuoRemarks());
        rukuMaster.setSourceOrder(shouhuoMaster.getShouhuoId());
        rukuMaster.setRukuDate(shouhuoMasterByShouhuoId.getSubmissionDate());
        rukuMaster.setRukuMoney(shouhuoMasterByShouhuoId.getPurchaseAmount());
        rukuDao.save(rukuMaster);
    }

    @Override
    public RukuOrderInfoVO findOne(String rukuId) {
        RukuOrderInfoVO rukuOrderInfoVO = new RukuOrderInfoVO();
        RukuMaster rukuMasterByRukuId = rukuDao.findRukuMasterByRukuId(rukuId);
        ShouhuoOrderInfoVO one = shouhuoService.findOne(rukuMasterByRukuId.getSourceOrder());
        BeanUtils.copyProperties(one,rukuOrderInfoVO ,"detailList");
        List<RukuDetail> rukuDetails = new ArrayList();
        for(ShouhuoDetail shouhuoDetail:one.getDetailList()){
            RukuDetail rukuDetail = new RukuDetail();
            rukuDetail.setDetailPrice(shouhuoDetail.getDetailPrice());
            rukuDetail.setFoodId(shouhuoDetail.getFoodId());
            rukuDetail.setFoodQuantity(shouhuoDetail.getFoodQuantity());
            rukuDetail.setStockId(shouhuoDetail.getStockId());
            rukuDetail.setFoodName(shouhuoDetail.getFoodName());
            rukuDetail.setStockName(shouhuoDetail.getStockName());
            rukuDetail.setDetailRemarks(shouhuoDetail.getDetailRemarks());
            rukuDetail.setFoodPrice(shouhuoDetail.getFoodPrice());
            rukuDetails.add(rukuDetail);
        }
        rukuOrderInfoVO.setDetailList(rukuDetails);
        return rukuOrderInfoVO;
    }

    @Override
    public Page<RukuMaster> findList(RukuMaster rukuMaster, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("rukuId",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("supplierName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("rukuStatus",ExampleMatcher.GenericPropertyMatchers.contains());
//                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<RukuMaster> example = Example.of(rukuMaster,exampleMatcher);
        Page<RukuMaster> rukuMasters = rukuDao.findAll(example, pageable);
        return rukuMasters;
    }

    @Override
    public void cancel(String rukuId) {
        //判断订单状态
        RukuMaster rukuMasterByRukuId = rukuDao.findRukuMasterByRukuId(rukuId);
        if (!rukuMasterByRukuId.getRukuStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        rukuMasterByRukuId.setRukuStatus(OrderStatusEnum.AUDIT_NO_PASS.getCode());
        RukuMaster save = rukuDao.save(rukuMasterByRukuId);
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
    }

    @Override
    @Transactional
    public void finish(String rukuId) {
        //判断订单状态
        RukuMaster rukuMasterByRukuId = rukuDao.findRukuMasterByRukuId(rukuId);
        if (!rukuMasterByRukuId.getRukuStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        rukuMasterByRukuId.setRukuStatus(OrderStatusEnum.AUDIT_PASS.getCode());
        RukuMaster save = rukuDao.save(rukuMasterByRukuId);
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
        //4.加库存 在审核通过才加库存
        List<OrderCartDTO> collect = findOne(rukuId).getDetailList().stream().map(e -> new OrderCartDTO(e.getFoodId(), e.getFoodQuantity(), e.getStockId())).collect(Collectors.toList());
        foodStockService.increaseStock(collect);
        //生成库存日志
        createLog(rukuId);
    }

    @Override
    public void createLog(String rukuId) {
        RukuOrderInfoVO one = findOne(rukuId);
        for (RukuDetail rukuDetail : one.getDetailList()) {
            StockLog stockLog = new StockLog();
            stockLog.setFoodName(rukuDetail.getFoodName());
            stockLog.setFoodId(rukuDetail.getFoodId());
            stockLog.setStockId(rukuDetail.getStockId());
            stockLog.setStockName(rukuDetail.getStockName());
            stockLog.setDocumentNumber(rukuId);
            stockLog.setDocumentType(1);
            stockLog.setIncOrDec("增加");
            stockLog.setQuantity(rukuDetail.getFoodQuantity());
            stockLog.setRestStock(foodStockDao.findStockByFoodIdAndStockId(rukuDetail.getFoodId(), rukuDetail.getStockId()));
            logDao.save(stockLog);
        }
    }
}
