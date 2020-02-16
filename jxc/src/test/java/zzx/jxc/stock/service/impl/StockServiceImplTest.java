package zzx.jxc.stock.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.stock.entity.Stock;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StockServiceImplTest {

    @Autowired
    private StockServiceImpl stockService;
    @Test
    void findAll() {
        Stock stock = new Stock();
        stock.setStockFzr("张三");
        Page<Stock> all = stockService.findAll(stock, PageRequest.of(0, 5));
        System.out.println(all);
    }

    @Test
    void save() {
        Stock stock = new Stock();
        stock.setStockFzr("张三");
        stock.setStockId("001");
        stock.setStockName("天河仓库");
        stock.setStockType("冷冻类");
        Stock save = stockService.save(stock);
        System.out.println(save);
    }

    @Test
    void delete() {
        stockService.delete("001");
    }

    @Test
    void testFindAll() {
        List<Stock> all = stockService.findAll();
        System.out.println(all);
    }

}