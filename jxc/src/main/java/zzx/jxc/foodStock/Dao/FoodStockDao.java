package zzx.jxc.foodStock.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zzx.jxc.foodStock.entity.FoodStock;

import java.util.List;

public interface FoodStockDao extends JpaRepository<FoodStock,Integer> {

    @Query(
            nativeQuery = true, value = "select t.stock from  food_stock t\n" +
            "inner join food_info c on c.food_id=t.food_id\n" +
            "inner join stock s on s.stock_id =t.stock_id\n" +
            "where\n" +
            " c.food_id=?1  and  s.stock_id =?2"
    )
    Integer findFoodStockByFoodIdAndStockId(String foodId, String StockId);

    Page<FoodStock> findAllByStockId(String stockId, Pageable pageable);

    List<FoodStock> findAllByStockIdIn(List<String> stockIdList);


}
