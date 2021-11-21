package com.example.Trade.service;

import com.example.Trade.model.Bar;
import com.example.Trade.model.Fractal;

import java.util.List;

public interface FractalsPeriod {

    /**
     * method get fractal
     */
    List<Fractal> getFractals(int period, List<Bar> barList);

}
