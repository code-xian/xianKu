package zzx.jxc.stockLog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.VO.ResultVO;
import zzx.jxc.stockLog.entity.StockLog;
import zzx.jxc.stockLog.service.LogService;
import zzx.jxc.util.ResultVOUtil;

@RestController
@RequestMapping("/stockLog")
public class StockLogController {
    @Autowired
    private LogService logService;
    @GetMapping("/list")
    @CrossOrigin(origins = "*")
    public ResultVO list(@RequestParam(required = false) String documentNumber,
                         @RequestParam(required = false) String stockName,
                         @RequestParam(required = false) Integer documentType,
                         @RequestParam Integer page,
                         @RequestParam Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page-1, size);
            StockLog stockLog = new StockLog();
            stockLog.setDocumentNumber(documentNumber);
            stockLog.setDocumentType(documentType);
            stockLog.setStockName(stockName);
            Page<StockLog> list = logService.findList(stockLog, pageRequest);
            return ResultVOUtil.success(list, "ok");
        } catch (Exception e) {
            return ResultVOUtil.error(1,"查询日志列表失败");
        }
    }
}
