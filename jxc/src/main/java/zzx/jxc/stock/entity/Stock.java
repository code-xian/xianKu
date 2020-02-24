package zzx.jxc.stock.entity;


import lombok.Data;
import zzx.jxc.enums.StockStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Stock {

    @Id
    private String stockId;
    private String stockFzr;
    private String stockType;
    private String stockName;
    private String stockNote;
    private Integer stockStatus = StockStatusEnum.STOCK_UP.getCode();

//    @OneToMany( mappedBy="stock")
//    private List<FoodStock> foodStock = new ArrayList<>();
}
