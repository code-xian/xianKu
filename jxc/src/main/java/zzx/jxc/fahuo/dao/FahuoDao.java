package zzx.jxc.fahuo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.fahuo.entity.FahuoMaster;

public interface FahuoDao extends JpaRepository<FahuoMaster, String> {
    Integer countByFahuoIdLike(String number);

    FahuoMaster findFahuoMasterByFahuoId(String fahuoId);
}
