package zzx.jxc.supplier.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Supplier {

    @Id
    private String supplierId;
    private String supplierAddress;
    private String supplierName;
    private String supplierPhone;
    private String supplierFzr;
    private String supplierType;
    private String supplierNote;

}