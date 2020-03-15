package zzx.jxc.shouhuo.entity;

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
public class ShouhuoMaster {
    @Id
    private String shouhuoId;
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private String supplierFzr;
    private String shouhuoWay;
    private String shouhuoRemarks;
    private Integer shouhuoStaus = OrderStatusEnum.UNAUDITED.getCode();
    private String reviewer;  //审核人
    private String sourceOrder;
    private String shouhuoType;
    private Date createTime;
    private Date updateTime;
    private BigDecimal purchaseAmount;
    private Date submissionDate;
}
