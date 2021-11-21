package com.example.Trade.repository;

import com.example.Trade.model.Bar;

import java.util.List;
import java.util.Map;

public interface BarRepository {

    void save(String key, Bar bar);

    Bar findById(String id);

    Map<String, Bar> findAll(String key);

    void delete(String id);

    void saveAll(String key, Map<String, Bar> map);

    long getSize(String key);

    List<Bar> getListBars(String key);
}
