package zzx.jxc.foodStock.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.VO.FoodStockVO;
import zzx.jxc.foodStock.service.FoodStockService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FoodInfoServiceImplTest {

    @Autowired
    private FoodStockService foodStockService;
    @Test
    void findAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<FoodStockVO> all = foodStockService.findAll("001", pageRequest,"");
        System.out.println(all);
    }
}