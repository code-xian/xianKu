package zzx.jxc.purchase.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.PurchaseFoodSelectListVO;
import zzx.jxc.VO.PurchaseOrderInfoVO;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.dto.PurchaseOrderDTO;
import zzx.jxc.purchase.entity.PurchaseMaster;
import zzx.jxc.purchase.service.PurchaseService;
import zzx.jxc.util.ResultVOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    //查询食品添加列表
    @GetMapping("/foodList")
    @CrossOrigin(origins = "*")
    public ResultVO findFoodList(@RequestParam(required = false) String categoryId,
                                 @RequestParam(required = false) String foodName,
                                 @RequestParam Integer page,
                                 @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            if(categoryId.equals("")||categoryId.isEmpty()){
                categoryId = "";
            }
            PagedListHolder<PurchaseFoodSelectListVO> all = purchaseService.findListAll(pageRequest, categoryId,foodName);
            return ResultVOUtil.success(all, "ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1,"查询食品列表失败");
        }

    }

    //新增供应订单
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResultVO createOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        try {
            purchaseService.create(purchaseOrderDTO);
            return ResultVOUtil.success("创建采购订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "创建采购订单失败");
        }
    }

    //查询供应订单列表
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findList(@RequestParam(required = false) String purchaseId,
                             @RequestParam(required = false) String supplierName,
                             @RequestParam(required = false) Integer orderStatus,
                             @RequestParam Integer page,
                             @RequestParam Integer size
    ){
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createTime"));
            PurchaseMaster purchaseMaster = new PurchaseMaster();
            purchaseMaster.setPurchaseId(purchaseId);
            purchaseMaster.setSupplierName(supplierName);
            if(orderStatus == null){
                purchaseMaster.setPurchaseStatus(null);
            }else{
                purchaseMaster.setPurchaseStatus(orderStatus);
            }
            Page<PurchaseMaster> purchaseMasterList = purchaseService.findList(purchaseMaster, pageRequest);
            return ResultVOUtil.success(purchaseMasterList, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询采购订单列表错误");
        }
    }

    //查询单个订单详情
    @GetMapping("/detail")
    @CrossOrigin(origins = "*")
    public ResultVO findOne(@RequestParam String purchaseId) {
        try {
            PurchaseOrderInfoVO one = purchaseService.findOne(purchaseId);
            return ResultVOUtil.success(one, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询采购订单详情错误");
        }
    }

    //审核订单
    @PostMapping("/audit")
    @CrossOrigin(origins = "*")
    public ResultVO audit(@RequestBody Map<String,Object> params) {
        try {
            Gson gson = new Gson();
            PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
            purchaseOrderDTO.setPurchaseId((String) params.get("purchaseId"));
            Object list1 = params.get("list");
            List<OrderCartDTO> orderCartDTOS = new ArrayList<OrderCartDTO>();
            String s = gson.toJson(list1);
            orderCartDTOS = gson.fromJson(s, new TypeToken<List<OrderCartDTO>>() {
            }.getType());

            purchaseOrderDTO.setPurchaseDetailList(orderCartDTOS);
            if (params.get("orderStatus").equals(0)) {     //0 审核通过    1审核不通过
                purchaseService.finish(purchaseOrderDTO);
            }else if(params.get("orderStatus").equals(1)){
                purchaseService.cancel(purchaseOrderDTO);
            }
            return ResultVOUtil.success("审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "审核错误");
        }
    }
}
