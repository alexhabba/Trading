package com.example.Trade.service.impl;

import com.example.Trade.model.Bar;
import com.example.Trade.model.Fractal;
import com.example.Trade.service.FractalsPeriod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FractalsPeriodImpl implements FractalsPeriod {

    @Override
    public List<Fractal> getFractals(int period, List<Bar> barList) {

        List<Fractal> fractalList = new ArrayList<>();

        for (int i = 13; i < barList.size(); i++) {
            double leftMaxValue = barList.stream().skip(i - 13).limit(13)
                    .map(Bar::getHigh)
                    .mapToDouble(Double::doubleValue)
                    .max().orElse(0.0);
            double rightMaxValue = barList.stream().skip(i + 1).limit(13)
                    .map(Bar::getHigh)
                    .mapToDouble(Double::doubleValue)
                    .max().orElse(0.0);
            double leftMinValue = barList.stream().skip(i - 13).limit(13)
                    .map(Bar::getLow)
                    .mapToDouble(Double::doubleValue)
                    .min().orElse(0.0);
            double rightMinValue = barList.stream().skip(i + 1).limit(13)
                    .map(Bar::getLow)
                    .mapToDouble(Double::doubleValue)
                    .min().orElse(0.0);
            Bar bar = barList.get(i);
            if (((bar.getHigh() >= leftMaxValue && bar.getHigh() > rightMaxValue) ||
                    (bar.getLow() <= leftMinValue && bar.getLow() < rightMinValue))
                    && barList.size() > i + 13) {
                Fractal fractal = new Fractal();
                fractal
                        .setTime(bar.getTime())
                        .setDate(bar.getDate())
                        .setPeriod(period);
                if (bar.getHigh() >= leftMaxValue && bar.getHigh() >= rightMaxValue) {
                    fractal.setHighFractalPrice(bar.getHigh());
                } else {
                    fractal.setLowFractalPrice(bar.getLow());
                }
                fractalList.add(fractal);
            }
        }
        return fractalList;
    }
}
