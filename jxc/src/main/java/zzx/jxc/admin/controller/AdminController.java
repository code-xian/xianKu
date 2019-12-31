package zzx.jxc.admin.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.AdministratorsVO;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.admin.entity.Administrators;
import zzx.jxc.admin.service.AdminService;
import zzx.jxc.util.ResultVOUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResultVO login(@RequestBody Administrators administrators) {
//        Administrators administrators1 = new Administrators();
//        administrators1.setAdminName("阿松大");
        try {
            String adminUsername = administrators.getAdminUsername();
            Administrators administratorsByAdminUsername = adminService.findAdministratorsByAdminUsername(adminUsername);
            if (administratorsByAdminUsername != null) {
                if (administratorsByAdminUsername.getAdminPassword().equals(administrators.getAdminPassword())) {
                    return ResultVOUtil.success(administratorsByAdminUsername.getAdminAuthority(), "ok");
                } else {
                    return ResultVOUtil.error(2,"密码错误");
                }
            } else {
                return ResultVOUtil.error(3,"用户名不存在");
            }
        } catch (Exception e) {
            return ResultVOUtil.error(400,"系统错误,请联系管理员");
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/query")
    public ResultVO query(String adminUsername) {
        AdministratorsVO administratorsVO = new AdministratorsVO();
        try {
            Administrators administrators = adminService.findAdministratorsByAdminUsername(adminUsername);
            BeanUtils.copyProperties(administrators,administratorsVO);
            return ResultVOUtil.success(administratorsVO, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(404,"系统错误,获取不到管理员信息");
        }
    }
}
