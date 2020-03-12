package zzx.jxc.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.stock.entity.Stock;
import zzx.jxc.stock.service.StockService;
import zzx.jxc.util.CommonIdUtil;
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
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            Stock stock = new Stock();
            stock.setStockName(name);
            if (typeNumber.equals("全部")) {
                stock.setStockType("");
            } else {
                stock.setStockType(typeNumber);
            }
            Page<Stock> stockList = stockService.findAll(stock, pageRequest);
            return ResultVOUtil.success(stockList, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询仓库错误");
        }
    }


    /**
     * 新增仓库
     */
    @PostMapping("/save")
    @CrossOrigin(origins = "*")
    public ResultVO save(@RequestBody Stock stock) {
        try {
            Integer count = (int) stockService.count();
            String stockId = CommonIdUtil.commonId(count);
            stock.setStockId(stockId);
            stockService.save(stock);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "新增仓库失败");
        }
    }

    /**
     * 废弃仓库
     */
    @PostMapping("/delete")
    @CrossOrigin(origins = "*")
    public ResultVO delete(@RequestBody Stock stock) {
        try {
            Stock oneById = stockService.findOneById(stock.getStockId());
            oneById.setStockStatus(1);
            stockService.delete(oneById);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "废弃仓库失败");
        }
    }

    /**
     * 启用仓库
     */
    @PostMapping("/enable")
    @CrossOrigin(origins = "*")
    public ResultVO enable(@RequestBody Stock stock) {
        try {
            Stock oneById = stockService.findOneById(stock.getStockId());
            oneById.setStockStatus(0);
            stockService.enable(oneById);
            return ResultVOUtil.success("ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "启用仓库失败");
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
//            List<String> stockIdList = stockList.stream().map(Stock::getStockId).collect(Collectors.toList());
            List<String> stockTypeList = stockList.stream().map(Stock::getStockType).collect(Collectors.toList());
            ArrayList<Map<String, String>> list = new ArrayList<>();
            for (int key = 0; key < stockList.size(); key++) {
                Map<String, String> result = new HashMap<>();
//                result.put("value", stockIdList.get(key));
                result.put("label", stockTypeList.get(key));
                list.add(result);
            }
            return ResultVOUtil.success(list, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1, "查询下拉列表失败");
        }
    }

    /**
     * 查询单个仓库
     */
    @GetMapping("/detail")
    @CrossOrigin(origins = "*")
    public ResultVO findOne(@RequestParam(required = false) String stockId) {
        try {
            Stock oneById = stockService.findOneById(stockId);
            return ResultVOUtil.success(oneById, "ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(1, "查询单个仓库详情错误");
        }

    }
}
