package zzx.jxc.supplier.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.supplier.entity.Supplier;
import zzx.jxc.supplier.service.SupplierService;
import zzx.jxc.util.ResultVOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    /**
     * 查询列表
     */
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findAll(@RequestParam(required = false) String supplierName,
                            @RequestParam(required = false) String supplierType,
                            @RequestParam Integer page,
                            @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Supplier supplier = new Supplier();
            supplier.setSupplierName(supplierName);
            supplier.setSupplierType(supplierType);
            Page<Supplier> all = supplierService.findAll(supplier, pageRequest);
            return ResultVOUtil.success(all, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询错误");
        }
    }

    /**
     * 新增供应商
     */
    @PostMapping("/save")
    @CrossOrigin(origins = "*")
    public ResultVO save(@RequestBody Supplier supplier) {
        try {
            supplierService.save(supplier);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "新增失败");
        }
    }

    /**
     * 删除供应商
     */
    @PostMapping("/delete")
    @CrossOrigin(origins = "*")
    public ResultVO delete(@RequestBody Supplier supplier) {
        try {
            supplierService.delete(supplier.getSupplierId());
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "删除失败");
        }
    }

    /**
     * 查询下拉列表
     */
    @GetMapping("/list/supplierName")
    @CrossOrigin(origins = "*")
    public ResultVO findSupplierList() {
        try {
            List<Supplier> supplierList = supplierService.findAll();
            List<String> supplierNameList = supplierList.stream().map(Supplier::getSupplierName).collect(Collectors.toList());
            List<String> supplierIdList = supplierList.stream().map(Supplier::getSupplierId).collect(Collectors.toList());
            ArrayList<Map<String,String>> list = new ArrayList();
            for (int key = 0; key < supplierNameList.size(); key++) {
                Map<String, String> result = new HashMap();
                result.put("value", supplierIdList.get(key));
                result.put("label",supplierNameList.get(key));
                list.add(result);
            }
            return ResultVOUtil.success(list, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询错误");
        }
    }
}
