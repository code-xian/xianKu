package zzx.jxc.ruku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.VO.RukuOrderInfoVO;
import zzx.jxc.ruku.entity.RukuMaster;
import zzx.jxc.ruku.service.RukuService;
import zzx.jxc.shouhuo.entity.ShouhuoMaster;
import zzx.jxc.util.ResultVOUtil;

import java.util.Map;

@RestController
@RequestMapping("/ruku")
public class RukuController {

    @Autowired
    private RukuService rukuService;

    //新增入库单
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResultVO createOrder(@RequestBody ShouhuoMaster shouhuoMaster) {
        try {
            rukuService.create(shouhuoMaster);
            return ResultVOUtil.success("创建入库单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "创建入库单失败");
        }
    }

    //查询入库单列表
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findList(@RequestParam(required = false) String rukuId,
                             @RequestParam(required = false) String supplierName,
                             @RequestParam(required = false) Integer orderStatus,
                             @RequestParam Integer page,
                             @RequestParam Integer size
    ){
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createTime"));
            RukuMaster rukuMaster = new RukuMaster();
            rukuMaster.setRukuId(rukuId);
            rukuMaster.setSupplierName(supplierName);
            if(orderStatus == null){
                rukuMaster.setRukuStatus(null);
            }else{
                rukuMaster.setRukuStatus(orderStatus);
            }
            Page<RukuMaster> rukuMasters = rukuService.findList(rukuMaster, pageRequest);
            return ResultVOUtil.success(rukuMasters, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询入库单列表错误");
        }
    }
    //查询单个订单详情
    @GetMapping("/detail")
    @CrossOrigin(origins = "*")
    public ResultVO findOne(@RequestParam String rukuId) {
        try {
            RukuOrderInfoVO rukuOrderInfoVO = rukuService.findOne(rukuId);
            return ResultVOUtil.success(rukuOrderInfoVO, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询入库单错误");
        }
    }

    //审核订单
    @PostMapping("/audit")
    @CrossOrigin(origins = "*")
    public ResultVO audit(@RequestBody Map<String,Object> params) {  //取orderStatus和shouhuoId
        try {
            if (params.get("orderStatus").equals(0)) {         //0 审核通过    1审核不通过
                rukuService.finish((String)params.get("rukuId"));
            }else if(params.get("orderStatus").equals(1)){
                rukuService.cancel((String)params.get("rukuId"));
            }
            return ResultVOUtil.success("审核成功");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "审核错误");
        }
    }
}
