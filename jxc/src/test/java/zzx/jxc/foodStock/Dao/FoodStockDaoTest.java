package zzx.jxc.foodStock.Dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.foodStock.entity.FoodStock;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FoodStockDaoTest {


    @Autowired
    public FoodStockDao foodStockDao;
    @Test
    void findByFoodIdAndStockId() {
        Integer byFoodIdAndStockId = foodStockDao.findStockByFoodIdAndStockId("001", "001");
        System.out.println(byFoodIdAndStockId+"个");
    }


    @Test
    void findAllByStockId() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<FoodStock> allByStockId = foodStockDao.findAllByStockId("001", pageRequest);
        System.out.println(allByStockId.getContent());
        System.out.println(allByStockId);
    }
}