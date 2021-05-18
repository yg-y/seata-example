package com.young.seata.feign.fallback;

import com.young.seata.feign.OrderServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderServiceFeignFallback implements OrderServiceFeign {

    @Override
    public String save() throws Exception {
        log.error("OrderServiceFeignFallback , save method fail!");
        throw new Exception("OrderServiceFeignFallback , save method fail!");
    }
}
