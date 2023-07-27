package com.example.Trade.service.impl;

import com.example.Trade.model.Tick;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FileOperations {

    public static void readFileAndCreateFilesByPattern(String pattern, Path pathFrom) {
        byte[] bytes;
        List<String> list;
        try {
            bytes = Files.readAllBytes(pathFrom);
            list = Arrays.asList(new String(bytes, StandardCharsets.UTF_16).split("\\n"));
            String collect = list.stream()
                    .filter(str -> str.contains(pattern))
                    .collect(Collectors.joining("\n"));
            if (collect.length() < 10) {
                return;
            }
            System.out.println(pattern);
            String nameDirectory = getNameDirectory(pathFrom);
                Path dir = Path.of("TICKS/" + nameDirectory.toUpperCase());
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            Path to = Path.of(dir + "/" + nameDirectory + "_" + pattern + ".txt");

            Files.createFile(to);
            Files.writeString(to, collect);
        } catch (IOException e) {
            System.out.println("failed read file");
        }
    }

    public static String getNameDirectory(Path path) {
        String[] split = path.toString().split("/");
        return split[split.length - 1].split("\\.")[0];
    }

    public static List<Tick> getListTickFromFile(Path path) {
        byte[] bytes;
        List<Tick> list = new ArrayList<>();
        try {
            bytes = Files.readAllBytes(path);
            Arrays.asList(new String(bytes).split("\\n"))
                    .forEach(str -> {
                        String[] s = str.split("\\s+");
                        Tick tick = new Tick(Integer.parseInt(s[0]), Double.parseDouble(s[1]), s[2], s[3], s[4], s[5]);
                        list.add(tick);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
