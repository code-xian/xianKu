package zzx.jxc.shouhuo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
@Entity
@Data
public class ShouhuoDetail {
    @Id
    private String detailId;
    private String shouhuoId;
    private String foodId;
    private Integer foodQuantity;
    private String detailRemarks;
    private String foodName;
    private String stockName;
    private String stockId;
    private BigDecimal foodPrice;
    private BigDecimal detailPrice;
}
