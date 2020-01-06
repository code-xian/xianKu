package zzx.jxc.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.admin.entity.Administrators;

import java.util.List;

public interface AdminService {

    Administrators getOne(Integer id);

    List<Administrators> findAll();

    Page<Administrators> findAll(Administrators administrators, Pageable pageable);

    Administrators save(Administrators administrators);

    Administrators findAdministratorsByAdminUsername(String AdminUsername);

    List<Administrators> findByAdminUsername(String AdminUsername);

    void delete(Integer id);
}
