package code.challenge.transactionrecord.service;

import code.challenge.transactionrecord.converter.TransactionConverter;
import code.challenge.transactionrecord.model.dto.TransactionDTO;
import code.challenge.transactionrecord.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;

    public TransactionService(TransactionRepository transactionRepository, TransactionConverter transactionConverter) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
    }

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        log.debug("Saving transaction: {}", transactionDTO);
        var savedTransaction = transactionRepository.save(transactionConverter.convertToEntity(transactionDTO));
        log.info("Transaction saved with ID: {}", savedTransaction.getId());
        return transactionConverter.convertToDTO(savedTransaction);
    }

    public List<TransactionDTO> getTransactions() {
        log.debug("Retrieving all transactions");
        var transactions = transactionRepository.findAll();
        log.info("Total transactions retrieved: {}", transactions.size());
        return transactions.stream()
                .map(transactionConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id) {
        log.debug("Retrieving transaction with ID: {}", id);
        var transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            log.info("Transaction retrieved: {}", transaction);
            return transactionConverter.convertToDTO(transaction);
        } else {
            log.warn("Transaction with ID {} not found", id);
            return null;
        }
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        log.debug("Updating transaction with ID: {}", id);
        var transactionToUpdate = transactionRepository.findById(id).orElse(null);
        if (transactionToUpdate == null) {
            log.warn("Transaction with ID {} not found for update", id);
            return null;
        } else {
            transactionToUpdate.setTransactionId(transactionDTO.getTransactionId());
            transactionToUpdate.setAmount(transactionDTO.getAmount());
            transactionToUpdate.setCurrency(transactionDTO.getCurrency());
            transactionToUpdate.setTimestamp(transactionDTO.getTimestamp());
            transactionToUpdate.setStatus(transactionDTO.getStatus());
            var updatedTransaction = transactionRepository.save(transactionToUpdate);
            log.info("Transaction updated: {}", updatedTransaction);
            return transactionConverter.convertToDTO(updatedTransaction);
        }
    }

    public boolean deleteTransaction(Long id) {
        log.debug("Deleting transaction with ID: {}", id);
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            log.info("Transaction with ID {} deleted", id);
            return true;
        } else {
            log.warn("Transaction with ID {} not found for deletion", id);
            return false;
        }
    }
}
