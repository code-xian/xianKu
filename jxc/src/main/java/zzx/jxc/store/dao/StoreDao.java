package zzx.jxc.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.store.entity.Store;

public interface StoreDao extends JpaRepository<Store, Integer> {
    Store findStoreByStoreId(Integer id);
}
