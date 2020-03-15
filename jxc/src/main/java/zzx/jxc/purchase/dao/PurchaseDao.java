package zzx.jxc.purchase.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.purchase.entity.PurchaseMaster;

public interface PurchaseDao extends JpaRepository<PurchaseMaster,String> {
    //两种都能查询类型
    Integer countByPurchaseIdLike(String number);
    Integer countSaleMastersByPurchaseIdLike(String number);

    PurchaseMaster findPurchaseMasterByPurchaseId(String purchaseId);
}
