package zzx.jxc.sale.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.sale.entity.SaleDetail;

import java.util.List;

public interface SaleDetailDao extends JpaRepository<SaleDetail,String> {
    List<SaleDetail> findAllBySaleId(String saleId);
}


