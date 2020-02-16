package zzx.jxc.stock.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StockDaoTest {

    @Autowired
    public StockDao stockDao;

    @Test
    void findStockByStockId() {
    }

    @Test
    void count() {
        long count = stockDao.count();
        System.out.println(count);
    }
}