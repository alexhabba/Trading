package com.example.Trade;

import com.example.Trade.model.Tick;
import com.example.Trade.repository.BarRepository;
import com.example.Trade.service.impl.Bar;
import com.example.Trade.service.impl.BarPrice;
import com.example.Trade.service.impl.FileOperations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@SpringBootApplication
public class TradingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TradingApplication.class, args);
        BarRepository barRepository = run.getBean("barRepository", BarRepository.class);

        List<Tick> ticks = FileOperations.getListTickFromFile(Path.of("TICKS/S&P/s&p_2023.07.10.txt"));

        List<Tick> collect = ticks.stream()
                .filter(t -> t.getTime().contains("17:52:"))
                .collect(Collectors.toList());


        Bar bar = Bar.builder()
                .close(1.2)
                .open(1.2)
                .high(1.2)
                .low(1.2)
                .interval(60)
                .volumeBuy(150)
                .volumeSell(350)
                .barPrices(List.of(BarPrice.builder().price(1.2).volumeBuy(12).volumeSell(122).build()))
                .build();
        Bar save = barRepository.save(bar);
        System.out.println();
    }

}
