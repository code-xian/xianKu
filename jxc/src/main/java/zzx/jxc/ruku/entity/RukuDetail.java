package zzx.jxc.ruku.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
@Entity
@Data
public class RukuDetail {
    @Id
    private String detailId;
    private String rukuId;
    private String foodId;
    private Integer foodQuantity;
    private String detailRemarks;
    private String foodName;
    private String stockName;
    private String stockId;
    private BigDecimal foodPrice;
    private BigDecimal detailPrice;
}
