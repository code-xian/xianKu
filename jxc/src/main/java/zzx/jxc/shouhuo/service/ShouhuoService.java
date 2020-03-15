package zzx.jxc.shouhuo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.FahuoOrderInfoVO;
import zzx.jxc.fahuo.entity.FahuoMaster;
import zzx.jxc.purchase.entity.PurchaseMaster;

public interface ShouhuoService {
    //查询今日的订单数,以便添加订单编号
    Integer countByShouhuoIdLike();

    //创建订单
    void create(PurchaseMaster purchaseMaster);

    //查询单个订单
    FahuoOrderInfoVO findOne(String fahuoId);

    //查询订单列表
    Page<FahuoMaster> findList(FahuoMaster fahuoMaster, Pageable pageable);

    //订单未通过审核
    void cancel(String fahuoId);

    //订单通过审核
    void finish(String fahuoId);

}
