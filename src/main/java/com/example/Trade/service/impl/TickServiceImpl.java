package com.example.Trade.service.impl;

import com.example.Trade.model.Tick;
import com.example.Trade.repositoryRedis.TickRepository;
import com.example.Trade.service.TickService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TickServiceImpl implements TickService {

    private static final Logger LOG = Logger.getLogger(TickServiceImpl.class);

    @Autowired
    private TickRepository repository;

    @Override
    public void save(Tick tick) {
        repository.save(tick);
    }

    @Override
    public Tick findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Map<String, Tick> findAll() {
        LOG.info("зашли в метод findAll");
        Map<String, Tick> all = repository.findAll();
        LOG.info(String.format("получили Map<String, Tick> из %d елементов", all.size()));
        return all;
    }

    @Override
    public void saveAll(Map<String, Tick> map) {
        LOG.info("зашли в метод saveAll");
        repository.saveAll(map);
        LOG.info("сохранили Map<String, Tick> в redis");
    }

    @Override
    public long getSize() {
        return repository.getSize();
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public List<Tick> getAllTicks() {
        return repository.getAllTicks();
    }

}
