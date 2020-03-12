package zzx.jxc.VO;

import lombok.Data;
import zzx.jxc.chuku.entity.ChukuDetail;

import java.util.List;

@Data
public class ChukuOrderInfoVO {
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String saleRemarks;
    private String storeFzr;
    private String submissionWay;
    private String submissionDate;
    private List<ChukuDetail> detailList;
}
