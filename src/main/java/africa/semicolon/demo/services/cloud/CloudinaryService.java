package africa.semicolon.demo.services.cloud;

import africa.semicolon.demo.exceptions.ImageUploadFailedException;

public interface CloudinaryService {
    String  upload(byte[] data) throws ImageUploadFailedException;
}
