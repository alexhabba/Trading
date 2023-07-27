package com.example.Trade;

import com.example.Trade.model.Tick;
import com.example.Trade.service.impl.FileOperations;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class TestMain {

    public static void main(String[] args) throws IOException {

        String string = "1 4438.75  sell  2023.07.10 17:55:54 1689011754151";
        String[] s = string.split("\\s+");
        System.out.println(string.contains("17:55:"));
        Tick tick = new Tick(Integer.parseInt(s[0]), Double.parseDouble(s[1]), s[2], s[3], s[4], s[5]);

        FileOperations fileOperations = new FileOperations();

//        for (int i = 0; i < 50; i++) {
//            String pattern = LocalDate.now().minusDays(i).toString().replace("-", ".");
//            fileOperations.readFileAndCreateFilesByPattern(pattern, Path.of("/Users/alex/Downloads/nasdaq.txt"));
//            fileOperations.readFileAndCreateFilesByPattern(pattern, Path.of("/Users/alex/Downloads/s&p.txt"));
//            fileOperations.readFileAndCreateFilesByPattern(pattern, Path.of("/Users/alex/Downloads/cl.txt"));
//        }


    }
}
