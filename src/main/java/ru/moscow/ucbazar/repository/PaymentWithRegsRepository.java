package ru.moscow.ucbazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moscow.ucbazar.entity.PaymentUcEntity;
import ru.moscow.ucbazar.entity.payment.UcPayment;

@Repository
public interface PaymentWithRegsRepository extends JpaRepository<UcPayment,Long> {

}
