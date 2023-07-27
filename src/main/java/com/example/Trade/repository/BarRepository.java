package com.example.Trade.repository;

import com.example.Trade.entity.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface BarRepository extends JpaRepository<Bar, UUID> {
}
