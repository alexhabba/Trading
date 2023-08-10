package com.example.Trade;

import com.example.Trade.service.BarHiberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@EnableScheduling
@SpringBootApplication
public class TradingApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext run = SpringApplication.run(TradingApplication.class, args);
        BarHiberService barHiberService = run.getBean("barHiberService", BarHiberService.class);

//        barHiberService.createBarOneSeconds();



    }

}
