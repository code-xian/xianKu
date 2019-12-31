package zzx.jxc.sale.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class SaleDetail {

    @Id
    private String detailId;
    private String saleId;
    private String foodId;
    private String foodName;
    private BigDecimal foodPrice;
    private Integer foodQuantity;
    private String detailRemarks;
    private Integer stockQuantity;
    private String foodUnit;
    private BigDecimal detailPrice;
    private Date createTime;
    private Date updateTime;
}
