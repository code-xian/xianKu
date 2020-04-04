package zzx.jxc.stockLog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zzx.jxc.stockLog.Dao.LogDao;
import zzx.jxc.stockLog.entity.StockLog;
import zzx.jxc.stockLog.service.LogService;
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Override
    public Page<StockLog> findList(StockLog stockLog, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("documentNumber", ExampleMatcher.GenericPropertyMatchers.contains())//contains是storeId 包含的数据
                .withMatcher("stockName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("documentType", ExampleMatcher.GenericPropertyMatchers.contains());
//                .withIgnorePaths("stockStatus");//isFace字段不参与匹配
        //创建实例
        Example<StockLog> example = Example.of(stockLog, exampleMatcher);
        Page<StockLog> stockLogs = logDao.findAll(example, pageable);
        return stockLogs;
    }

}
