package zzx.jxc.purchase.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.VO.PurchaseFoodSelectListVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.dto.PurchaseOrderDTO;
import zzx.jxc.purchase.entity.PurchaseMaster;
import zzx.jxc.purchase.service.PurchaseService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PurchaseServiceImplTest {

    @Autowired
    private PurchaseService purchaseService;
    @Test
    void countByPurchaseIdLike() {
    }

    @Test
    void create() throws ParseException {
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1 = dateFormat1.parse("2020-06-01");
        purchaseOrderDTO.setReviewer("李四");
        purchaseOrderDTO.setPurchaseRemarks("有有有有有有有有有有有有有有有有");
        purchaseOrderDTO.setSupplierId(2);
        purchaseOrderDTO.setSubmissionDate(myDate1);
        purchaseOrderDTO.setSubmissionWay("物流");
        List<OrderCartDTO> orderCartDTOS = new ArrayList<>();
        OrderCartDTO orderCartDTO = new OrderCartDTO();
        orderCartDTO.setFoodId("001");
        orderCartDTO.setStockId("001");
        orderCartDTO.setSaleQuantity(10);
        OrderCartDTO orderCartDTO2 = new OrderCartDTO();
        orderCartDTO2.setFoodId("002");
        orderCartDTO2.setStockId("001");
        orderCartDTO2.setSaleQuantity(10);
        orderCartDTOS.add(orderCartDTO);
        orderCartDTOS.add(orderCartDTO2);
        purchaseOrderDTO.setPurchaseDetailList(orderCartDTOS);
        purchaseService.create(purchaseOrderDTO);
    }

    @Test
    void findOne() {
    }

    @Test
    void findList() {
        PurchaseMaster purchaseMaster = new PurchaseMaster();
        Page<PurchaseMaster> list = purchaseService.findList(purchaseMaster, PageRequest.of(0, 10));
    }

    @Test
    void cancel() {
    }

    @Test
    void finish() {
    }

    @Test
    void findListAll() {
        Page<PurchaseFoodSelectListVO> listAll = purchaseService.findListAll(PageRequest.of(1, 5), "", "");
    }
}