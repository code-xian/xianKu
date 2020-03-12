package zzx.jxc.chuku.entity;


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
public class ChukuMaster {
    @Id
    private String chukuId;
    private String orderType;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String storeFzr;
    private BigDecimal chukuMoney;
    private String chukuRemarks;
    private Integer chukuStatus = OrderStatusEnum.UNAUDITED.getCode();;
    private Date chukuDate;
    private String sourceOrder;
    private Date createTime;
    private Date updateTime;
    private String fahuoWay;
    private String reviewer;
}
