package zzx.jxc.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Page<Administrators> findAll(Pageable pageable) {
        return adminDao.findAll(pageable);
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
}
