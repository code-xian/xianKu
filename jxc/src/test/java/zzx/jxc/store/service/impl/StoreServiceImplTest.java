package zzx.jxc.store.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.store.entity.Store;
import zzx.jxc.store.service.StoreService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StoreServiceImplTest {

    @Autowired
    private StoreService storeService;
    @Test
    void findAll() {
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
        Store storeByStoreId = storeService.findOneById(15);
        System.out.println(storeByStoreId);
    }
}