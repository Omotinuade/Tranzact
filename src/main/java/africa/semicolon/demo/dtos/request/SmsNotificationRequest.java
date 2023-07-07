package africa.semicolon.demo.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsNotificationRequest {
    private String to;
    private String message;
}
