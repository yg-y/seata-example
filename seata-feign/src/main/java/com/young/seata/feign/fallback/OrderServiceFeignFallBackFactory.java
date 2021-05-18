package com.young.seata.feign.fallback;

import com.young.seata.feign.OrderServiceFeign;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFeignFallBackFactory implements FallbackFactory<OrderServiceFeign> {
    private final OrderServiceFeignFallback orderServiceFeignFallback;

    public OrderServiceFeignFallBackFactory(OrderServiceFeignFallback orderServiceFeignFallback) {
        this.orderServiceFeignFallback = orderServiceFeignFallback;
    }

    @Override
    public OrderServiceFeign create(Throwable throwable) {
        throwable.printStackTrace();
        return orderServiceFeignFallback;
    }
}
