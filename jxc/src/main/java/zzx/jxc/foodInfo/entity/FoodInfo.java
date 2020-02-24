package zzx.jxc.foodInfo.entity;


import lombok.Data;
import zzx.jxc.enums.FoodStatusEnum;

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
    private String foodDescription;
    private String categoryId;
    private Integer shelfLife;
    private Integer foodStatus = FoodStatusEnum.Up.getCode();
    private Date createTime;
    private Date updateTime;
//    @OneToMany( mappedBy="foodInfo")
//    private List<FoodStock> foodStock = new ArrayList<>();

    public FoodInfo() {

    }
}
