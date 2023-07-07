package africa.semicolon.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nexmo.client.NexmoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static africa.semicolon.demo.utils.AppUtils.CLOUDINARY_API_KEY;
import static africa.semicolon.demo.utils.AppUtils.CLOUDINARY_API_SECRET;

@Configuration
public class NexmoConfig {
    @Value("${nexmo.creds.api-key}")
    private String VONAGE_API_KEY;
    @Value("${nexmo.creds.secret}")
    private String VONAGE_API_SECRET;
    @Bean
    public NexmoClient nexmoClient(){
        return NexmoClient.builder().apiKey(VONAGE_API_KEY).apiSecret(VONAGE_API_SECRET).build();
    }
}
