package com.example.Trade.service.impl;

import com.example.Trade.model.Bar;
import com.example.Trade.model.Tick;
import com.example.Trade.service.BarsM1;
import com.example.Trade.service.TickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BarsM1Impl implements BarsM1 {

    @Autowired
    private TickService tickService;

    private static double open = 0.0;
    private static double close = 0.0;
    private static double high = 0.0;
    private static double low = Double.MAX_VALUE;
    private static int volumeBuy = 0;
    private static int volumeSell = 0;
    private static int tempMinute = 0;
    private static boolean isOpen = false;
    private static String time1 = "";
    private static String milliseconds = "";

    @Override
    public List<Bar> getBars() {
        List<Bar> barList = new ArrayList<>();

        List<Tick> allTicks = tickService.getAllTicks();

        allTicks.stream()
                .filter(tick -> tick.getMls().length() == 14)
                .map(tick -> tick.setMls(tick.getMls().substring(0, 13)))
                .sorted(Comparator.comparing(Tick::getMls))
                .forEach(tick -> {
                    int vol = tick.getVolume();
                    double price = tick.getPrice();
                    String operation = tick.getOperation();
                    String date = tick.getDate();
                    String[] time = tick.getTime().split(":");
                    String mls = tick.getMls();

                    int currentMinute = Integer.parseInt(time[1]);
                    if (tempMinute == currentMinute) {
                        // обьем
                        if (operation.equals("buy")) {
                            volumeBuy += vol;
                        } else {
                            volumeSell += vol;
                        }

                        // получение цены открытия
                        if (isOpen) {
                            isOpen = false;
                            milliseconds = mls;
                            open = price;
                            time1 = tick.getTime();
                        }

                        // get high price
                        if (high < price) {
                            high = price;
                        }

                        // get low price
                        if (low > price) {
                            low = price;
                        }

                        // get close price
                        close = price;
                    } else {
                        Bar bar = new Bar();
                        bar
                                .setOpen(open)
                                .setClose(close)
                                .setHigh(high)
                                .setLow(low)
                                .setDate(date)
                                .setTime(time1)
                                .setMls(milliseconds)
                                .setVolume(volumeBuy + volumeSell)
                                .setVolumeBuy(volumeBuy)
                                .setVolumeSell(volumeSell)
                                .setSymbol(tick.getSymbol())
                                .setPeriod(1);

                        barList.add(bar);
                        open = 0.0;
                        close = 0.0;
                        high = 0.0;
                        low = Double.MAX_VALUE;
                        volumeBuy = 0;
                        volumeSell = 0;
                        tempMinute = currentMinute;
                        milliseconds = null;
                        isOpen = true;
                    }
                });

        return barList;
    }
}
