package africa.semicolon.demo.services.sms_service;

import africa.semicolon.demo.dtos.request.SmsNotificationRequest;
import africa.semicolon.demo.sms_sender.SmsService;
import com.nexmo.client.NexmoClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TranzactSmsServiceTest{
@Autowired
    private SmsService smsService;

@Test
    public void sendSmsTest() throws IOException, NexmoClientException {
    SmsNotificationRequest request = new SmsNotificationRequest("2348138732503", "Hello world");
    smsService.sendSms(request);

}

}
