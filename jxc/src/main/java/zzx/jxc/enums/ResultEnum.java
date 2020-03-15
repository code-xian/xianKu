package zzx.jxc.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    FOOD_NOT_EXIST(10, "食品不存在"),
    FOOD_STOCK_ERROR(11, "库存不正确"),
    ORDER_STATUS_ERROR(12, "订单不正确"),
    ORDER_AUDIT_FAIL(13,"订单审核失败"),
    ORDER_CREATE_FAIL(14, "订单创建失败"),
    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
