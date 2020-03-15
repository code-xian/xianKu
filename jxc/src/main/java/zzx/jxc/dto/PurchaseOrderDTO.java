package zzx.jxc.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseOrderDTO {
    private String purchaseId;
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private Date submissionDate;   //交货期限
    private String submissionWay;
    private Integer supplierId;
    private String supplierFzr;   //门店负责人
    private String reviewer;
    private String purchaseRemarks;
    private String stockId;
    private String supplierType;
    List<OrderCartDTO> purchaseDetailList;
}
