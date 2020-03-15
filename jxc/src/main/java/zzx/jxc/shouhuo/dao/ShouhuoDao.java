package zzx.jxc.shouhuo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.shouhuo.entity.ShouhuoMaster;

public interface ShouhuoDao extends JpaRepository<ShouhuoMaster,String> {
    Integer countByShouhuoIdLike(String number);

    ShouhuoMaster findShouhuoMasterByShouhuoId(String shouhuoId);
}
