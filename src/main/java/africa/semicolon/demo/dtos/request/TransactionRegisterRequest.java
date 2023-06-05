package africa.semicolon.demo.dtos.request;

import africa.semicolon.demo.models.Payment;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRegisterRequest {

    private Long buyerId;
    private Long sellerId;
    private BigDecimal amount;
    private String description;
}
