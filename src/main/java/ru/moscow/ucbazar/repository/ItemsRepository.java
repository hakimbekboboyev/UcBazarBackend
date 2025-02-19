package ru.moscow.ucbazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moscow.ucbazar.entity.Items;
@Repository
public interface ItemsRepository extends JpaRepository<Items,Integer> {

}
