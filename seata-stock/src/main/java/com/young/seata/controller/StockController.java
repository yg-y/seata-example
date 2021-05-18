package com.young.seata.controller;


import com.young.seata.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author young
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    IStockService iStockService;

    @GetMapping("get")
    public Object get() {
        return iStockService.list();
    }

}
