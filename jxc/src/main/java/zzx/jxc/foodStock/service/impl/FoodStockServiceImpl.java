package zzx.jxc.foodStock.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzx.jxc.VO.FoodStockVO;
import zzx.jxc.dto.OrderCartDTO;
import zzx.jxc.enums.ResultEnum;
import zzx.jxc.exception.SellException;
import zzx.jxc.foodCategory.dao.FoodCategoryDao;
import zzx.jxc.foodCategory.entity.FoodCategory;
import zzx.jxc.foodInfo.dao.FoodInfoDao;
import zzx.jxc.foodInfo.entity.FoodInfo;
import zzx.jxc.foodInfo.service.FoodInfoService;
import zzx.jxc.foodStock.Dao.FoodStockDao;
import zzx.jxc.foodStock.entity.FoodStock;
import zzx.jxc.foodStock.service.FoodStockService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FoodStockServiceImpl implements FoodStockService {

    @Autowired
    private FoodStockDao foodStockDao;
    @Autowired
    private FoodInfoDao foodInfoDao;
    @Autowired
    private FoodCategoryDao foodCategoryDao;
    @Autowired
    private FoodInfoService foodInfoService;

    /**
     * 根据仓库Id,在FookStock表查食品ids,拿食品ids去查食品列表,
     * 遍历食品列表id 获取库存
     * 食品列表 category_id 获取类目
     * @param stockId
     * @param pageable
     * @param categoryId
     * @return
     */
    @Override
    public Page<FoodStockVO> findAll(String stockId, Pageable pageable, String categoryId) {
        List<FoodStock> allByStockPage = foodStockDao.findAllByStockId(stockId);
        List<String> foodIds = allByStockPage.stream().map(FoodStock::getFoodId).collect(Collectors.toList());
        List<FoodInfo> allByFoodIdIn = foodInfoDao.findAllByFoodIdIn(foodIds);
        List<FoodStockVO> foodStockVOS = new ArrayList<>();
        for (FoodInfo foodInfo : allByFoodIdIn) {
            if(!categoryId.isEmpty()||!categoryId.equals("")){
                if (!categoryId.equals(foodInfo.getCategoryId())) {
                    continue;
                }
            }
            FoodStockVO foodStockVO = new FoodStockVO();
            BeanUtils.copyProperties(foodInfo, foodStockVO);
            Integer foodStock = foodStockDao.findStockByFoodIdAndStockId(foodInfo.getFoodId(), stockId);
            foodStockVO.setStock(foodStock);
            FoodCategory foodCategoryByCategoryId = foodCategoryDao.findFoodCategoryByCategoryId(foodInfo.getCategoryId());
            foodStockVO.setCategoryName(foodCategoryByCategoryId.getCategoryName());
            foodStockVOS.add(foodStockVO);
        }
        int size = foodStockVOS.size();
        Page<FoodStockVO> foodStockVOPage = new PageImpl<FoodStockVO>(foodStockVOS,pageable,size);  //todo
        return foodStockVOPage;
    }

    @Override
    @Transactional
    public void decreaseStock(List<OrderCartDTO> cartDTOList) {
        for (OrderCartDTO orderCartDTO : cartDTOList) {
            FoodInfo oneById = foodInfoService.findOneById(orderCartDTO.getFoodId());
            if (oneById == null) {
                throw new SellException(ResultEnum.FOOD_NOT_EXIST);
            }
            Integer foodStockNumber = foodStockDao.findStockByFoodIdAndStockId(orderCartDTO.getFoodId(), orderCartDTO.getStockId());
            Integer result = foodStockNumber - orderCartDTO.getSaleQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.FOOD_STOCK_ERROR);
            }
            FoodStock foodStockByFoodIdAndStockId = foodStockDao.findFoodStockByFoodIdAndStockId(orderCartDTO.getFoodId(), orderCartDTO.getStockId());
            foodStockByFoodIdAndStockId.setStock(result);
            foodStockDao.save(foodStockByFoodIdAndStockId);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<OrderCartDTO> cartDTOList) {
        for (OrderCartDTO orderCartDTO : cartDTOList) {
            FoodInfo oneById = foodInfoService.findOneById(orderCartDTO.getFoodId());
            if (oneById == null) {
                throw new SellException(ResultEnum.FOOD_NOT_EXIST);
            }
            FoodStock foodStockByFoodIdAndStockId1 = foodStockDao.findFoodStockByFoodIdAndStockId(orderCartDTO.getFoodId(), orderCartDTO.getStockId());
            if (foodStockByFoodIdAndStockId1 == null) {
                FoodStock foodStock = new FoodStock();
                foodStock.setFoodId(orderCartDTO.getFoodId());
                foodStock.setStockId(orderCartDTO.getStockId());
                foodStock.setStock(orderCartDTO.getSaleQuantity());
                foodStockDao.save(foodStock);
            }else{
                Integer foodStockNumber = foodStockDao.findStockByFoodIdAndStockId(orderCartDTO.getFoodId(), orderCartDTO.getStockId());
                Integer result = foodStockNumber + orderCartDTO.getSaleQuantity();
                FoodStock foodStockByFoodIdAndStockId = foodStockDao.findFoodStockByFoodIdAndStockId(orderCartDTO.getFoodId(), orderCartDTO.getStockId());
                foodStockByFoodIdAndStockId.setStock(result);
                foodStockDao.save(foodStockByFoodIdAndStockId);
            }

        }
    }



}
