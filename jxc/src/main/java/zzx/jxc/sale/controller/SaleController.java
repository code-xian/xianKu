package zzx.jxc.sale.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.VO.SaleFoodSelectListVO;
import zzx.jxc.sale.service.SaleService;
import zzx.jxc.util.ResultVOUtil;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;


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
}
