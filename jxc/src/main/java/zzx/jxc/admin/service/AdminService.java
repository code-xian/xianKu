package zzx.jxc.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.admin.entity.Administrators;

import java.util.List;

public interface AdminService {

    public Administrators getOne(Integer id);

    public List<Administrators> findAll();

    public Page<Administrators> findAll(Pageable pageable);

    public Administrators save(Administrators administrators);

    public Administrators findAdministratorsByAdminUsername(String AdminUsername);

    public List<Administrators> findByAdminUsername(String AdminUsername);
}
