package zzx.jxc.foodStock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.FoodStockVO;

public interface FoodStockService {
    //根据仓库id查询所有库存食品
    Page<FoodStockVO> findAll(String stockId, Pageable pageable, String CategoryId);



    //减库存
    //    void decreaseStock(List)

    //加库存


}
