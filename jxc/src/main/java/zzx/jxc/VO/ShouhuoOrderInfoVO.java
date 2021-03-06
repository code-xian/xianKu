package zzx.jxc.VO;

import lombok.Data;
import zzx.jxc.shouhuo.entity.ShouhuoDetail;

import java.util.List;

@Data
public class ShouhuoOrderInfoVO {
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private String purchaseRemarks;
    private String supplierFzr;
    private String submissionWay;
    private String submissionDate;
    private List<ShouhuoDetail> detailList;
}
