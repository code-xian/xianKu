package zzx.jxc.fahuo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.VO.FahuoOrderInfoVO;
import zzx.jxc.fahuo.entity.FahuoMaster;
import zzx.jxc.sale.entity.SaleMaster;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FahuoServiceImplTest {



    @Autowired
    private FahuoServiceImpl fahuoService;

    @Test
    void countByFahuoIdLike() {
    }

    @Test
    void create() {
        SaleMaster saleMaster = new SaleMaster();
        saleMaster.setSaleId("XSDD20200303001");
        fahuoService.create(saleMaster);
    }

    @Test
    void findOne() {
        FahuoOrderInfoVO fhd20200309001 = fahuoService.findOne("FHD20200309001");
        System.out.println(fhd20200309001);
    }

    @Test
    void findList() {
        FahuoMaster fahuoMaster = new FahuoMaster();
        Page<FahuoMaster> list = fahuoService.findList(fahuoMaster, PageRequest.of(0, 10));
    }

    @Test
    void cancel() {
    }

    @Test
    void finish() {
    }
}