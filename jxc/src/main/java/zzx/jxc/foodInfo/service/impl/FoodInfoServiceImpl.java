package zzx.jxc.foodInfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.foodInfo.dao.FoodInfoDao;
import zzx.jxc.foodInfo.entity.FoodInfo;
import zzx.jxc.foodInfo.service.FoodInfoService;

import java.util.List;

@Service
public class FoodInfoServiceImpl implements FoodInfoService {

    @Autowired
    private FoodInfoDao foodInfoDao;

    @Override
    public Page<FoodInfo> findAll(FoodInfo foodInfo, Pageable pageable) {

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("foodName",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("categoryId",ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("foodStatus").withIgnorePaths("purchasePrice");//isFace字段不参与匹配
        //创建实例
        Example<FoodInfo> example = Example.of(foodInfo,exampleMatcher);
        Page<FoodInfo> foodInfoList = foodInfoDao.findAll(example, pageable);
        return foodInfoList;
    }

    @Override
    public FoodInfo save(FoodInfo foodInfo) {
        return foodInfoDao.save(foodInfo);
    }

    @Override
    public void delete(FoodInfo foodInfo) {
        foodInfoDao.save(foodInfo);
    }

    @Override
    public List<FoodInfo> findAll() {
        return foodInfoDao.findAll();
    }

    @Override
    public FoodInfo findOneById(String id) {
        return foodInfoDao.findFoodInfoByFoodId(id);
    }

    @Override
    public long count() {
        return foodInfoDao.count();
    }
}
