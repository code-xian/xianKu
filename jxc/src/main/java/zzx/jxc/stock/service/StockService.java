package zzx.jxc.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.stock.entity.Stock;

import java.util.List;


public interface StockService {
    Page<Stock> findAll(Stock stock, Pageable pageable);

    Stock save(Stock stock);

    void delete(Stock stock);

    void enable(Stock stock);

    List<Stock> findAll();

    Stock findOneById(String id);

    long count();

}
