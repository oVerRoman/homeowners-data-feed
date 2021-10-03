package com.simbirsoftintensiv.intensiv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

@Entity
@Table(name = "counter_values")
public class CounterValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "counter_id")
    private int counterId;
    @Column(name = "date")
    private String date;
    @DecimalMin(value = "0.1", message = "Значение должно быть больше 0")
    @Column(name = "value")
    private double value;

    public CounterValue() {
    }

    public CounterValue(int counterId, String date, double value) {
        super();
        this.counterId = counterId;
        this.date = date;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + counterId;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CounterValue other = (CounterValue) obj;
        if (counterId != other.counterId)
            return false;
        if (id != other.id)
            return false;
        return true;
    }
}