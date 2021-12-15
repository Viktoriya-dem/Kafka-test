package com.vika.kafka.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class TableDto {
    private Integer id;
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public TableDto(Integer id, String name, Date timestamp) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
    }

    public TableDto() {
    }

    @Override
    public String toString() {
        return "TableDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
