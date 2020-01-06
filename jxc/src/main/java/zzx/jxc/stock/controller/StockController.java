package zzx.jxc.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.stock.entity.Stock;
import zzx.jxc.stock.service.StockService;
import zzx.jxc.util.ResultVOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;
    /**
     * 查询仓库列表
     */
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO findAll(
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String typeNumber,
                            @RequestParam Integer page,
                            @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Stock stock = new Stock();
            stock.setStockName(name);
            stock.setStockType(typeNumber);
            Page<Stock> stockList = stockService.findAll(stock, pageRequest);
            return ResultVOUtil.success(stockList, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询仓库错误");
        }
    }


    /**
     * 新增仓库
     */
    @PostMapping("/save")
    @CrossOrigin(origins = "*")
    public ResultVO save(@RequestBody Stock stock) {
        try {
            stockService.save(stock);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "新增仓库失败");
        }

    }

    /**
     * 删除仓库
     */
    @PostMapping("/delete")
    @CrossOrigin(origins = "*")
    public ResultVO delete(@RequestBody Stock stock) {
        try {
            stockService.delete(stock.getStockId());
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "删除仓库失败");
        }
    }

    /**
     * 查询下拉列表
     */
    @GetMapping("/list/stockName")
    @CrossOrigin(origins = "*")
    public ResultVO findStockList() {
        try {
            List<Stock> stockList = stockService.findAll();
            List<String> stockIdList = stockList.stream().map(e -> e.getStockId()).collect(Collectors.toList());
            List<String> stockNameList = stockList.stream().map(e -> e.getStockName()).collect(Collectors.toList());
            ArrayList<Map<String, String>> list = new ArrayList<>();
            for (int key = 0; key < stockList.size(); key++) {
                Map<String, String> result = new HashMap<>();
                result.put("value", stockIdList.get(key));
                result.put("label", stockNameList.get(key));
                list.add(result);
            }
            return ResultVOUtil.success(list, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询下拉列表失败");
        }
    }
}
