package zzx.jxc.chuku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ChukuOrderInfoVO;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.chuku.entity.ChukuMaster;
import zzx.jxc.chuku.service.ChukuService;
import zzx.jxc.fahuo.entity.FahuoMaster;
import zzx.jxc.util.ResultVOUtil;

import java.util.Map;

@RestController
@RequestMapping("/chuku")
public class ChukuController {
    @Autowired
    private ChukuService chukuService;

    //新增出库单
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResultVO createOrder(@RequestBody FahuoMaster fahuoMaster) {
        try {
            chukuService.create(fahuoMaster);
            return ResultVOUtil.success("创建出库单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "创建出库单失败");
        }
    }

    //查询出库单列表
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findList(@RequestParam(required = false) String chukuId,
                             @RequestParam(required = false) String storeName,
                             @RequestParam(required = false) Integer orderStatus,
                             @RequestParam Integer page,
                             @RequestParam Integer size
    ){
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            ChukuMaster chukuMaster = new ChukuMaster();
            chukuMaster.setChukuId(chukuId);
            chukuMaster.setStoreName(storeName);
            if(orderStatus == null){
                chukuMaster.setChukuStatus(null);
            }else{
                chukuMaster.setChukuStatus(orderStatus);
            }
            Page<ChukuMaster> chukuMasters = chukuService.findList(chukuMaster, pageRequest);
            return ResultVOUtil.success(chukuMasters, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询出库单列表错误");
        }
    }
    //查询单个订单详情
    @GetMapping("/detail")
    @CrossOrigin(origins = "*")
    public ResultVO findOne(@RequestParam String chukuId) {
        try {
            ChukuOrderInfoVO chukuOrderInfoVO = chukuService.findOne(chukuId);
            return ResultVOUtil.success(chukuOrderInfoVO, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询出库单错误");
        }
    }

    //审核订单
    @PostMapping("/audit")
    @CrossOrigin(origins = "*")
    public ResultVO audit(@RequestBody Map<String,Object> params) {  //取orderStatus和fahuoId
        try {
            if (params.get("orderStatus").equals(0)) {         //0 审核通过    1审核不通过
                chukuService.finish((String)params.get("chukuId"));
            }else if(params.get("orderStatus").equals(1)){
                chukuService.cancel((String)params.get("chukuId"));
            }
            return ResultVOUtil.success("审核成功");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "审核错误");
        }
    }
}
