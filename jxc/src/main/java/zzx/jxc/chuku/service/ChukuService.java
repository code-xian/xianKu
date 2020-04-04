package zzx.jxc.chuku.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.ChukuOrderInfoVO;
import zzx.jxc.chuku.entity.ChukuMaster;
import zzx.jxc.fahuo.entity.FahuoMaster;

public interface ChukuService {
    //查询今日的订单数,以便添加订单编号
    Integer countByChukuIdLike();

    //创建订单
    void create(FahuoMaster fahuoMaster);

    //查询单个订单
    ChukuOrderInfoVO findOne(String chukuId);

    //查询订单列表
    Page<ChukuMaster> findList(ChukuMaster chukuMaster, Pageable pageable);

    //订单未通过审核
    void cancel(String chukuId);

    //订单通过审核
    void finish(String chukuId);

    //生成日志
    void createLog(String rukuId);
}
