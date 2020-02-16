package zzx.jxc.foodInfo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class FoodInfo {

    @Id
    private String foodId;
    private String foodName;
    private BigDecimal foodPrice;
    private Integer foodStock;
    private String foodDescription;
    private String categoryId;
    private Integer shelfLife;
    private String stockId;
    private Date createTime;
    private Date updateTime;
}
