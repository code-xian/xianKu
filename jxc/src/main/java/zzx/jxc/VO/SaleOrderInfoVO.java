package zzx.jxc.VO;

import lombok.Data;
import zzx.jxc.sale.entity.SaleDetail;

import java.util.List;

@Data
public class SaleOrderInfoVO {
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private String saleRemarks;
    private String storeFzr;
    private String submissionWay;
    private String submissionDate;
    private List<SaleDetail> detailList;

}
