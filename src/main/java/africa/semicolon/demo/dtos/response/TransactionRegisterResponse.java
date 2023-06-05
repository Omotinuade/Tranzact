package africa.semicolon.demo.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@Builder
public class TransactionRegisterResponse {
    private Long id;
    private String message;

}
