package com.example.Trade.service.impl;

import com.example.Trade.model.Bar;
import com.example.Trade.repositoryRedis.BarRepository;
import com.example.Trade.service.BarService;
import com.example.Trade.service.BarsM1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BarRedisService implements BarService {

    @Autowired
    private BarRepository repository;

    @Autowired
    private BarsM1 barsM1;

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

    public List<Bar> getListBars(String key) {
        return repository.getListBars(key).stream()
                .sorted(Comparator.comparing(Bar::getMls))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedDelay = 1300)
    private void getBarsM1() {
        Map<String, Bar> map = barsM1.getBars().stream()
                .filter(bar -> bar.getVolume() != 0)
                .collect(Collectors.toMap(bar -> bar.getDate() + " " + bar.getTime(), bar -> bar));
        saveAll("EPM", map);
    }
}
