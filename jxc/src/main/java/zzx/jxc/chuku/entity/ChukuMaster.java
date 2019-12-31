package zzx.jxc.chuku.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class ChukuMaster {
    @Id
    private String chukuId;
    private String orderType;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String chukuAbstract;
    private BigDecimal chukuMoney;
    private String chukuRemarks;
    private Integer chukuStatus;
    private Integer totalQuantity;
    private Date chukuDate;
    private String stockName;
    private String sourceOrder;
    private Date createTime;
    private Date updateTime;
}
