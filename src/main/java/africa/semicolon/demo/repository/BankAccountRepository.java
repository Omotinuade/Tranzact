package africa.semicolon.demo.repository;

import africa.semicolon.demo.models.BankAccount;
import africa.semicolon.demo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
}
