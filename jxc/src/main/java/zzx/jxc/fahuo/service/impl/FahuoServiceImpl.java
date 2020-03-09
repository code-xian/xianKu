package zzx.jxc.fahuo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.VO.FahuoOrderInfoVO;
import zzx.jxc.VO.SaleOrderInfoVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.enums.OrderStatusEnum;
import zzx.jxc.enums.ResultEnum;
import zzx.jxc.exception.SellException;
import zzx.jxc.fahuo.dao.FahuoDao;
import zzx.jxc.fahuo.dao.FahuoDetailDao;
import zzx.jxc.fahuo.entity.FahuoDetail;
import zzx.jxc.fahuo.entity.FahuoMaster;
import zzx.jxc.fahuo.service.FahuoService;
import zzx.jxc.foodStock.service.FoodStockService;
import zzx.jxc.sale.dao.SaleDao;
import zzx.jxc.sale.dao.SaleDetailDao;
import zzx.jxc.sale.entity.SaleDetail;
import zzx.jxc.sale.entity.SaleMaster;
import zzx.jxc.sale.service.SaleService;
import zzx.jxc.util.DetailKeyUtil;
import zzx.jxc.util.ddbhUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FahuoServiceImpl implements FahuoService {

    @Autowired
    private FahuoDao fahuoDao;

    @Autowired
    private FahuoDetailDao fahuoDetailDao;

    @Autowired
    private SaleDetailDao saleDetailDao;
    @Autowired
    private SaleDao saleDao;
    @Autowired
    private SaleService saleService;
    @Autowired
    private FoodStockService foodStockService;

    @Override
    public Integer countByFahuoIdLike() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        return fahuoDao.countByFahuoIdLike("%" + timeStr + "%");
    }

    @Override
    public void create(SaleMaster saleMaster) {
        String fhd = ddbhUtil.xsdd(countByFahuoIdLike(),"FHD");

        //订单详情入库
        for(SaleDetail saleDetail :saleDetailDao.findAllBySaleId(saleMaster.getSaleId())){
            FahuoDetail fahuoDetail = new FahuoDetail();
            BeanUtils.copyProperties(saleDetail, fahuoDetail);
            fahuoDetail.setDetailId(DetailKeyUtil.genUniqueKey());
            fahuoDetail.setFahuoId(fhd);
            fahuoDetailDao.save(fahuoDetail);
        }

        //写入发货单数据库( FahuoMaster)
        FahuoMaster fahuoMaster = new FahuoMaster();
        BeanUtils.copyProperties(saleDao.findSaleMasterBySaleId(saleMaster.getSaleId()),fahuoMaster,"createTime","updateTime");
        fahuoMaster.setFahuoId(fhd);
        fahuoMaster.setFahuoWay(saleDao.findSaleMasterBySaleId(saleMaster.getSaleId()).getSubmissionWay());
        fahuoMaster.setFahuoRemarks(saleDao.findSaleMasterBySaleId(saleMaster.getSaleId()).getSaleRemarks());
        fahuoMaster.setSourceOrder(saleMaster.getSaleId());
        fahuoDao.save(fahuoMaster);

    }

    @Override
    public FahuoOrderInfoVO findOne(String fahuoId) {
        FahuoOrderInfoVO fahuoOrderInfoVO = new FahuoOrderInfoVO();
        FahuoMaster fahuoMasterByFahuoId = fahuoDao.findFahuoMasterByFahuoId(fahuoId);
        SaleOrderInfoVO one = saleService.findOne(fahuoMasterByFahuoId.getSourceOrder());
        BeanUtils.copyProperties(one,fahuoOrderInfoVO);
        return fahuoOrderInfoVO;
    }

    @Override
    public Page<FahuoMaster> findList(FahuoMaster fahuoMaster, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("fahuoId",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("storeName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("fahuoStatus",ExampleMatcher.GenericPropertyMatchers.contains());
//                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<FahuoMaster> example = Example.of(fahuoMaster,exampleMatcher);
        Page<FahuoMaster> fahuoMasterList = fahuoDao.findAll(example, pageable);
        return fahuoMasterList;
    }

    @Override
    public void cancel(String fahuoId) {
        //判断订单状态
        FahuoMaster fahuoMasterByFahuoId = fahuoDao.findFahuoMasterByFahuoId(fahuoId);
        if (!fahuoMasterByFahuoId.getFahuoStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        fahuoMasterByFahuoId.setFahuoStatus(OrderStatusEnum.AUDIT_NO_PASS.getCode());
        FahuoMaster save = fahuoDao.save(fahuoMasterByFahuoId); //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
        //审核未通过需要返回库存
        List<OrderCartDTO> collect = findOne(fahuoId).getDetailList().stream().map(e -> new OrderCartDTO(e.getFoodId(), e.getFoodQuantity(), e.getStockId())).collect(Collectors.toList());
        foodStockService.increaseStock(collect);
    }

    @Override
    public void finish(String fahuoId) {
        //判断订单状态
        FahuoMaster fahuoMasterByFahuoId = fahuoDao.findFahuoMasterByFahuoId(fahuoId);
        if (!fahuoMasterByFahuoId.getFahuoStatus().equals(OrderStatusEnum.UNAUDITED.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        fahuoMasterByFahuoId.setFahuoStatus(OrderStatusEnum.AUDIT_PASS.getCode());
        FahuoMaster save = fahuoDao.save(fahuoMasterByFahuoId); //返回更新之后的对象
        if (save == null) {
            throw new SellException(ResultEnum.ORDER_AUDIT_FAIL);
        }
    }
}
