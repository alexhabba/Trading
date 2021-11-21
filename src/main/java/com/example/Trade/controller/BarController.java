package com.example.Trade.controller;

import com.example.Trade.model.Bar;
import com.example.Trade.service.BarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BarController {

    @Autowired
    BarService barService;

    @GetMapping("/getAllBars")
    public List<Bar> findAllBars() {
        List<Bar> epm = barService.getListBars("EPM");
        return epm.stream()
                .skip(epm.size() - 100)
                .collect(Collectors.toList());
    }
}
