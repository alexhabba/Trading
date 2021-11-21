package com.example.Trade.service.impl;

import com.example.Trade.dao.TickRepository;
import com.example.Trade.model.Tick;
import com.example.Trade.service.TickService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TickServiceImpl implements TickService {

    private static final Logger LOG = Logger.getLogger(TickServiceImpl.class);

    @Autowired
    TickRepository repository;

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

    @Scheduled(fixedDelay = 20000)
    public void deleteElementRedis() {
        LOG.info("зашли в метод deleteElementRedis");
        if (getSize() > 100_000) {
            Map<String, Tick> map = findAll();

            List<Tick> sortedList = map.values().stream()
                    .filter(v -> v.getMls().length() == 14)
                    .map(v -> v.setMls(v.getMls().substring(0, 13)))
                    .sorted(Comparator.comparing(Tick::getMls))
                    .collect(Collectors.toList());

            List<Tick> removeElement = sortedList.stream()
                    .limit(map.size() - 100000)
                    .collect(Collectors.toList());

            List<String> collect = removeElement.stream()
                    .map(t -> t.getMls() + t.getOperation())
                    .collect(Collectors.toList());

            collect.forEach(this::delete);
        }
    }
}
