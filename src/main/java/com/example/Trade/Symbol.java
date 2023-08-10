package com.example.Trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Symbol {
    NASDAQ("nasdaq"),
    SANDP("s&p"),
    CL("cl");

    private final String value;
}
