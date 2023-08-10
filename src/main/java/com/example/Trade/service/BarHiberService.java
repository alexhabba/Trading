package com.example.Trade.service;

import com.example.Trade.Symbol;
import com.example.Trade.entity.Bar;
import com.example.Trade.model.Tick;
import com.example.Trade.repository.BarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.Trade.service.impl.FileOperations.getListTickFromFile;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class BarHiberService {

    private static double open = 0.0;
    private static double close = 0.0;
    private static double high = 0.0;
    private static double low = 0.0;
    private static int volumeBuy = 0;
    private static int volumeSell = 0;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    private static final AtomicInteger count = new AtomicInteger(0);

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private final BarRepository barRepository;

    /**
     * Получение 1 секундных баров из тиков и сохранение их в бд
     */
    public void createBarOneSeconds() throws IOException {
        Files.walk(Path.of("TICKS"))
                .filter(f -> !Files.isDirectory(f))
                .forEach(path -> {
                    List<Tick> listTickFromFile = getListTickFromFile(path);
                    List<Bar> barOneSeconds = createBarOneSeconds(listTickFromFile);
                    barRepository.saveAll(barOneSeconds);
                    System.out.println();
                });
    }

    public List<Bar> createBarOneSeconds(List<Tick> ticks) {

        List<Bar> barList = new ArrayList<>();
        ticks
                .forEach(tick -> {
                    count.incrementAndGet();
                    String dateTime = new StringBuilder().append(tick.getDate()).append(" ").append(tick.getTime()).toString();
                    int vol = tick.getVolume();
                    double price = tick.getPrice();
                    String operation = tick.getOperation();

                    if (isNull(threadLocal.get())) {
                        threadLocal.set(dateTime);
                        open = price;
                        close = price;
                        high = price;
                        low = price;
                    }
                    if (dateTime.equals(threadLocal.get())) {

                        // объем
                        if (operation.equals("buy")) {
                            volumeBuy += vol;
                        } else {
                            volumeSell += vol;
                        }

                        // get high price
                        if (high < price) {
                            high = price;
                        }

                        // get low price
                        if (low > price) {
                            low = price;
                        }

                        close = price;
                    }
                    if (!dateTime.equals(threadLocal.get()) || ticks.size() == count.get()) {
                        Bar bar = Bar.builder()
                                .open(open)
                                .close(close)
                                .high(high)
                                .low(low)
                                .dateTime(LocalDateTime.parse(threadLocal.get(), formatter))
                                .volumeBuy(volumeBuy)
                                .volumeSell(volumeSell)
                                .symbol(Arrays.stream(Symbol.values()).filter(value -> value.getValue().equals(tick.getSymbol())).findAny().get())
                                .interval(1)
                                .build();

                        barList.add(bar);

                        threadLocal.set(dateTime);
                        open = price;
                        close = price;
                        high = price;
                        low = price;
                        if (operation.equals("buy")) {
                            volumeBuy = vol;
                            volumeSell = 0;
                        } else {
                            volumeSell = vol;
                            volumeBuy = 0;
                        }
                    }
                });

        return barList;
    }
}
