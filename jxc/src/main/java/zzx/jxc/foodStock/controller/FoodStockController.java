package zzx.jxc.foodStock.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.FoodStockVO;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.foodStock.service.FoodStockService;
import zzx.jxc.util.ResultVOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/foodStock")
public class FoodStockController {

    @Autowired
    private FoodStockService stockService;

    /**
     * 查询仓库库存食品
     */
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findAll(@RequestParam(required = false) String stockId,
                            @RequestParam(required = false) String categoryId,
                            @RequestParam Integer page,
                            @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            if (categoryId.equals("") || categoryId.isEmpty() || categoryId == null) {
                categoryId = "";
            }
            Page<FoodStockVO> all = stockService.findAll(stockId, pageRequest, categoryId);
            return ResultVOUtil.success(all, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询库存错误");
        }
    }

    @PostMapping("/move")
    @CrossOrigin(origins = "*")
    @Transactional
    public ResultVO move(@RequestBody Map<String,Object> params) {
        try {
            Gson gson = new Gson();
            Object list1 = params.get("inList");
            List<OrderCartDTO> orderCartDTOS = new ArrayList<OrderCartDTO>();
            String s = gson.toJson(list1);
            orderCartDTOS = gson.fromJson(s, new TypeToken<List<OrderCartDTO>>() {
            }.getType());
            List<OrderCartDTO> oldstock= orderCartDTOS.stream().map(e -> new OrderCartDTO(e.getFoodId(), e.getSaleQuantity(),(String)params.get("stockId"))).collect(Collectors.toList());
            for (OrderCartDTO orderCartDTO : orderCartDTOS) {
                if(orderCartDTO.getSaleQuantity().equals("")||orderCartDTO.getSaleQuantity()==null||orderCartDTO.getClass().getName().equals("saleQuantity")){
                    return ResultVOUtil.error(1, "请填写所需要转移的数量");
                }if(orderCartDTO.getStockId().equals("")||orderCartDTO.getStockId()==null||orderCartDTO.getClass().getName().equals("stockId")){
                    return ResultVOUtil.error(1, "请填写所需要转移的仓库");
                }
            }
            stockService.decreaseStock(oldstock);
            stockService.increaseStock(orderCartDTOS);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "移库错误");
        }
    }
}
