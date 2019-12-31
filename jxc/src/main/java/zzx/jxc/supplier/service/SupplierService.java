package zzx.jxc.supplier.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.supplier.entity.Supplier;

import java.util.List;

public interface SupplierService {
    Page<Supplier> findAll(Supplier supplier, Pageable pageable);

    Supplier save(Supplier supplier);

    void delete(String id);

    List<Supplier> findAll();

    Supplier findOneById(String id);
}
