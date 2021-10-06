package com.simbirsoftintensiv.intensiv.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "mater_values")
public class CounterValue extends AbstractBaseEntity {

    @JoinColumn(name = "mater_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Counter counter;

    @Column(name = "date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    @Column(name = "value")
    @Min(value = 1, message = "Значение должно быть больше 0")
    private Integer value;

    public CounterValue() {
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


}