package ru.moscow.ucbazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moscow.ucbazar.entity.payment.UcPayment;

import java.util.Optional;

@Repository
public interface PaymentWithRegsRepository extends JpaRepository<UcPayment,Long> {
    Optional<UcPayment> findBySessionId(Long ucUcId);
}
