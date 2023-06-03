package africa.semicolon.demo.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public class AppUtils {
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    public static int FOUR = 4;
    public static int ZERO = 0;
    public static int ONE = 1;

    public static Pageable buildPageRequest(int page, int pageSize){
        if(page<= ZERO) page = DEFAULT_PAGE_NUMBER;
        if(pageSize<= ZERO) pageSize = DEFAULT_PAGE_SIZE;

        page-=ONE;
        return PageRequest.of(page, pageSize);
    }
}
