package zzx.jxc.ruku.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.ruku.entity.RukuMaster;

public interface RukuDao extends JpaRepository<RukuMaster, String> {
    Integer countByRukuIdLike(String number);

    RukuMaster findRukuMasterByRukuId(String rukuId);
}
