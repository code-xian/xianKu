package zzx.jxc.sale.entity;

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
public class SaleMaster {

    @Id
    private String saleId;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private Date submissionDate;
    private String submissionWay;
    private Integer storeId;   //供应门店id
    private String storeFzr;   //门店负责人
    private String reviewer;
    private String saleRemarks;
    private Integer saleStatus = OrderStatusEnum.UNAUDITED.getCode();
    private BigDecimal purchaseAmount;
    private Date createTime;
    private Date updateTime;

}
