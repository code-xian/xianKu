package zzx.jxc.VO;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleFoodSelectListVO {
    private String foodId;
    private String foodName;
    private BigDecimal foodPrice;
    private String foodDescription;  //描述
    private String categoryId;
    private Integer shelfLife;
    private Integer stock;     //库存
    private Integer saleQuantity = 0;  //销售数量
    private String categoryName;
    private BigDecimal totalAmount = new BigDecimal(0);
    private String stockName;   //仓库名字
}
