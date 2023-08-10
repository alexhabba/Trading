package com.example.Trade.entity;

import com.example.Trade.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bar {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    /**
     * Интервал времени в секундах.
     */
    private int interval;

    /**
     * Цена Открытия бара.
     */
    private double open;

    /**
     * Цена закрытия бара.
     */
    private double close;

    /**
     * Максимальная цена бара.
     */
    private double high;

    /**
     * Минимальная цена бара.
     */
    private double low;

    /**
     * Объем на покупку.
     */
    private int volumeBuy;

    /**
     * Объем на продажу.
     */
    private int volumeSell;

    /**
     * Временное начало бара.
     */
    private LocalDateTime dateTime;

    /**
     * Инструмент.
     */
    @Enumerated(EnumType.STRING)
    private Symbol symbol;

}
