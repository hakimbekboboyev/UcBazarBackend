package ru.moscow.ucbazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moscow.ucbazar.entity.PaymentUcEntity;

@Repository
public interface PaymentUcRepository extends JpaRepository<PaymentUcEntity,Long> {

}
