package code.challenge.transactionrecord;

import code.challenge.transactionrecord.model.Transaction;
import code.challenge.transactionrecord.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionrecordApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setup() {
        transactionRepository.deleteAll();
        Transaction transaction1 = new Transaction(null, "TX123", 100.00, "USD", LocalDateTime.now(), "Completed");
        Transaction transaction2 = new Transaction(null, "TX124", 150.00, "EUR", LocalDateTime.now(), "Pending");
        Transaction transaction3 = new Transaction(null, "TX125", 200.00, "GBP", LocalDateTime.now(), "Failed");
        transactionRepository.saveAll(List.of(transaction1, transaction2, transaction3));
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        mockMvc.perform(get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].transactionId").value("TX123"))
                .andExpect(jsonPath("$[1].transactionId").value("TX124"))
                .andExpect(jsonPath("$[2].transactionId").value("TX125"));
    }

    @Test
    public void testCreateTransaction() throws Exception {
        String newTransaction = """
                {
                    "transactionId": "TX126",
                    "amount": 250.00,
                    "currency": "USD",
                    "timestamp": "2023-07-30T14:34:56",
                    "status": "Completed"
                }
                """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTransaction))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("TX126"))
                .andExpect(jsonPath("$.amount").value(250.00))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.status").value("Completed"));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction transaction = transactionRepository.save(new Transaction(null, "TX127", 300.00, "JPY", LocalDateTime.now(), "Completed"));

        mockMvc.perform(get("/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TX127"))
                .andExpect(jsonPath("$.amount").value(300.00))
                .andExpect(jsonPath("$.currency").value("JPY"))
                .andExpect(jsonPath("$.status").value("Completed"));
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        Transaction transaction = transactionRepository.save(new Transaction(null, "TX128", 400.00, "AUD", LocalDateTime.now(), "Pending"));

        String updatedTransaction = """
                {
                    "transactionId": "TX128",
                    "amount": 450.00,
                    "currency": "AUD",
                    "timestamp": "2023-07-30T14:34:56",
                    "status": "Completed"
                }
                """;

        mockMvc.perform(put("/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTransaction))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TX128"))
                .andExpect(jsonPath("$.amount").value(450.00))
                .andExpect(jsonPath("$.currency").value("AUD"))
                .andExpect(jsonPath("$.status").value("Completed"));
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        Transaction transaction = transactionRepository.save(new Transaction(null, "TX129", 500.00, "CAD", LocalDateTime.now(), "Pending"));

        mockMvc.perform(delete("/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
