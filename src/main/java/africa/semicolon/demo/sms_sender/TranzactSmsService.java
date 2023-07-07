package africa.semicolon.demo.sms_sender;

import africa.semicolon.demo.dtos.request.SmsNotificationRequest;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.MessageStatus;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.messages.TextMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static africa.semicolon.demo.utils.AppUtils.YOUR_NEXMO_PHONE_NUMBER;

@Service
@AllArgsConstructor
public class TranzactSmsService implements SmsService {
    private final NexmoClient nexmoClient;

    @Override
    public void sendSms(SmsNotificationRequest request) throws IOException, NexmoClientException {
        TextMessage sms = new TextMessage(YOUR_NEXMO_PHONE_NUMBER, request.getTo(), request.getMessage());
        SmsSubmissionResponse response = nexmoClient.getSmsClient().submitMessage(sms);
        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("SMS sent successfully.");
        } else {
            System.out.println("SMS failed to send with error: " + response.getMessages().get(0).getErrorText() );
        }
    }
}
