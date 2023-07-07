package africa.semicolon.demo.sms_sender;

import africa.semicolon.demo.dtos.request.SmsNotificationRequest;
import com.nexmo.client.NexmoClientException;

import java.io.IOException;

public interface SmsService {
    void sendSms(SmsNotificationRequest request) throws IOException, NexmoClientException;
}
