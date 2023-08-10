package com.example.Trade.service.impl;

import com.example.Trade.model.Tick;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                    .forEach(str -> addTickToList(path, list, str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void addTickToList(Path path, List<Tick> list, String str) {
        String[] s = str.split("\\s+");
        Tick tick = getTick(s, path);
        list.add(tick);
    }

    private static Tick getTick(String[] s, Path path) {
        return Tick.builder()
                .volume(Integer.parseInt(s[0]))
                .price(Double.parseDouble(s[1]))
                .operation(s[2])
                .date(s[3])
                .time(s[4])
                .mls(s[5])
                .symbol(path.getFileName().toString().split("_")[0])
                .build();
    }
}
