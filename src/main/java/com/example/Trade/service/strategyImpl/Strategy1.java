package com.example.Trade.service.strategyImpl;

import com.example.Trade.service.Strategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class Strategy1 implements Strategy {

    @Override
    public Map<Integer, String> start() {
        return null;
    }
}
