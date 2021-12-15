package com.vika.kafka.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="table1")
public class TableEn1 extends TableEn {

}
