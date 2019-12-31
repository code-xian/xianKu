package zzx.jxc.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.store.entity.Store;

import java.util.List;

public interface StoreService {
    Page<Store> findAll(Store store, Pageable pageable);

    Store save(Store store);

    void delete(Integer id);

    List<Store> findAll();
    Store findOneById(Integer id);
}
