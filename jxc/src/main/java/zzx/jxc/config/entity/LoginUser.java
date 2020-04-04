package zzx.jxc.config.entity;

import lombok.Data;

@Data
public class LoginUser {
    /**
     * 当前登陆的用户
     */
    private String loginUser;

    /**
     * 推送的标题
     */
    private String title;

    /**
     * 推送的内容
     */
    private String content;
    /**
     * 空的构造函数
     */
}
