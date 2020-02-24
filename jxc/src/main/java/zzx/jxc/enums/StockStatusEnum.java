package zzx.jxc.enums;

import lombok.Getter;

@Getter
public enum StockStatusEnum {
    STOCK_UP(0, "在用"),
    STOCK_DOWN(1, "废弃"),
    ;
    private Integer code;
    private String msg;

    StockStatusEnum(Integer code, String msg) {
        this.code=code;
        this.msg=msg;
    }
}
