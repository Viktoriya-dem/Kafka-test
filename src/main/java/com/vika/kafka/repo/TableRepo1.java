package com.vika.kafka.repo;

import com.vika.kafka.entity.TableEn1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepo1 extends JpaRepository<TableEn1, Integer> {

}
