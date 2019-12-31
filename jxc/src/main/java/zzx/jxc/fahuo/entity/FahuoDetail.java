package zzx.jxc.fahuo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class FahuoDetail {

    @Id
    private String detailId;
    private String fahuoId;
    private String foodId;
    private Integer shelfLife;
    private Integer foodQuantity;
    private String foodUnit;
    private String detailRemarks;
    private String foodName;

}
