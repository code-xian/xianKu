package zzx.jxc.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    AUDIT_PASS(10, "审核通过"),
    AUDIT_NO_PASS(11, "审核未通过"),
    UNAUDITED(12, "未审核"),
    ;
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code=code;
        this.msg=msg;
    }
}
