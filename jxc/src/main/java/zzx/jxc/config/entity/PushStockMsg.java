package zzx.jxc.config.entity;


import lombok.Data;

@Data
public class PushStockMsg {
    private String stockName;
    private String stockId;
    private Integer stock;
    private String foodName;
    private String foodId;
    private Integer number; //消息条数

}
