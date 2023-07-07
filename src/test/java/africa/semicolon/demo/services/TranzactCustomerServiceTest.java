package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.demo.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.demo.dtos.response.CustomerResponse;
import africa.semicolon.demo.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.demo.exceptions.ProfileUpdateFailedException;
import africa.semicolon.demo.exceptions.UserNotFoundException;
import africa.semicolon.demo.services.CustomerService;
import africa.semicolon.demo.utils.AppUtils;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static java.math.BigInteger.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static africa.semicolon.demo.utils.AppUtils.FOUR;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Slf4j
public class TranzactCustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    private CustomerRegistrationRequest customerRegistrationRequest;
    private CustomerRegistrationResponse customerRegistrationResponse;

    @BeforeEach
    void setUp() throws CustomerRegistrationFailedException {
        customerRegistrationRequest=new CustomerRegistrationRequest();
        customerRegistrationRequest.setEmail("tinubu@gmail.com");
        customerRegistrationRequest.setPassword("123");
        customerRegistrationResponse
                =customerService.registerCustomer(customerRegistrationRequest);
    }

    @Test
    public void testThatCustomerCanRegister() throws CustomerRegistrationFailedException {
        assertThat(customerRegistrationResponse).isNotNull();
    }
   @Test
   public void getCustomerByIdTest() throws UserNotFoundException {
       var foundCustomer = customerService.getCustomerById(customerRegistrationResponse.getId());
       assertThat(foundCustomer).isNotNull();
       assertThat(foundCustomer.getEmail()).isNotNull();
       assertThat(foundCustomer.getEmail()).isEqualTo(customerRegistrationRequest.getEmail());

   }
   @Test
    public void getAllCustomersTest() throws CustomerRegistrationFailedException {
       customerRegistrationRequest=new CustomerRegistrationRequest();
       customerRegistrationRequest.setEmail("tinuade@gmail.com");
       customerRegistrationRequest.setPassword("1234");
        customerService.registerCustomer(customerRegistrationRequest);
        List<CustomerResponse> customers = customerService.getAllCustomers(ONE.intValue(), TEN.intValue());
        assertThat(customers.size()).isEqualTo(FOUR);
   }
   @Test
    public void deleteCustomerByIdTest(){
        var customers = customerService.getAllCustomers(ONE.intValue(), TEN.intValue());
        int number = customers.size();
        assertThat(number).isGreaterThan(AppUtils.ZERO);
        customerService.deleteCustomerById(customerRegistrationResponse.getId());
       var newCustomers = customerService.getAllCustomers(ONE.intValue(), TEN.intValue());
       assertThat(newCustomers.size()).isEqualTo(number-ONE.intValue());
   }

   @Test
    public void updateCustomerDetailsTest() throws Exception {
        JsonPatch updateForm = buildJsonPatch();
       MultipartFile image = new MockMultipartFile("download",new FileInputStream("C:\\Users\\Tinuade\\Desktop\\demo\\src\\test\\resources\\images\\download.jpeg"));
       var foundCustomer= customerService.getCustomerById(customerRegistrationResponse.getId());
       log.info(foundCustomer.toString());
       assertThat(foundCustomer.getName().contains("Spicy") && foundCustomer.getName().contains("Sandy  ")).isFalse();
       customerService.updateCustomerDetail(foundCustomer.getId(),updateForm, image);
       var updatedCustomer= customerService.getCustomerById(customerRegistrationResponse.getId());
       log.info(updatedCustomer.toString());
       assertThat(updatedCustomer.getName().contains("Spicy") && updatedCustomer.getName().contains("Sandy")).isTrue();
   }

    private JsonPatch buildJsonPatch() {
        try {
            List<JsonPatchOperation> patch = List.of(
                    new ReplaceOperation(
                            new JsonPointer("/firstname"),
                            new TextNode("Spicy")
                    ),
                    new ReplaceOperation(
                            new JsonPointer("/lastname"),
                            new TextNode("Sandy")
                    ),
                    new ReplaceOperation(
                            new JsonPointer("/bankAccount/accountName"),
                            new TextNode("Folahan Joshua")
                    ),
                    new ReplaceOperation(
                            new JsonPointer("/bankAccount/accountNumber"),
                            new TextNode("0123456789")
                    ),
                    new ReplaceOperation(
                            new JsonPointer("/bankAccount/bankName"),
                            new TextNode("Prof-Pay")
                    )
            );
            return new JsonPatch(patch);
        } catch (JsonPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
