package zzx.jxc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 前端请求的对象
 */

public class SaleOrderDTO implements Serializable {
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


    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getSubmissionWay() {
        return submissionWay;
    }

    public void setSubmissionWay(String submissionWay) {
        this.submissionWay = submissionWay;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreFzr() {
        return storeFzr;
    }

    public void setStoreFzr(String storeFzr) {
        this.storeFzr = storeFzr;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getSaleRemarks() {
        return saleRemarks;
    }

    public void setSaleRemarks(String saleRemarks) {
        this.saleRemarks = saleRemarks;
    }

    public List<OrderCartDTO> getSaleDetailList() {
        return saleDetailList;
    }

    public void setSaleDetailList(List<OrderCartDTO> saleDetailList) {
        this.saleDetailList = saleDetailList;
    }
}
