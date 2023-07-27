package com.example.Trade.repositoryRedis.impl;

import com.example.Trade.model.Bar;
import com.example.Trade.repositoryRedis.BarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class BarRepositoryImpl implements BarRepository {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Bar> hashOperations;

    @PostConstruct
    private void initializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(String key, Bar bar) {
        hashOperations.put(key, bar.getDate().concat(" ").concat(bar.getTime()), bar);
    }

    @Override
    public Bar findById(String id) {
        return null;
    }

    @Override
    public Map<String, Bar> findAll(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void saveAll(String key, Map<String, Bar> map) {
        hashOperations.putAll(key, map);
    }

    @Override
    public long getSize(String key) {
        return hashOperations.size(key);
    }

    public List<Bar> getListBars(String key) {
        return hashOperations.values(key);
    }
}
