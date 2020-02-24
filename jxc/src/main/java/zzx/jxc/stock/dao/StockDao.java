package zzx.jxc.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.stock.entity.Stock;

public interface StockDao extends JpaRepository<Stock, String> {
    Stock findStockByStockId(String id);
    long count();

    Stock findStockByStockNameLike(String stockName);


}
