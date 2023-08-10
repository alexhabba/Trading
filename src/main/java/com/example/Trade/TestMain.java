package com.example.Trade;

import com.example.Trade.service.BarHiberService;
import com.example.Trade.service.impl.FileOperations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static com.example.Trade.service.impl.FileOperations.readFileAndCreateFilesByPattern;

public class TestMain {

    // todo придумать 100 стратегий
    // todo сохранить 1 секундные бары

    public static void main(String[] args) throws IOException {
//        createNewFiles();
//        BarHiberService.createBarOneSeconds();
    }

    /**
     * Вызвать когда необходимо создать новые файлы
     */
    private static void createNewFiles() {
        for (int i = 0; i < 50; i++) {
            String pattern = LocalDate.now().minusDays(i).toString().replace("-", ".");
            readFileAndCreateFilesByPattern(pattern, Path.of("/Users/alex/Downloads/nasdaq.txt"));
            readFileAndCreateFilesByPattern(pattern, Path.of("/Users/alex/Downloads/s&p.txt"));
            readFileAndCreateFilesByPattern(pattern, Path.of("/Users/alex/Downloads/cl.txt"));
        }
    }
}
