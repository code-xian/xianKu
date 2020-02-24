package zzx.jxc.sale.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.VO.SaleFoodSelectListVO;
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

    @Test
    void create() {
    }

    @Test
    void findOne() {
    }

    @Test
    void findList() {
    }

    @Test
    void cancel() {
    }

    @Test
    void finish() {
    }

    @Test
    void findListAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<SaleFoodSelectListVO> listAll = saleService.findListAll("", pageRequest, "", "可乐");
        System.out.println(listAll);
    }
}