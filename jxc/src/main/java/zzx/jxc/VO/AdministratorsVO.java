package zzx.jxc.VO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdministratorsVO {

    @JsonProperty("adminId")
    private Integer adminId;

    private String adminToken;

    private String adminName;

    private String adminUsername;

    private String adminPassword;

    private String adminAuthority;
}
