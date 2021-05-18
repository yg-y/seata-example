package com.young.seata.controller;


import com.young.seata.entity.Commodity;
import com.young.seata.service.ICommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    ICommodityService iCommodityService;

    /**
     * 新增商品数据
     *
     * @param commodity
     * @return
     */
    @PostMapping("/add")
    public String addCommodity(@RequestBody Commodity commodity) throws Exception {
        return iCommodityService.addCommodity(commodity);
    }

}
