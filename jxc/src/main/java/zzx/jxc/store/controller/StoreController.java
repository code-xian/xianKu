package zzx.jxc.store.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.store.entity.Store;
import zzx.jxc.store.service.StoreService;
import zzx.jxc.util.ResultVOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;


    /**
     * 查询列表
     * @param storeId
     * @param storeName
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findAll(@RequestParam(required = false) Integer storeId,
                            @RequestParam(required = false) String storeName,
                            @RequestParam Integer page, @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            Store store = new Store();
            store.setStoreId(storeId);
            store.setStoreName(storeName);
            Page<Store> all = storeService.findAll(store, pageRequest);
            return ResultVOUtil.success(all, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询错误");
        }
    }

    /**
     * 新增门店
     * @param store
     * @return
     */
    @PostMapping("/save")
    @CrossOrigin(origins = "*")
    public ResultVO save(@RequestBody Store store) {
        try {
            storeService.save(store);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "新增失败");
        }
    }

    /**
     * 删除门店
     * @param store
     * @return
     */
    @PostMapping("/delete")
    @CrossOrigin(origins = "*")
    public ResultVO delete(@RequestBody Store store) {
        try {
            System.out.println(store.getStoreId());
            storeService.delete(store.getStoreId());
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "删除失败");
        }
    }

    /**
     * 门店下拉框列表
     * @return
     */
    @GetMapping("/list/storeName")
    @CrossOrigin(origins = "*")
    public ResultVO findStoreList() {
        try {
            List<Store> storeList = storeService.findAll();
            List<String> storeNameList = storeList.stream().map(Store::getStoreName).collect(Collectors.toList());
            List<Integer> storeIdList = storeList.stream().map(Store::getStoreId).collect(Collectors.toList());
            ArrayList<Map<String,Object>> list = new ArrayList();
            for (int key = 0; key < storeNameList.size(); key++) {
                Map<String, Object> result = new HashMap();
                result.put("value", storeIdList.get(key));
                result.put("label",storeNameList.get(key));
                list.add(result);
            }
            return ResultVOUtil.success(list, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询错误");
        }
    }
}
