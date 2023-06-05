package africa.semicolon.demo.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class TransactionResponse {
   private Long id;
   private String buyerName;
   private String sellerName;
   private String description;
   private BigDecimal amount;
}
