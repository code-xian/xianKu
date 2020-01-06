package zzx.jxc.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.supplier.dao.SupplierDao;
import zzx.jxc.supplier.entity.Supplier;
import zzx.jxc.supplier.service.SupplierService;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public Page<Supplier> findAll(Supplier supplier, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("supplierName",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("supplierType",ExampleMatcher.GenericPropertyMatchers.contains());
//          .withIgnorePaths("isFace");//isFace字段不参与匹配
        //创建实例
        Example<Supplier> example = Example.of(supplier,exampleMatcher);
        Page<Supplier> all = supplierDao.findAll(example, pageable);
        return all;
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierDao.save(supplier);
    }

    @Override
    public void delete(Integer id) {
        supplierDao.deleteById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierDao.findAll();
    }

    @Override
    public Supplier findOneById(Integer id) {
        return supplierDao.findSupplierBySupplierId(id);
    }
}
