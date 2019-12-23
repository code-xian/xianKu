package zzx.jxc.sale.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SaleDaoTest {

    @Autowired
    public SaleDao saleDao;

    @Test
    void countBySaleIdLike() {
        Integer integer = saleDao.countBySaleIdLike("%20191223%");
        System.out.println(integer+"一");

    }

    @Test
    void countSaleMastersBySaleIdLike() {
        Integer integer = saleDao.countSaleMastersBySaleIdLike("%20191223%");
        System.out.println(integer+"二");
    }
}