package zzx.jxc.VO;

import lombok.Data;
import zzx.jxc.purchase.entity.PurchaseDetail;

import java.util.List;
@Data
public class PurchaseOrderInfoVO {
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private String purchaseRemarks;
    private String supplierFzr;
    private String submissionWay;
    private String submissionDate;
    private List<PurchaseDetail> detailList;
}
