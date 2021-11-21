package com.example.Trade.service;

import com.example.Trade.model.Tick;

import java.util.Map;

public interface TickService {

    void save(Tick tick);

    Tick findById(String id);

    Map<String, Tick> findAll();

    void saveAll(Map<String, Tick> map);

    long getSize();

    void delete(String id);

}
