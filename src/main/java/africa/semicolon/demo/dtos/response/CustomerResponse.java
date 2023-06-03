package africa.semicolon.demo.dtos.response;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class CustomerResponse {
   private Long id;
   private String name;
   private String email;
}

