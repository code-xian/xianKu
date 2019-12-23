package zzx.jxc.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.admin.entity.Administrators;

import java.util.List;

public interface AdminDao extends JpaRepository<Administrators,Integer> {
    List<Administrators> findByAdminUsername(String name);

    Administrators findAdministratorsByAdminUsername(String name);
}
