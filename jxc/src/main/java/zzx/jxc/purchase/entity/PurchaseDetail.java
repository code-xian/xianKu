package zzx.jxc.purchase.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class PurchaseDetail {
    @Id
    private String detailId;
    private String purchaseId;
    private String foodId;
    private String foodName;
    private BigDecimal foodPrice;
    private Integer foodQuantity;
    private String detailRemarks;
    private Integer stockQuantity;
    private BigDecimal detailPrice;
    private String stockName;
    private String stockId;
}
