package zzx.jxc.stockLog.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.stockLog.entity.StockLog;

public interface LogDao extends JpaRepository<StockLog, Integer> {

}
