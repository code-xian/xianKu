package zzx.jxc.purchase.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import zzx.jxc.enums.OrderStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class PurchaseMaster {
    @Id
    private String purchaseId;
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private String stockName;
    private String purchaseRemarks;
    private Integer purchaseStatus = OrderStatusEnum.UNAUDITED.getCode();;
    private Date submissionDate;
    private String submissionWay;
    private String reviewer;
    private BigDecimal purchaseAmount;
    private Integer supplierId;
    private String supplierFzr;
    private String supplierType;
    private Date createTime;
    private Date updateTime;



}
