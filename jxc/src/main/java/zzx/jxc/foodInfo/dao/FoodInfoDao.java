package zzx.jxc.foodInfo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.foodInfo.entity.FoodInfo;

import java.util.List;

public interface FoodInfoDao extends JpaRepository<FoodInfo,Integer> {
    //两种方法均可以查出食品列表
    List<FoodInfo> findAllByFoodIdIn(List<String> foodIdList);
    List<FoodInfo> findAllByCategoryIdIn(List<String> categoryId);

    FoodInfo findFoodInfoByFoodId(String foodId);
}
