package zzx.jxc.admin.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.admin.entity.Administrators;
import zzx.jxc.enums.AdminAuthorityEnum;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AdminServiceImplTest {

    @Autowired
    private AdminServiceImpl adminService;

    @Test
    void getOne() {
        Administrators one = adminService.getOne(3);
        System.out.println(one);
    }

    @Test
    void findAll() {
        List<Administrators> all = adminService.findAll();
        System.out.println(all);
    }

    @Test
    void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Administrators> all = adminService.findAll(pageRequest);
        System.out.println(all.getTotalElements());
    }

    @Test
    void save() {
        Administrators administrators = new Administrators();
        administrators.setAdminAuthority(AdminAuthorityEnum.SUPER.getAuthority());
        administrators.setAdminName("小六");
        administrators.setAdminUsername("xiao六");
        administrators.setAdminPassword("123456");
        administrators.setAdminToken("123456");
        Administrators save = adminService.save(administrators);
        System.out.println(save);
    }
}