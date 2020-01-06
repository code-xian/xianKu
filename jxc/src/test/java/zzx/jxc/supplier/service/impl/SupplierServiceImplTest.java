package zzx.jxc.supplier.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.supplier.entity.Supplier;
import zzx.jxc.supplier.service.SupplierService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SupplierServiceImplTest {

    @Autowired
    private SupplierService supplierService;

    @Test
    void findAll() {
        Supplier supplier = new Supplier();
        supplier.setSupplierType("酒水饮料类");
        supplier.setSupplierName("可口可乐公司");
        Page<Supplier> all = supplierService.findAll(supplier, PageRequest.of(0, 1));

    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void findOneById() {
    }
}