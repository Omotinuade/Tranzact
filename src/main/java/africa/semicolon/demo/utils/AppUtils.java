package africa.semicolon.demo.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public class AppUtils {
    public static final String CLOUDINARY_SECURE_KEY = "secure_url";
    public static final String CLOUDINARY_API_KEY = "${cloudinary.api.key}";
    public static final String CLOUDINARY_API_SECRET = "${cloudinary.api.secret}";
    public static final String CLOUDINARY_CLOUD_NAME = "${cloudinary.cloud.name}";
    public static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    public static int FOUR = 4;
    public static int ZERO = 0;
    public static int ONE = 1;
    public static int THREE = 3;


    public static Pageable buildPageRequest(int page, int pageSize){
        if(page<= ZERO) page = DEFAULT_PAGE_NUMBER;
        if(pageSize<= ZERO) pageSize = DEFAULT_PAGE_SIZE;

        page-=ONE;
        return PageRequest.of(page, pageSize);
    }
}
