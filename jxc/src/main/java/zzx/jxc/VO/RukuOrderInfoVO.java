package zzx.jxc.VO;

import lombok.Data;
import zzx.jxc.ruku.entity.RukuDetail;

import java.util.List;

@Data
public class RukuOrderInfoVO {
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;
    private String purchaseRemarks;
    private String supplierFzr;
    private String submissionWay;
    private String submissionDate;
    private List<RukuDetail> detailList;
}
