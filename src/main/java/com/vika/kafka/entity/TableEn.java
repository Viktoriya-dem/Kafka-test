package com.vika.kafka.entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;


@ToString
@EqualsAndHashCode
@MappedSuperclass
public abstract class TableEn {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name="timestamp", unique = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public TableEn() {
    }

    public TableEn(Integer id, String name, Date timestamp) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
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

