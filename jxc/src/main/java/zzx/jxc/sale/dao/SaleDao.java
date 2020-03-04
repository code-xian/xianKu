package zzx.jxc.sale.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.sale.entity.SaleMaster;

public interface SaleDao extends JpaRepository<SaleMaster,String> {
    //两种都能查询类型
    Integer countBySaleIdLike(String number);
    Integer countSaleMastersBySaleIdLike(String number);

    SaleMaster findSaleMasterBySaleId(String saleId);
}
