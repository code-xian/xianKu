package zzx.jxc.foodInfo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.foodInfo.entity.FoodInfo;

import java.util.List;

public interface FoodInfoService {
    Page<FoodInfo> findAll(FoodInfo foodInfo, Pageable pageable);

    FoodInfo save(FoodInfo foodInfo);

    void delete(FoodInfo foodInfo);

    List<FoodInfo> findAll();

    FoodInfo findOneById(String id);

    long count();
}
