package com.young.seata;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeataCommodityApplicationTests {

    @Value("$server.port")
    String value;

    @Test
    public void contextLoads() {
        System.err.println(value);
    }

    @Autowired
    @NacosInjected
    private ConfigService configService;

    @Test
    public void testPublishUser() throws NacosException {
        System.err.println(configService.getConfig("service.vgroupMapping.my_test_tx_group", "DEFAULT_GROUP", 3000l));
    }
}
