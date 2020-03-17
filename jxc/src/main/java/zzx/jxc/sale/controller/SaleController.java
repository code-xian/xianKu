package zzx.jxc.sale.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.VO.SaleFoodSelectListVO;
import zzx.jxc.VO.SaleOrderInfoVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.dto.SaleOrderDTO;
import zzx.jxc.sale.entity.SaleMaster;
import zzx.jxc.sale.service.SaleService;
import zzx.jxc.util.ResultVOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;


    //查询库存食品添加列表
    @GetMapping("/foodList")
    @CrossOrigin(origins = "*")
    public ResultVO findFoodList(@RequestParam(required = false) String stockName,
                                 @RequestParam(required = false) String categoryId,
                                 @RequestParam(required = false) String foodName,
                                 @RequestParam Integer page,
                                 @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            if(categoryId.equals("")||categoryId.isEmpty()){
                categoryId = "";
            }
            Page<SaleFoodSelectListVO> all = saleService.findListAll(stockName, pageRequest, categoryId,foodName);
            return ResultVOUtil.success(all, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询库存食品列表失败");
        }

    }

    //新增供应订单
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResultVO createOrder(@RequestBody SaleOrderDTO saleOrderDTO) {
        try {
            saleService.create(saleOrderDTO);
            return ResultVOUtil.success("创建供应订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "创建供应订单失败");
        }
    }

    //查询供应订单列表
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findList(@RequestParam(required = false) String saleId,
                             @RequestParam(required = false) String storeName,
                             @RequestParam(required = false) Integer orderStatus,
                             @RequestParam Integer page,
                             @RequestParam Integer size
                             ){
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            SaleMaster saleMaster = new SaleMaster();
            saleMaster.setSaleId(saleId);
            saleMaster.setStoreName(storeName);
            if(orderStatus == null){
                saleMaster.setSaleStatus(null);
            }else{
                saleMaster.setSaleStatus(orderStatus);
            }
            Page<SaleMaster> saleMasterList = saleService.findList(saleMaster, pageRequest);
            return ResultVOUtil.success(saleMasterList, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询供应订单列表错误");
        }
    }

    //查询单个订单详情
    @GetMapping("/detail")
    @CrossOrigin(origins = "*")
    public ResultVO findOne(@RequestParam String saleId) {
        try {
            SaleOrderInfoVO one = saleService.findOne(saleId);
            return ResultVOUtil.success(one, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询供应订单错误");
        }
    }

    //审核订单
    @PostMapping("/audit")
    @CrossOrigin(origins = "*")
    public ResultVO audit(@RequestBody Map<String,Object> params) {
        try {
            Gson gson = new Gson();
            SaleOrderDTO saleOrderDTO = new SaleOrderDTO();
            saleOrderDTO.setSaleId((String) params.get("saleId"));
            Object list1 = params.get("list");
            List<OrderCartDTO> orderCartDTOS = new ArrayList<>();
            String s = gson.toJson(list1);
            //特殊符号 需要replace
            orderCartDTOS = gson.fromJson(s, new TypeToken<List<OrderCartDTO>>() {
            }.getType());
            saleOrderDTO.setSaleDetailList(orderCartDTOS);
            if (params.get("orderStatus").equals(0)) {     //0 审核通过    1审核不通过
                saleService.finish(saleOrderDTO);
            }else if(params.get("orderStatus").equals(1)){
                saleService.cancel(saleOrderDTO);
            }
            return ResultVOUtil.success("审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "审核错误");
        }
    }


}
