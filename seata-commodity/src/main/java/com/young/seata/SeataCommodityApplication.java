package com.young.seata;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RequestMapping("/app")
@RestController
@EnableTransactionManagement
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SeataCommodityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataCommodityApplication.class, args);
    }

}
