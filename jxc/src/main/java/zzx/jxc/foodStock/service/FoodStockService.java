package zzx.jxc.foodStock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.FoodStockVO;
import zzx.jxc.dto.OrderCartDTO;

import java.util.List;

public interface FoodStockService {
    //根据仓库id查询所有库存食品
    Page<FoodStockVO> findAll(String stockId, Pageable pageable, String CategoryId);

    //减库存
    void decreaseStock(List<OrderCartDTO> cartDTOList);

    //加库存
    void increaseStock(List<OrderCartDTO> cartDTOList);



}
