package com.example.Trade;

import com.example.Trade.model.Tick;
import com.example.Trade.service.impl.FileOperations;

import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException {

        String[] s = "1 4438.75  sell  2023.07.10 17:55:54 1689011754151".split("\\s+");
        Tick tick = new Tick(Integer.parseInt(s[0]), Double.parseDouble(s[1]), s[2], s[3], s[4], s[5]);

        FileOperations fileOperations = new FileOperations();
    }
}
