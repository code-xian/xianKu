package zzx.jxc.fahuo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zzx.jxc.fahuo.entity.FahuoDetail;

import java.util.List;

public interface FahuoDetailDao extends JpaRepository<FahuoDetail,String> {
    List<FahuoDetail> findAllByFahuoId(String fahuoId);
}
