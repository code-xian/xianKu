package zzx.jxc.shouhuo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.ShouhuoOrderInfoVO;
import zzx.jxc.purchase.entity.PurchaseMaster;
import zzx.jxc.shouhuo.entity.ShouhuoMaster;

public interface ShouhuoService {
    //查询今日的订单数,以便添加订单编号
    Integer countByShouhuoIdLike();

    //创建订单
    void create(PurchaseMaster purchaseMaster);

    //查询单个订单
    ShouhuoOrderInfoVO findOne(String shouhuoId);

    //查询订单列表
    Page<ShouhuoMaster> findList(ShouhuoMaster shouhuoMaster, Pageable pageable);

    //订单未通过审核
    void cancel(String shouhuoId);

    //订单通过审核
    void finish(String shouhuoId);

}
