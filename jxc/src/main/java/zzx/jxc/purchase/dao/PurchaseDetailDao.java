package zzx.jxc.purchase.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.purchase.entity.PurchaseDetail;

import java.util.List;

public interface PurchaseDetailDao extends JpaRepository<PurchaseDetail,String> {
    List<PurchaseDetail> findAllByPurchaseId(String purchaseId);
}
