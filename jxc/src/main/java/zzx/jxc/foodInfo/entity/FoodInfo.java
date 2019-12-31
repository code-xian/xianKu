package zzx.jxc.foodInfo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class FoodInfo {

    @Id
    private String foodId;
    private String foodName;
    private String foodPrice;
    private String foodStock;
    private String foodDescription;
    private String categoryId;
    private String shelfLife;
}
