package zzx.jxc.sale.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zzx.jxc.VO.SaleFoodSelectListVO;
import zzx.jxc.VO.SaleOrderInfoVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.dto.SaleOrderDTO;
import zzx.jxc.sale.entity.SaleMaster;
import zzx.jxc.sale.service.SaleService;
import zzx.jxc.util.ddbhUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SaleServiceImplTest {
    @Autowired
    private SaleService saleService;
    @Test
    void countBySaleIdLike() {
        Integer integer = saleService.countBySaleIdLike();
        System.out.println(integer);
        String xsdd = ddbhUtil.xsdd(integer,"XSDD");
        System.out.println(xsdd);
    }

    @Test
    void create() throws ParseException {
        SaleOrderDTO saleOrderDTO = new SaleOrderDTO();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1 = dateFormat1.parse("2020-06-01");
        saleOrderDTO.setReviewer("李四");
        saleOrderDTO.setSaleRemarks("不加级加急");
        saleOrderDTO.setStoreId(15);
        saleOrderDTO.setSubmissionDate(myDate1);
        saleOrderDTO.setSubmissionWay("物流");
        List<OrderCartDTO> orderCartDTOS = new ArrayList<>();
        OrderCartDTO orderCartDTO = new OrderCartDTO();
        orderCartDTO.setFoodId("001");
        orderCartDTO.setStockId("001");
        orderCartDTO.setSaleQuantity(10);
        orderCartDTOS.add(orderCartDTO);
        saleOrderDTO.setSaleDetailList(orderCartDTOS);
        SaleOrderDTO saleOrderDTO1 = saleService.create(saleOrderDTO);
        System.out.println(saleOrderDTO1);
    }

    @Test
    void findOne() {
        SaleOrderInfoVO one = saleService.findOne("XSDD20200303001");
        System.out.println(one);
    }

    @Test
    void findList() {
        SaleMaster saleMaster = new SaleMaster();
        saleMaster.setSaleStatus(null);
        Page<SaleMaster> list = saleService.findList(saleMaster, PageRequest.of(0, 5));
        System.out.println(list);
    }

    @Test
    void cancel() {

    }

    @Test
    void finish() {
    }

    @Test
    void findListAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<SaleFoodSelectListVO> listAll = saleService.findListAll("", pageRequest, "", "");
        System.out.println(listAll);
    }
}