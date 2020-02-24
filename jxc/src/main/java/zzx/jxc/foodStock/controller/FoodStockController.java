package zzx.jxc.foodStock.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.FoodStockVO;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.foodStock.service.FoodStockService;
import zzx.jxc.util.ResultVOUtil;

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
    public ResultVO findAll(@RequestParam String stockId,
                            @RequestParam(required = false) String categoryId,
                            @RequestParam Integer page,
                            @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            if(categoryId.equals("")||categoryId.isEmpty()){
                categoryId = "";
            }
            Page<FoodStockVO> all = stockService.findAll(stockId, pageRequest, categoryId);
            return ResultVOUtil.success(all, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询库存错误");
        }
    }
}
