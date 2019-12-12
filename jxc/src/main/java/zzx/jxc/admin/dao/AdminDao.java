package zzx.jxc.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.admin.entity.Admin;

public interface AdminDao extends JpaRepository<Admin,Integer> {
}
