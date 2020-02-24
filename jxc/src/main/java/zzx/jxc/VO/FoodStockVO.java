package zzx.jxc.VO;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodStockVO {
    private String foodId;
    private String foodName;
    private BigDecimal foodPrice;
    private String foodDescription;
    private String categoryId;
    private Integer shelfLife;
    private Integer stock;
    private String categoryName;
}
