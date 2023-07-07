package africa.semicolon.demo.services.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Slf4j
public class CloudinaryServiceTest {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Test
    public void uploadTest() {
        try {
            MultipartFile imageFile = new MockMultipartFile("dog", new FileInputStream("C:\\Users\\Tinuade\\Desktop\\demo\\src\\test\\resources\\images\\dog.jpeg"));
            String cloudUrl=   cloudinaryService.upload(imageFile.getBytes());
            log.info(cloudUrl);
            assertThat(cloudUrl).isNotNull();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
