package zzx.jxc.foodCategory.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class FoodCategory {

    @Id
    private String categoryId;
    private String categoryName;
    private String stockId;
}
