package code.challenge.transactionrecord.converter;

import code.challenge.transactionrecord.model.Transaction;
import code.challenge.transactionrecord.model.dto.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    public TransactionDTO convertToDTO(Transaction transaction) {
        if (transaction != null) {
            TransactionDTO dto = new TransactionDTO();
            dto.setTransactionId(transaction.getTransactionId());
            dto.setAmount(transaction.getAmount());
            dto.setCurrency(transaction.getCurrency());
            dto.setTimestamp(transaction.getTimestamp());
            dto.setStatus(transaction.getStatus());
            return dto;
        } else {
            return null;
        }
    }

    public Transaction convertToEntity(TransactionDTO dto) {
        if (dto != null) {
            Transaction transaction = new Transaction();
            transaction.setTransactionId(dto.getTransactionId());
            transaction.setAmount(dto.getAmount());
            transaction.setCurrency(dto.getCurrency());
            transaction.setTimestamp(dto.getTimestamp());
            transaction.setStatus(dto.getStatus());
            return transaction;
        } else {
            return null;
        }
    }
}
