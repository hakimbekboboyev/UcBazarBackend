package ru.moscow.ucbazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.moscow.ucbazar.entity.UcEntity;

import java.util.List;

@Repository
public interface UcRepository extends JpaRepository<UcEntity, Long> {

    @Query(value = "select * from uc_entity where item_id = ?",nativeQuery = true)
    List<UcEntity> findAllById(int id);
}
