package africa.semicolon.demo.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
public class ApiResponse {
   private String message;
}
