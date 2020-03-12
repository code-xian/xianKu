package zzx.jxc.chuku.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.chuku.entity.ChukuDetail;

import java.util.List;

public interface ChukuDetailDao extends JpaRepository<ChukuDetail,String> {
    List<ChukuDetail> findAllByChukuId(String chukuId);
}