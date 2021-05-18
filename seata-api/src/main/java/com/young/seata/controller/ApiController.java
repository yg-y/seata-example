package com.young.seata.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.young.seata.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author young
 * @version 1.0
 * @date 2021/3/8 4:54 下午
 * @description
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @PostMapping("/create/order")
    public R createOrder(){
        return apiService.createOrder();
    }

}
