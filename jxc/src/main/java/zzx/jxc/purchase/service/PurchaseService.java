package zzx.jxc.purchase.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.PurchaseFoodSelectListVO;
import zzx.jxc.VO.PurchaseOrderInfoVO;
import zzx.jxc.dto.PurchaseOrderDTO;
import zzx.jxc.purchase.entity.PurchaseMaster;
public interface PurchaseService {
    //查询今日的订单数,以便添加订单编号
    Integer countByPurchaseIdLike();

    //创建订单
    PurchaseOrderDTO create(PurchaseOrderDTO purchaseOrderDTO);

    //查询单个订单
    PurchaseOrderInfoVO findOne(String purchaseId);

    //查询订单列表
    Page<PurchaseMaster> findList(PurchaseMaster purchaseMaster, Pageable pageable);

    //订单未通过审核
    void cancel(PurchaseOrderDTO purchaseOrderDTO);

    //订单通过审核
    PurchaseOrderDTO finish(PurchaseOrderDTO purchaseOrderDTO);

    //查询食品列表
    Page<PurchaseFoodSelectListVO> findListAll( Pageable pageable, String categoryId, String foodName);
}
