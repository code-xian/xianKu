package zzx.jxc.fahuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.FahuoOrderInfoVO;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.fahuo.entity.FahuoMaster;
import zzx.jxc.fahuo.service.FahuoService;
import zzx.jxc.sale.entity.SaleMaster;
import zzx.jxc.util.ResultVOUtil;

import java.util.Map;

@RestController
@RequestMapping("/fahuo")
public class FahuoController {
    @Autowired
    private FahuoService fahuoService;


    //新增发货单
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResultVO createOrder(@RequestBody SaleMaster saleMaster) {
        try {
            fahuoService.create(saleMaster);
            return ResultVOUtil.success("创建发货单成功");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "创建发货单失败");
        }
    }

    //查询发货单列表
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findList(@RequestParam(required = false) String fahuoId,
                             @RequestParam(required = false) String storeName,
                             @RequestParam(required = false) Integer orderStatus,
                             @RequestParam Integer page,
                             @RequestParam Integer size
    ){
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createTime"));
            FahuoMaster fahuoMaster = new FahuoMaster();
            fahuoMaster.setFahuoId(fahuoId);
            fahuoMaster.setStoreName(storeName);
            if(orderStatus == null){
                fahuoMaster.setFahuoStatus(null);
            }else{
                fahuoMaster.setFahuoStatus(orderStatus);
            }
            Page<FahuoMaster> fahuoMasterList = fahuoService.findList(fahuoMaster, pageRequest);
            return ResultVOUtil.success(fahuoMasterList, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询发货单列表错误");
        }
    }

    //查询单个订单详情
    @GetMapping("/detail")
    @CrossOrigin(origins = "*")
    public ResultVO findOne(@RequestParam String fahuoId) {
        try {
            FahuoOrderInfoVO fahuoInfo = fahuoService.findOne(fahuoId);
            return ResultVOUtil.success(fahuoInfo, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询发货单单错误");
        }
    }

    //审核订单
    @PostMapping("/audit")
    @CrossOrigin(origins = "*")
    public ResultVO audit(@RequestBody Map<String,Object> params) {  //取orderStatus和fahuoId
        try {
            if (params.get("orderStatus").equals(0)) {         //0 审核通过    1审核不通过
                fahuoService.finish((String)params.get("fahuoId"));
            }else if(params.get("orderStatus").equals(1)){
                fahuoService.cancel((String)params.get("fahuoId"));
            }
            return ResultVOUtil.success("审核成功");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "审核错误");
        }
    }
}
