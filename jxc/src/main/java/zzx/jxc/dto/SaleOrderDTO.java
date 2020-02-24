package zzx.jxc.dto;

import lombok.Data;
import zzx.jxc.sale.entity.SaleDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private String stockName;
    private String reviewer;
    private String saleRemarks;
    private Integer saleStatus;
    private BigDecimal purchaseAmount;
    private Date createTime;
    private Date updateTime;
    List<SaleDetail> saleDetailList;
}
