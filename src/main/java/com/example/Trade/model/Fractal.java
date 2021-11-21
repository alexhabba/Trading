package com.example.Trade.model;

import java.util.Objects;

public class Fractal {

    private String date;

    private String time;

    private int period;

    private double highFractalPrice;

    private double lowFractalPrice;

    public String getDate() {
        return date;
    }

    public Fractal setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public Fractal setTime(String time) {
        this.time = time;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public Fractal setPeriod(int period) {
        this.period = period;
        return this;
    }

    public double getHighFractalPrice() {
        return highFractalPrice;
    }

    public Fractal setHighFractalPrice(double highFractalPrice) {
        this.highFractalPrice = highFractalPrice;
        return this;
    }

    public double getLowFractalPrice() {
        return lowFractalPrice;
    }

    public Fractal setLowFractalPrice(double lowFractalPrice) {
        this.lowFractalPrice = lowFractalPrice;
        return this;
    }

    @Override
    public String toString() {
        return "Fractal{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", period=" + period +
                ", highFractalPrice=" + highFractalPrice +
                ", lowFractalPrice=" + lowFractalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fractal)) return false;
        Fractal fractal = (Fractal) o;
        return getPeriod() == fractal.getPeriod() && Double.compare(fractal.getHighFractalPrice(), getHighFractalPrice()) == 0 && Double.compare(fractal.getLowFractalPrice(), getLowFractalPrice()) == 0 && Objects.equals(getDate(), fractal.getDate()) && Objects.equals(getTime().substring(0, 5), fractal.getTime().substring(0, 5));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getTime(), getPeriod(), getHighFractalPrice(), getLowFractalPrice());
    }
}
