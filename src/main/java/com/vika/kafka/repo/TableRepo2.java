package com.vika.kafka.repo;

import com.vika.kafka.entity.TableEn2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepo2 extends JpaRepository<TableEn2, Integer> {

}
