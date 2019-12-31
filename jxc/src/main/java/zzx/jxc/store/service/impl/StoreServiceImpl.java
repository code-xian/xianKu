package zzx.jxc.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.store.dao.StoreDao;
import zzx.jxc.store.entity.Store;
import zzx.jxc.store.service.StoreService;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;
    @Override
    public Page<Store> findAll(Store store, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("storeId",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("storeName",ExampleMatcher.GenericPropertyMatchers.contains());
//          .withIgnorePaths("isFace");//isFace字段不参与匹配

        //创建实例findStoreByStoreId
        Example<Store> example = Example.of(store,exampleMatcher);
        Page<Store> all = storeDao.findAll(example, pageable);
        return all;
    }

    @Override
    public Store save(Store store) {
        return storeDao.save(store);
    }

    @Override
    public void delete(Integer id) {
        storeDao.deleteById(id);
    }

    @Override
    public List<Store> findAll() {
        return storeDao.findAll();
    }

    @Override
    public Store findOneById(Integer id) {
        return storeDao.findStoreByStoreId(id);
    }
}
