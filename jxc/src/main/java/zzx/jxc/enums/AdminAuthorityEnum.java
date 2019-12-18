package zzx.jxc.enums;

import lombok.Getter;

@Getter
public enum AdminAuthorityEnum {

    SUPER("0","超级"),
    NORMAL("1","普通");

    private String authority;
    private String message;

    AdminAuthorityEnum(String authority,String message) {
        this.authority=authority;
        this.message=message;
    }

}
