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
