package zzx.jxc.stockLog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.stockLog.entity.StockLog;

public interface LogService {
    //查询列表
    Page<StockLog> findList(StockLog stockLog, Pageable pageable);

}
