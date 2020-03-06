package zzx.jxc.fahuo.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class FahuoMaster {

    @Id
    private String fahuoId;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String fahuoWay;
    private String fahuoRemarks;
    private Integer fahuoStaus;
    private String reviewer;  //审核人
    private Date fahuoDate;
    private String sourceOrder;
    private String fahuoType;
    private Date createTime;
    private Date updateTime;
    private BigDecimal purchaseAmount;
    private Date submissionDate;

}
