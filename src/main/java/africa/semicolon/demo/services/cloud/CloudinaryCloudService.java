package africa.semicolon.demo.services.cloud;

import africa.semicolon.demo.exceptions.ImageUploadFailedException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static africa.semicolon.demo.utils.AppUtils.CLOUDINARY_SECURE_KEY;

@Service
@AllArgsConstructor
public class CloudinaryCloudService implements CloudinaryService{

    private final Cloudinary cloudinary ;
    @Override
    public String upload(byte[] data) throws ImageUploadFailedException {
        try {
           Map<?,?> cloudResponse= cloudinary.uploader().upload(data, ObjectUtils.emptyMap());
         String url= (String) cloudResponse.get(CLOUDINARY_SECURE_KEY);
         return url;
        } catch (IOException e) {
            throw new ImageUploadFailedException(e.getMessage());
        }
    }
}
