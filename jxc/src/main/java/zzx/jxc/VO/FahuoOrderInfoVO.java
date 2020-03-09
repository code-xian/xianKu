package zzx.jxc.VO;

import lombok.Data;
import zzx.jxc.fahuo.entity.FahuoDetail;

import java.util.List;

@Data
public class FahuoOrderInfoVO {
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String saleRemarks;
    private String storeFzr;
    private String submissionWay;
    private String submissionDate;
    private List<FahuoDetail> detailList;
}
