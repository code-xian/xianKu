package zzx.jxc.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.stock.dao.StockDao;
import zzx.jxc.stock.entity.Stock;
import zzx.jxc.stock.service.StockService;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;
    @Override
    public Page<Stock> findAll(Stock stock, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("stockName",ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("stockType",ExampleMatcher.GenericPropertyMatchers.contains());
//          .withIgnorePaths("isFace");//isFace字段不参与匹配
        //创建实例
        Example<Stock> example = Example.of(stock,exampleMatcher);
        Page<Stock> stockList = stockDao.findAll(example, pageable);
        return stockList;
    }

    @Override
    public Stock save(Stock stock) {
        return stockDao.save(stock);
    }

    @Override
    public void delete(String id) {
        stockDao.deleteById(id);
    }

    @Override
    public List<Stock> findAll() {
        return stockDao.findAll();
    }

//    @Override
//    public Stock findOneById(String id) {
//        return stockDao.findStockByStockId(id);
//    }

    @Override
    public long count() {
        return stockDao.count();
    }

}
