package com.vika.kafka.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@EqualsAndHashCode
@Entity
@Table(name = "table2")
public class TableEn2 extends TableEn {
    public TableEn2() {
    }

    public TableEn2(Integer id, String name, Date timestamp) {
        super(id, name, timestamp);
    }
}
