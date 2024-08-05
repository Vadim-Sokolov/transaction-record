package code.challenge.transactionrecord.repository;

import code.challenge.transactionrecord.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
