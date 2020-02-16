package zzx.jxc.foodCategory.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.foodCategory.entity.FoodCategory;
import zzx.jxc.foodCategory.service.FoodCategoryService;
import zzx.jxc.util.ResultVOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/foodCategory")
public class FoodCategoryController {
    @Autowired
    private FoodCategoryService foodCategoryService;
    /**
     * 查询食物类别列表
     */
//    @GetMapping("/list")
//    @CrossOrigin(origins = "*")
//    public ResultVO findAll(
//            @RequestParam(required = false) String stockId,
////            @RequestParam(required = false) String typeNumber,
//            @RequestParam Integer page,
//            @RequestParam Integer size) {
//        try {
//            PageRequest pageRequest = PageRequest.of(page-1, size);
////            Stock stock = new Stock();
//            FoodCategory foodCategory = new FoodCategory();
////            stock.setStockName(name);
//            foodCategory.setStockId(stockId);
////            if(typeNumber.equals("全部")){
////                stock.setStockType("");
////            }else{
////                stock.setStockType(typeNumber);
////            }
//            Page<FoodCategory> foodCategoryList = foodCategoryService.findAll(foodCategory, pageRequest);
//            return ResultVOUtil.success(foodCategoryList, "ok");
//        } catch (Exception e) {
//            return ResultVOUtil.error(1,"查询仓库错误");
//        }
//    }


    /**
     * 新增食物类别
     */
//    @PostMapping("/save")
//    @CrossOrigin(origins = "*")
//    public ResultVO save(@RequestBody FoodCategory foodCategory) {
//        try {
//            Integer count = (int)foodCategoryService.count();
//            String foodCategoryId = CommonIdUtil.commonId(count);
//            foodCategory.setStockId(foodCategoryId);
//            foodCategoryService.save(foodCategory);
//            return ResultVOUtil.success("ok");
//        } catch (Exception e) {
//            return ResultVOUtil.error(1, "新增食物类别失败");
//        }
//    }

    /**
     * 删除类别
     */
//    @PostMapping("/delete")
//    @CrossOrigin(origins = "*")
//    public ResultVO delete(@RequestBody Stock stock) {
//        try {
//            stockService.delete(stock.getStockId());
//            return ResultVOUtil.success("ok");
//        } catch (Exception e) {
//            return ResultVOUtil.error(1, "删除仓库失败");
//        }
//    }

    /**
     * 查询下拉列表
     */
    @GetMapping("/list/foodCategoryName")
    @CrossOrigin(origins = "*")
    public ResultVO findStockList() {
        try {
            List<FoodCategory> foodCategoryList = foodCategoryService.findAll();
            List<String> categoryIdList = foodCategoryList.stream().map(FoodCategory::getCategoryId).collect(Collectors.toList());
            List<String> foodTypeList = foodCategoryList.stream().map(FoodCategory::getCategoryName).collect(Collectors.toList());
            ArrayList<Map<String, String>> list = new ArrayList<>();
            for (int key = 0; key < foodCategoryList.size(); key++) {
                Map<String, String> result = new HashMap<>();
                result.put("value", categoryIdList.get(key));
                result.put("label", foodTypeList.get(key));
                list.add(result);
            }
            return ResultVOUtil.success(list, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询下拉列表失败");
        }
    }
}
