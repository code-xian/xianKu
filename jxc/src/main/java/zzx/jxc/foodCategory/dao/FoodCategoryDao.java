package zzx.jxc.foodCategory.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.foodCategory.entity.FoodCategory;

public interface FoodCategoryDao extends JpaRepository<FoodCategory,String> {
    long count();
    FoodCategory findFoodCategoryByCategoryId(String CategoryId);
}
