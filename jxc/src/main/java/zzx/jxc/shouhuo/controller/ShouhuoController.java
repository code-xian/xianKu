package zzx.jxc.shouhuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.VO.ShouhuoOrderInfoVO;
import zzx.jxc.purchase.entity.PurchaseMaster;
import zzx.jxc.shouhuo.entity.ShouhuoMaster;
import zzx.jxc.shouhuo.service.ShouhuoService;
import zzx.jxc.util.ResultVOUtil;

import java.util.Map;

@RestController
@RequestMapping("/shouhuo")
public class ShouhuoController {
    @Autowired
    private ShouhuoService shouhuoService;


    //新增发货单
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResultVO createOrder(@RequestBody PurchaseMaster purchaseMaster) {
        try {
            shouhuoService.create(purchaseMaster);
            return ResultVOUtil.success("创建收货单成功");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "创建收货单失败");
        }
    }

    //查询发货单列表
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findList(@RequestParam(required = false) String shouhuoId,
                             @RequestParam(required = false) String supplierName,
                             @RequestParam(required = false) Integer orderStatus,
                             @RequestParam Integer page,
                             @RequestParam Integer size
    ){
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            ShouhuoMaster shouhuoMaster = new ShouhuoMaster();
            shouhuoMaster.setShouhuoId(shouhuoId);
            shouhuoMaster.setSupplierName(supplierName);
            if(orderStatus == null){
                shouhuoMaster.setShouhuoStatus(null);
            }else{
                shouhuoMaster.setShouhuoStatus(orderStatus);
            }
            Page<ShouhuoMaster> shouhuoServiceList = shouhuoService.findList(shouhuoMaster, pageRequest);
            return ResultVOUtil.success(shouhuoServiceList, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询收货单列表错误");
        }
    }

    //查询单个订单详情
    @GetMapping("/detail")
    @CrossOrigin(origins = "*")
    public ResultVO findOne(@RequestParam String shouhuoId) {
        try {
            ShouhuoOrderInfoVO one = shouhuoService.findOne(shouhuoId);
            return ResultVOUtil.success(one, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询收货单错误");
        }
    }

    //审核订单
    @PostMapping("/audit")
    @CrossOrigin(origins = "*")
    public ResultVO audit(@RequestBody Map<String,Object> params) {  //取orderStatus和fahuoId
        try {
            if (params.get("orderStatus").equals(0)) {         //0 审核通过    1审核不通过
                shouhuoService.finish((String)params.get("shouhuoId"));
            }else if(params.get("orderStatus").equals(1)){
                shouhuoService.cancel((String)params.get("shouhuoId"));
            }
            return ResultVOUtil.success("审核成功");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "审核错误");
        }
    }
}
