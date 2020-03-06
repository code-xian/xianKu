package zzx.jxc.fahuo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class FahuoDetail {

    @Id
    private String detailId;
    private String fahuoId;
    private String foodId;
    private Integer foodQuantity;
    private String detailRemarks;
    private String foodName;
    private String stockName;
    private BigDecimal foodPrice;
    private BigDecimal detailPrice;


}
