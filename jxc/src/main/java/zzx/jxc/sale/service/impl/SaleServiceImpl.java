package zzx.jxc.sale.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzx.jxc.sale.dao.SaleDao;
import zzx.jxc.sale.service.SaleService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleDao saleDao;

    @Override
    public Integer countBySaleIdLike() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String timeStr=format.format(date);
        Integer saleMasterCount = saleDao.countBySaleIdLike("%" + timeStr + "%");
        return saleMasterCount;
    }
}
