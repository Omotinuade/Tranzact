package africa.semicolon.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static africa.semicolon.demo.utils.AppUtils.*;

@Configuration
public class BeanConfig {


    @Value(CLOUDINARY_API_KEY)
    private String api_key;
    @Value(CLOUDINARY_API_SECRET)
    private String api_secret;
    @Value(CLOUDINARY_CLOUD_NAME)
    private String cloud_name;
    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap("cloud_name",cloud_name,
                "api_key",api_key,
                "api_secret",api_secret));
    }

}
