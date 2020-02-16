package zzx.jxc.foodCategory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.foodCategory.entity.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    Page<FoodCategory> findAll(FoodCategory foodCategory, Pageable pageable);

    FoodCategory save(FoodCategory stock);

    void delete(String id);

    List<FoodCategory> findAll();

    long count();
}
