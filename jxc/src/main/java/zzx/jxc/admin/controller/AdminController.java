package zzx.jxc.admin.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.AdministratorsVO;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.admin.entity.Administrators;
import zzx.jxc.admin.service.AdminService;
import zzx.jxc.util.ResultVOUtil;

import java.util.Random;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResultVO login(@RequestBody Administrators administrators) {
        try {
            String adminUsername = administrators.getAdminUsername();
            Administrators administratorsByAdminUsername = adminService.findAdministratorsByAdminUsername(adminUsername);
            if (administratorsByAdminUsername != null) {
                if (administratorsByAdminUsername.getAdminPassword().equals(administrators.getAdminPassword())) {
                    return ResultVOUtil.success(administratorsByAdminUsername.getAdminAuthority(), "ok");
                } else {
                    return ResultVOUtil.error(2, "密码错误");
                }
            } else {
                return ResultVOUtil.error(3, "用户名不存在");
            }
        } catch (Exception e) {
            return ResultVOUtil.error(400, "系统错误,请联系管理员");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/query")
    public ResultVO query(String adminUsername) {
        AdministratorsVO administratorsVO = new AdministratorsVO();
        try {
            Administrators administrators = adminService.findAdministratorsByAdminUsername(adminUsername);
            BeanUtils.copyProperties(administrators, administratorsVO);
            return ResultVOUtil.success(administratorsVO, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(404, "系统错误,获取不到管理员信息");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/list")
    public ResultVO findAll(@RequestParam(required = false) String adminName,
                            @RequestParam Integer page,
                            @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            Administrators administrators = new Administrators();
            administrators.setAdminName(adminName);
            Page<Administrators> all = adminService.findAll(administrators, pageRequest);
            return ResultVOUtil.success(all, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询错误");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete")
    public ResultVO delete(@RequestBody Administrators administrators) {
        try {
            Integer adminId = administrators.getAdminId();
            adminService.delete(adminId);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "删除失败");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/save")
    public ResultVO save(@RequestBody Administrators administrators) {
        try {
            administrators.setAdminAuthority("0");
            administrators.setAdminToken(String.valueOf(System.currentTimeMillis() + new Random().nextInt(99)));
            adminService.save(administrators);
            return ResultVOUtil.success("保存成功");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "保存失败");
        }
    }
}
