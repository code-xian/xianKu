package zzx.jxc.supplier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.supplier.entity.Supplier;

public interface SupplierDao extends JpaRepository<Supplier, Integer> {
    Supplier findSupplierBySupplierId(Integer id);
}
