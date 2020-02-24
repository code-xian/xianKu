package zzx.jxc.enums;

import lombok.Getter;

@Getter
public enum FoodStatusEnum {
    Up(0,"食品上架"),
    Down(1,"食品下架"),
    ;

    private Integer code;
    private String msg;

    FoodStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
