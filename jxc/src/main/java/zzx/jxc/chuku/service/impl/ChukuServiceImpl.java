package zzx.jxc.chuku.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzx.jxc.VO.ChukuOrderInfoVO;
import zzx.jxc.VO.FahuoOrderInfoVO;
import zzx.jxc.chuku.dao.ChukuDao;
import zzx.jxc.chuku.dao.ChukuDetailDao;
import zzx.jxc.chuku.entity.ChukuDetail;
import zzx.jxc.chuku.entity.ChukuMaster;
import zzx.jxc.chuku.service.ChukuService;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.enums.OrderStatusEnum;
import zzx.jxc.enums.ResultEnum;
import zzx.jxc.exception.SellException;
import zzx.jxc.fahuo.dao.FahuoDao;
import zzx.jxc.fahuo.dao.FahuoDetailDao;
import zzx.jxc.fahuo.entity.FahuoDetail;
import zzx.jxc.fahuo.entity.FahuoMaster;
import zzx.jxc.fahuo.service.FahuoService;
import zzx.jxc.foodStock.Dao.FoodStockDao;
import zzx.jxc.foodStock.service.FoodStockService;
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
public class ChukuServiceImpl implements ChukuService {

    @Autowired
    private ChukuDao chukuDao;
    @Autowired
    private ChukuDetailDao chukuDetailDao;
    @Autowired
    private FahuoDetailDao fahuoDetailDao;
    @Autowired
    private FahuoDao fahuoDao;
    @Autowired
    private FahuoService fahuoService;
    @Autowired
    private FoodStockService foodStockService;
    @Autowired
    private FoodStockDao foodStockDao;
    @Autowired
    private LogDao logDao;
    @Override
    public Integer countByChukuIdLike() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        return chukuDao.countByChukuIdLike("%" + timeStr + "%");
    }

    @Override
    public void create(FahuoMaster fahuoMaster) {
        String ckd = ddbhUtil.xsdd(countByChukuIdLike(),"CKD");
        String fahuoId = fahuoMaster.getFahuoId();
        //订单详情入库
        for(FahuoDetail fahuoDetail :fahuoDetailDao.findAllByFahuoId(fahuoId)){
            ChukuDetail chukuDetail = new ChukuDetail();
            BeanUtils.copyProperties(fahuoDetail, chukuDetail);
            chukuDetail.setDetailId(DetailKeyUtil.genUniqueKey());
            chukuDetail.setChukuId(ckd);
            chukuDetailDao.save(chukuDetail);
        }
        FahuoMaster fahuoMasterByFahuoId = fahuoDao.findFahuoMasterByFahuoId(fahuoId);
        //写入出库单数据库( ChukuMaster)
        ChukuMaster chukuMaster = new ChukuMaster();
//        BeanUtils.copyProperties() 直接copy数组会导致泛型错误
        BeanUtils.copyProperties(fahuoMasterByFahuoId,chukuMaster,"createTime","updateTime");
        chukuMaster.setChukuId(ckd);
        chukuMaster.setFahuoWay(fahuoMasterByFahuoId.getFahuoWay());
        chukuMaster.setChukuRemarks(fahuoMasterByFahuoId.getFahuoRemarks());
        chukuMaster.setSourceOrder(fahuoMaster.getFahuoId());
        chukuMaster.setChukuDate(fahuoMasterByFahuoId.getSubmissionDate());
        chukuMaster.setChukuMoney(fahuoMasterByFahuoId.getPurchaseAmount());
        chukuDao.save(chukuMaster);
    }

    @Override
    public ChukuOrderInfoVO findOne(String chukuId) {
        ChukuOrderInfoVO chukuOrderInfoVO = new ChukuOrderInfoVO();
        ChukuMaster chukuMasterByChukuId = chukuDao.findChukuMasterByChukuId(chukuId);
        FahuoOrderInfoVO one = fahuoService.findOne(chukuMasterByChukuId.getSourceOrder());
        BeanUtils.copyProperties(one,chukuOrderInfoVO ,"detailList");
        List<ChukuDetail> chukuDetails = new ArrayList();
        for(FahuoDetail fahuoDetail:one.getDetailList()){
            ChukuDetail chukuDetail = new ChukuDetail();
            chukuDetail.setDetailPrice(fahuoDetail.getDetailPrice());
            chukuDetail.setFoodId(fahuoDetail.getFoodId());
            chukuDetail.setFoodQuantity(fahuoDetail.getFoodQuantity());
            chukuDetail.setStockId(fahuoDetail.getStockId());
            chukuDetail.setFoodName(fahuoDetail.getFoodName());
            chukuDetail.setStockName(fahuoDetail.getStockName());
            chukuDetail.setDetailRemarks(fahuoDetail.getDetailRemarks());
            chukuDetail.setFoodPrice(fahuoDetail.getFoodPrice());
            chukuDetails.add(chukuDetail);
        }
        chukuOrderInfoVO.setDetailList(chukuDetails);
        return chukuOrderInfoVO;
    }

    @Override
    public Page<ChukuMaster> findList(ChukuMaster chukuMaster, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("chukuId",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("storeName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("chukuStatus",ExampleMatcher.GenericPropertyMatchers.contains());
//                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<ChukuMaster> example = Example.of(chukuMaster,exampleMatcher);
        Page<ChukuMaster> chukuMasterList = chukuDao.findAll(example, pageable);
        return chukuMasterList;
    }

    @Override
    @Transactional
    public void cancel(String chukuId) {
        //判断订单状态
        ChukuMaster chukuMasterByChukuId = chukuDao.findChukuMasterByChukuId(chukuId);
        if (!chukuMasterByChukuId.getChukuStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        chukuMasterByChukuId.setChukuStatus(OrderStatusEnum.AUDIT_NO_PASS.getCode());
        ChukuMaster save = chukuDao.save(chukuMasterByChukuId);
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
        //审核未通过需要返回库存
        ChukuOrderInfoVO one = findOne(chukuId);
        List<OrderCartDTO> collect = one.getDetailList().stream().map(e -> new OrderCartDTO(e.getFoodId(), e.getFoodQuantity(), e.getStockId())).collect(Collectors.toList());
        foodStockService.increaseStock(collect);
    }

    @Override
    public void finish(String chukuId) {
        //判断订单状态
        ChukuMaster chukuMasterByChukuId = chukuDao.findChukuMasterByChukuId(chukuId);
        if (!chukuMasterByChukuId.getChukuStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        chukuMasterByChukuId.setChukuStatus(OrderStatusEnum.AUDIT_PASS.getCode());
        ChukuMaster save = chukuDao.save(chukuMasterByChukuId);
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
        createLog(chukuId);
    }

    @Override
    public void createLog(String chukuId) {
        ChukuOrderInfoVO one = findOne(chukuId);
        for (ChukuDetail chukuDetail : one.getDetailList()) {
            StockLog stockLog = new StockLog();
            stockLog.setFoodName(chukuDetail.getFoodName());
            stockLog.setFoodId(chukuDetail.getFoodId());
            stockLog.setStockId(chukuDetail.getStockId());
            stockLog.setStockName(chukuDetail.getStockName());
            stockLog.setDocumentNumber(chukuId);
            stockLog.setDocumentType(2);
            stockLog.setIncOrDec("减少");
            stockLog.setQuantity(chukuDetail.getFoodQuantity());
            stockLog.setRestStock(foodStockDao.findStockByFoodIdAndStockId(chukuDetail.getFoodId(), chukuDetail.getStockId()));
            logDao.save(stockLog);
        }
    }
}
