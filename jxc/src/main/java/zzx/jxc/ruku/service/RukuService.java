package zzx.jxc.ruku.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.RukuOrderInfoVO;
import zzx.jxc.ruku.entity.RukuMaster;
import zzx.jxc.shouhuo.entity.ShouhuoMaster;

public interface RukuService {
    //查询今日的订单数,以便添加订单编号
    Integer countByRukuIdLike();

    //创建订单
    void create(ShouhuoMaster shouhuoMaster);

    //查询单个订单
    RukuOrderInfoVO findOne(String rukuId);

    //查询订单列表
    Page<RukuMaster> findList(RukuMaster rukuMaster, Pageable pageable);

    //订单未通过审核
    void cancel(String rukuId);

    //订单通过审核
    void finish(String rukuId);

    //生成日志
    void createLog(String rukuId);
}
