package zzx.jxc.sale.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.sale.service.SaleService;
import zzx.jxc.util.ddbhUtil;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SaleServiceImplTest {
    @Autowired
    private SaleService saleService;
    @Test
    void countBySaleIdLike() {
        Integer integer = saleService.countBySaleIdLike();
        System.out.println(integer);
        String xsdd = ddbhUtil.xsdd(integer);
        System.out.println(xsdd);
    }
}