package code.challenge.transactionrecord.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {

    @NotBlank(message = "Transaction ID is mandatory")
    private String transactionId;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Currency is mandatory")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters")
    private String currency;

    @NotNull(message = "Timestamp is mandatory")
    private LocalDateTime timestamp;

    @NotBlank(message = "Status is mandatory")
    private String status;
}
