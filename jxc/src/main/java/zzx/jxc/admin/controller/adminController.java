package zzx.jxc.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzx.jxc.admin.entity.Administrators;
import zzx.jxc.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public String login(@RequestBody Administrators administrators) {
//        Administrators administrators1 = new Administrators();
//        administrators1.setAdminName("阿松大");
        try {
            String adminUsername = administrators.getAdminUsername();
            Administrators administratorsByAdminUsername = adminService.findAdministratorsByAdminUsername(adminUsername);
            if (administratorsByAdminUsername != null) {
                if (administratorsByAdminUsername.getAdminPassword().equals(administrators.getAdminPassword())) {
                    System.out.println("ok");
                    return "ok";
                }else{
                    return "密码错误";
                }
            }else{
                return "该用户名不存在";
            }
        } catch (Exception e) {
            return "系统错误";
        }
    }
}
