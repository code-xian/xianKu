package zzx.jxc.foodInfo.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.foodInfo.entity.FoodInfo;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FoodInfoDaoTest {

    @Autowired
    private FoodInfoDao foodInfoDao;
    @Test
    void findByFoodIdIn() {
        List<String> foodList = Arrays.asList("001", "002");
        List<FoodInfo> allByFoodIdIn = foodInfoDao.findAllByFoodIdIn(foodList);
        System.out.println(allByFoodIdIn+"222222222222222222222222");
    }
}