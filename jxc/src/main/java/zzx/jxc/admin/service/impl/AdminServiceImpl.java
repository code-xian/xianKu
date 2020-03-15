package zzx.jxc.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.admin.dao.AdminDao;
import zzx.jxc.admin.entity.Administrators;
import zzx.jxc.admin.service.AdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;
    @Override
    public Administrators getOne(Integer id) {
        return adminDao.getOne(id);
    }
    @Override
    public List<Administrators> findAll() {
        return adminDao.findAll();
    }
    @Override
    public Page<Administrators> findAll(Administrators administrators, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("adminName",ExampleMatcher.GenericPropertyMatchers.contains());//contains是storeId 包含的数据
//          .withIgnorePaths("isFace");//isFace字段不参与匹配
        //创建实例
        Example<Administrators> example = Example.of(administrators,exampleMatcher);
        Page<Administrators> all = adminDao.findAll(example, pageable);
        return all;
    }
    @Override
    public Administrators save(Administrators administrators) {
        return adminDao.save(administrators);
    }
    @Override
    public Administrators findAdministratorsByAdminUsername(String AdminUsername) {
        return adminDao.findAdministratorsByAdminUsername(AdminUsername);
    }
    @Override
    public List<Administrators> findByAdminUsername(String AdminUsername) {
        return adminDao.findByAdminUsername(AdminUsername);
    }
    @Override
    public void delete(Integer id) {
        adminDao.deleteById(id);
    }
}
