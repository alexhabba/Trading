package com.example.Trade.service.impl;

import com.example.Trade.model.Bar;
import com.example.Trade.repository.BarRepository;
import com.example.Trade.service.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BarServiceImpl implements BarService {

    @Autowired
    private BarRepository repository;

    @Override
    public void save(String key, Bar bar) {
        repository.save(key, bar);
    }

    @Override
    public Bar findById(String id) {
        return null;
    }

    @Override
    public Map<String, Bar> findAll(String key) {
        return repository.findAll(key);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void saveAll(String key, Map<String, Bar> map) {
        repository.saveAll(key, map);
    }

    @Override
    public long getSize(String key) {
        return repository.getSize(key);
    }
}
