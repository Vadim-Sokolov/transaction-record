package code.challenge.transactionrecord.service;

import code.challenge.transactionrecord.converter.TransactionConverter;
import code.challenge.transactionrecord.model.Transaction;
import code.challenge.transactionrecord.repository.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private final TransactionConverter transactionConverter = new TransactionConverter();
    private final TransactionService transactionService = new TransactionService(transactionRepository, transactionConverter);

    @Test
    void saveTransactionTest() {
        // GIVEN
        var transaction = createTransaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // WHEN
        var savedTransactionDTO = transactionService.saveTransaction(transactionConverter.convertToDTO(transaction));

        // THEN
        assertNotNull(savedTransactionDTO);
        assertEquals("TX126", savedTransactionDTO.getTransactionId());
    }

    @Test
    void getTransactionByIdTest() {
        // GIVEN
        var transaction = createTransaction();
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        // WHEN
        var foundTransactionDTO = transactionService.getTransactionById(1L);

        // THEN
        assertNotNull(foundTransactionDTO);
        assertEquals("TX126", foundTransactionDTO.getTransactionId());
    }

    @Test
    void getTransactionsTest() {
        // GIVEN
        var transactions = Arrays.asList(
                new Transaction(1L, "TX123", 100.00, "USD", LocalDateTime.now(), "Completed"),
                new Transaction(2L, "TX124", 150.00, "EUR", LocalDateTime.now(), "Pending")
        );
        when(transactionRepository.findAll()).thenReturn(transactions);

        // WHEN
        var foundTransactions = transactionService.getTransactions();

        // THEN
        assertEquals(2, foundTransactions.size());
        assertEquals("TX123", foundTransactions.get(0).getTransactionId());
    }

    @Test
    void updateTransactionTest() {
    }

    @Test
    void deleteTransactionTest() {
    }

    private Transaction createTransaction() {
        return new Transaction(1L, "TX126", 250.00, "USD", LocalDateTime.now(), "Completed");
    }
}