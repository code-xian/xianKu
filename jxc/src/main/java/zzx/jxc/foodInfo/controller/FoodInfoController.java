package zzx.jxc.foodInfo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.foodInfo.entity.FoodInfo;
import zzx.jxc.foodInfo.service.FoodInfoService;
import zzx.jxc.util.CommonIdUtil;
import zzx.jxc.util.ResultVOUtil;

@RestController
@RequestMapping("/foodInfo")
public class FoodInfoController {

    @Autowired
    private FoodInfoService foodInfoService;

    /**
     * 查询食品列表
     */
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findAll(
            @RequestParam(required = false) String foodName,
            @RequestParam(required = false) String categoryId,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
//            Stock stock = new Stock();
            FoodInfo foodInfo = new FoodInfo();
            foodInfo.setFoodName(foodName);
            foodInfo.setCategoryId(categoryId);
//            if(categoryId.equals("")){
//                stock.setStockType("");
//            }else{
//                stock.setStockType(typeNumber);
//            }
            Page<FoodInfo> foodInfoList = foodInfoService.findAll(foodInfo, pageRequest);
            return ResultVOUtil.success(foodInfoList, "ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1,"查询食品列表错误");
        }
    }


    /**
     * 新增食品
     */
    @PostMapping("/save")
    @CrossOrigin(origins = "*")
    public ResultVO save(@RequestBody FoodInfo foodInfo) {
        try {
            Integer count = (int)foodInfoService.count();
            String foodId = CommonIdUtil.commonId(count);
            foodInfo.setFoodId(foodId);
            foodInfoService.save(foodInfo);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "新增食品失败");
        }
    }

    /**
     * 下架食品
     */
    @PostMapping("/delete")
    @CrossOrigin(origins = "*")
    public ResultVO delete(@RequestBody FoodInfo foodInfo) {
        try {
            FoodInfo oneById = foodInfoService.findOneById(foodInfo.getFoodId());
            oneById.setFoodStatus(1);
            foodInfoService.delete(oneById);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "下架食品失败");
        }
    }
}
