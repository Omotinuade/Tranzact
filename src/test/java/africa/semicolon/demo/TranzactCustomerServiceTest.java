package africa.semicolon.demo;

import africa.semicolon.demo.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.demo.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.demo.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.demo.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class TranzactCustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    private CustomerRegistrationRequest customerRegistrationRequest;

    @BeforeEach
    void setUp() {
        customerRegistrationRequest=new CustomerRegistrationRequest();
        customerRegistrationRequest.setEmail("tinubu@gmail.com");
        customerRegistrationRequest.setPassword("");
    }

    @Test
    public void testThatCustomerCanRegister() throws CustomerRegistrationFailedException {

        CustomerRegistrationResponse customerRegistrationResponse
                =customerService.registerCustomer(customerRegistrationRequest);

        assertThat(customerRegistrationResponse).isNotNull();
    }
}
