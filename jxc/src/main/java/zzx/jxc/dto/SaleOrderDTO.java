package zzx.jxc.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 前端请求的对象
 */
@Data
public class SaleOrderDTO {
    private String saleId;
    private String storeName;
    private String storePhone;
    private String storeAddress;
    private Date submissionDate;   //交货期限
    private String submissionWay;
    private Integer storeId;   //供应门店id
    private String storeFzr;   //门店负责人
    private String reviewer;
    private String saleRemarks;
//    private Integer saleStatus;
//    private BigDecimal purchaseAmount;
    List<OrderCartDTO> saleDetailList;
}
