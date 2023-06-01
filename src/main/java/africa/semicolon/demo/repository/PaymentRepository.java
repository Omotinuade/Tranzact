package africa.semicolon.demo.repository;

import africa.semicolon.demo.models.Payment;
import africa.semicolon.demo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
