package zzx.jxc.foodCategory.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.foodCategory.entity.FoodCategory;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FoodCategoryDaoTest {

    @Autowired
    private FoodCategoryDao foodCategoryDao;
    @Test
    void count() {
    }

    @Test
    void findFoodCategoryBy() {
        FoodCategory foodCategoryBy = foodCategoryDao.findFoodCategoryByCategoryId("001");
        foodCategoryBy.getCategoryName();
        System.out.println(foodCategoryBy);
    }
}