package zzx.jxc.store.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.store.entity.Store;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StoreDaoTest {

    @Autowired
    private StoreDao storeDao;

    @Test
    void findAllTest() {
        Page<Store> all = storeDao.findAll(PageRequest.of(0, 5));
        System.out.println(all);
    }

    @Test
    void saveTest() {
        Store store = new Store();
        store.setStoreAddress("番禺区小妖村");
        store.setStoreFzr("李四");
        store.setStoreName("番禺区门店");
        store.setStoreNote("这店很牛p");
        store.setStorePhone("13413413413");
        Store save = storeDao.save(store);
        System.out.println(save);

    }

    @Test
    void deleteTest() {
        storeDao.deleteById(002);
    }


    @Test
    void findtiaojian() {
        Store store = new Store();
        store.setStoreAddress("");
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
            .withMatcher("storeId",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
            .withMatcher("storeAddress",ExampleMatcher.GenericPropertyMatchers.contains());
//          .withIgnorePaths("isFace");//isFace字段不参与匹配

        //创建实例
        Example<Store> example = Example.of(store,exampleMatcher);
        Page<Store> all = storeDao.findAll(example, PageRequest.of(0, 5));
        System.out.println(all);

    }
}