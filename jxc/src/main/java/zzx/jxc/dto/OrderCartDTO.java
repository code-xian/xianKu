package zzx.jxc.dto;

import lombok.Data;

@Data
public class OrderCartDTO {
    private String foodId;
    private Integer saleQuantity;
    private String stockId;

    public OrderCartDTO(String foodId, Integer saleQuantity, String stockId) {
        this.foodId = foodId;
        this.saleQuantity = saleQuantity;
        this.stockId = stockId;
    }

    public OrderCartDTO() {

    }
}
