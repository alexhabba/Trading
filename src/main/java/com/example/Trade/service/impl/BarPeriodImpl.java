package com.example.Trade.service.impl;

import com.example.Trade.model.Bar;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BarPeriodImpl {

    private static double open = 0.0;
    private static double close = 0.0;
    private static double high = 0.0;
    private static double low = Double.MAX_VALUE;
    private static int volumeBuy = 0;
    private static int volumeSell = 0;
    private static boolean isOpen = false;
    private static String time1 = "";

    /**
     * method get bar m(1-240)
     */
    public List<Bar> getBar(List<Bar> barsM1, Integer period) {
        if (period % 2 != 0 && period % 3 != 0 && period % 5 != 0) {
            return List.of();
        }

        List<Bar> bars = new ArrayList<>();

        barsM1.stream()
                .filter(bar -> !bar.getTime().equals("") && bar.getMls() != null)
                .sorted(Comparator.comparing(Bar::getMls))
                .forEach(bar -> {
                    // тут получаем в currentTime время в минутах
                    // for example
                    // split = "02:05:00" 2 * 60 + 5 then we get 125 minutes
                    String[] split = bar.getTime().split(":");
                    int currentTime = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);

                    if (currentTime % period != 0) {

                        // получение цены открытия
                        if (isOpen) {
                            isOpen = false;
                            open = bar.getOpen();
                        }

                        // обьем
                        volumeBuy += bar.getVolumeBuy();
                        volumeSell += bar.getVolumeSell();

                        // get high price
                        if (high < bar.getHigh()) {
                            high = bar.getHigh();
                        }

                        // get low price
                        if (low > bar.getLow()) {
                            low = bar.getLow();
                        }

                        // get close price
                        close = bar.getClose();

                    } else {
                        Bar b = new Bar();
                        b
                                .setOpen(open)
                                .setClose(close)
                                .setHigh(high)
                                .setLow(low)
                                .setDate(bar.getDate())
                                .setTime(time1)
                                .setMls(bar.getMls())
                                .setVolume(volumeBuy + volumeSell)
                                .setVolumeBuy(volumeBuy)
                                .setVolumeSell(volumeSell)
                                .setSymbol("EPM")
                                .setPeriod(period);

                        bars.add(b);
                        open = 0.0;
                        close = 0.0;
                        high = 0.0;
                        low = Double.MAX_VALUE;
                        volumeBuy = 0;
                        volumeSell = 0;
                        isOpen = true;
                        time1 = bar.getTime();
                    }
                });
        return bars;
    }
}
