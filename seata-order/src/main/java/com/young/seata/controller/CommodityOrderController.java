package com.young.seata.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.young.seata.entity.CommodityOrder;
import com.young.seata.service.ICommodityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author young
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/order")
public class CommodityOrderController {

    @Autowired
    ICommodityOrderService iCommodityOrderService;

    @GetMapping("save")
    public R save() {
        CommodityOrder order = new CommodityOrder();
        order.setPrice(new BigDecimal("1.02"));
        order.setCommodityId(1);
        order.setBuyTime(System.currentTimeMillis());
        order.setCreateTime(order.getBuyTime());
        order.setUpdateTime(order.getBuyTime());

        iCommodityOrderService.save(order);

        return R.ok("success");

    }
}
