package org.example.demo2.dao.repository;

import org.example.demo2.dao.entity.ClasseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity,Long> {


}
