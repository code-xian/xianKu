package zzx.jxc.ruku.entity;

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
public class RukuMaster {
    @Id
    private String rukuId;
    private String orderType;
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private String supplierFzr;
    private BigDecimal rukuMoney;
    private String rukuRemarks;
    private Integer rukuStatus = OrderStatusEnum.UNAUDITED.getCode();;
    private Date rukuDate;
    private String sourceOrder;
    private Date createTime;
    private Date updateTime;
    private String shouhuoWay;
    private String reviewer;
}
