package zzx.jxc.chuku.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.chuku.entity.ChukuMaster;

public interface ChukuDao extends JpaRepository<ChukuMaster, String> {
        Integer countByChukuIdLike(String number);

        ChukuMaster findChukuMasterByChukuId(String chukuId);
        }