package com.young.seata.controller;


import com.young.seata.entity.CommodityOrder;
import com.young.seata.service.ICommodityOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
@Slf4j
@RestController
@RequestMapping("/order")
public class CommodityOrderController {

    @Autowired
    ICommodityOrderService iCommodityOrderService;

    @PostMapping("save")
    public String save() throws InterruptedException {
        CommodityOrder order = new CommodityOrder();
        order.setPrice(new BigDecimal("1.02"));
        order.setCommodityId(1);
        order.setBuyTime(System.currentTimeMillis());
        order.setCreateTime(order.getBuyTime());
        order.setUpdateTime(order.getBuyTime());
        Thread.sleep(10000);
        iCommodityOrderService.save(order);
        log.info("class: CommodityOrderController , method : save , msg: run is ....");

        return "success";

    }
}
