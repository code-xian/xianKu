package zzx.jxc.sale.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zzx.jxc.VO.SaleFoodSelectListVO;
import zzx.jxc.VO.SaleOrderDetailVO;
import zzx.jxc.dto.SaleOrderDTO;
import zzx.jxc.sale.entity.SaleMaster;

public interface SaleService {
    //查询今日的订单数,以便添加订单编号
    Integer countBySaleIdLike();

    //创建订单
    SaleOrderDTO create(SaleOrderDTO saleOrderDTO);

    //查询单个订单
    SaleOrderDetailVO findOne(String saleId);

    //查询订单列表
    Page<SaleMaster> findList(SaleMaster saleMaster, Pageable pageable);

    //订单未通过审核
    void cancel(SaleOrderDTO saleOrderDTO);

    //订单通过审核
    SaleOrderDTO finish(SaleOrderDTO saleOrderDTO);

    //查询食品列表
    Page<SaleFoodSelectListVO> findListAll(String stockName, Pageable pageable, String CategoryId, String foodName);
}
