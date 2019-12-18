package zzx.jxc.admin.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.admin.entity.Administrators;

import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdminDaoTest {

    @Autowired
    private AdminDao adminDao;

    @Test
    public void findOneTest() {
        Administrators one = adminDao.getOne(1);
        System.out.println(one.toString());
    }
    @Test
    public void findAllTest() {
        List<Administrators> all = adminDao.findAll();
        System.out.println(all);
    }
    @Test
    public void save() {
        Administrators administrators = new Administrators();
        administrators.setAdminAuthority("0");
        administrators.setAdminName("小五");
        administrators.setAdminUsername("xiaowu");
        administrators.setAdminPassword("123456");
        administrators.setAdminToken("123456");
        Administrators administrators1 = adminDao.save(administrators);
        System.out.println(administrators1);
    }
    @Test
    public void update() {
        Administrators administrators = new Administrators();
        administrators.setAdminId(3);
        administrators.setAdminAuthority("0");
        administrators.setAdminName("大五");
        administrators.setAdminUsername("xiaowu");
        administrators.setAdminPassword("123456");
        administrators.setAdminToken("123456");
        Administrators administrators2 = adminDao.save(administrators);
        System.out.println(administrators2);
    }
    @Test
    public void findByAdminUsernameTest() {
        List<Administrators> zhangsan = adminDao.findByAdminUsername("zhangsan");
        List<Administrators> lisi = adminDao.findByAdminUsername("lisi");
        System.out.println(zhangsan);
        System.out.println(lisi);
    }

    @Test
    public void findAdministratorsByAdminUsernameTest() {
        Administrators zhangsan = adminDao.findAdministratorsByAdminUsername("zhangsan1");
        System.out.println(zhangsan);
    }

}