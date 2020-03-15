package zzx.jxc.shouhuo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.shouhuo.entity.ShouhuoDetail;

import java.util.List;

public interface ShouhuoDetailDao extends JpaRepository<ShouhuoDetail,String> {
    List<ShouhuoDetail> findAllByShouhuoId(String shouhuoId);
}
