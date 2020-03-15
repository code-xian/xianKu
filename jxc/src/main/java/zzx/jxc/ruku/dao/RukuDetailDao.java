package zzx.jxc.ruku.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.ruku.entity.RukuDetail;

import java.util.List;

public interface RukuDetailDao extends JpaRepository<RukuDetail, String> {
    List<RukuDetail> findAllByRukuId(String rukuId);
}
