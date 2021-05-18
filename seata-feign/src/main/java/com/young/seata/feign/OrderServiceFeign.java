package com.young.seata.feign;

import com.young.seata.feign.fallback.OrderServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author young
 * @version 1.0
 * @date 2021/5/17 4:22 下午
 * @description
 */
@FeignClient(value = "seata-order", fallback = OrderServiceFeignFallback.class)
public interface OrderServiceFeign {

    @RequestMapping(value = "/order/save", method = RequestMethod.POST)
    String save() throws Exception;
}
