package zzx.jxc.chuku.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.VO.ChukuOrderInfoVO;
import zzx.jxc.chuku.entity.ChukuMaster;
import zzx.jxc.chuku.service.ChukuService;
import zzx.jxc.fahuo.entity.FahuoMaster;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ChukuServiceImplTest {

    @Autowired
    private ChukuService chukuService;
    @Test
    void countByChukuIdLike() {

    }

    @Test
    void create() {
        FahuoMaster fahuoMaster = new FahuoMaster();
        fahuoMaster.setFahuoId("FHD20200310001");
        chukuService.create(fahuoMaster);
    }

    @Test
    void findOne() {
        ChukuOrderInfoVO fhd20200310001 = chukuService.findOne("CKD20200310001");
    }

    @Test
    void findList() {
        ChukuMaster chukuMaster = new ChukuMaster();
        Page<ChukuMaster> list = chukuService.findList(chukuMaster, PageRequest.of(0, 10));
    }

    @Test
    void cancel() {
        chukuService.cancel("CKD20200310001");
    }

    @Test
    void finish() {
    }
}