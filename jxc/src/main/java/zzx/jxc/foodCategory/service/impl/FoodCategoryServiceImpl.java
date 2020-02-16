package zzx.jxc.foodCategory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.foodCategory.dao.FoodCategoryDao;
import zzx.jxc.foodCategory.entity.FoodCategory;
import zzx.jxc.foodCategory.service.FoodCategoryService;

import java.util.List;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Autowired
    private FoodCategoryDao foodCategoryDao;

    @Override
    public Page<FoodCategory> findAll(FoodCategory foodCategory, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("categoryName",ExampleMatcher.GenericPropertyMatchers.contains());//contains是categoryName包含的数据
//          .withIgnorePaths("isFace");//isFace字段不参与匹配
        //创建实例
        Example<FoodCategory> example = Example.of(foodCategory,exampleMatcher);
        Page<FoodCategory> foodCategoryList = foodCategoryDao.findAll(example, pageable);
        return foodCategoryList;
    }

    @Override
    public FoodCategory save(FoodCategory foodCategory) {
        return foodCategoryDao.save(foodCategory);
    }

    @Override
    public void delete(String id) {
        foodCategoryDao.deleteById(id);
    }

    @Override
    public List<FoodCategory> findAll() {
        return foodCategoryDao.findAll();
    }

    @Override
    public long count() {
        return foodCategoryDao.count();
    }
}
