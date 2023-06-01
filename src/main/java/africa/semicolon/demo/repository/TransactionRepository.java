package africa.semicolon.demo.repository;

import africa.semicolon.demo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
