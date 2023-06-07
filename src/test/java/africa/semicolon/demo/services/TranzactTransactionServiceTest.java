package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.demo.dtos.request.TransactionRegisterRequest;
import africa.semicolon.demo.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.demo.dtos.response.TransactionRegisterResponse;
import africa.semicolon.demo.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.demo.exceptions.TransactionDoesNotExistException;
import africa.semicolon.demo.exceptions.TransactionRegistrationFailedException;
import africa.semicolon.demo.exceptions.UserNotFoundException;
import africa.semicolon.demo.services.CustomerService;
import africa.semicolon.demo.services.TransactionService;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.demo.utils.AppUtils.*;
import static java.math.BigInteger.TWO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TranzactTransactionServiceTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerService customerService;

    private CustomerRegistrationRequest customerRegistrationRequest;
    private CustomerRegistrationResponse user1;
    private CustomerRegistrationResponse user2;
    private TransactionRegisterResponse response;
    @BeforeEach
    public void setUp() throws CustomerRegistrationFailedException, UserNotFoundException, TransactionRegistrationFailedException {
        customerRegistrationRequest=new CustomerRegistrationRequest();
        customerRegistrationRequest.setEmail("tinubu@gmail.com");
        customerRegistrationRequest.setPassword("");
        user1= customerService.registerCustomer(customerRegistrationRequest);

        customerRegistrationRequest.setEmail("tinuade@gmail.com");
        customerRegistrationRequest.setPassword("1234");
        user2= customerService.registerCustomer(customerRegistrationRequest);

        TransactionRegisterRequest transactionRequest = new TransactionRegisterRequest();
        transactionRequest.setBuyerId(user1.getId());
        transactionRequest.setSellerId(user2.getId());
        transactionRequest.setDescription("For powerbank purchase");
        transactionRequest.setAmount(BigDecimal.valueOf(10000));
        response = transactionService.registerTransaction(transactionRequest);
    }
    @Test
    public void registerTransactionTest() throws UserNotFoundException, TransactionRegistrationFailedException {
        TransactionRegisterRequest transactionRequest = new TransactionRegisterRequest();
            transactionRequest.setBuyerId(user1.getId());
            transactionRequest.setSellerId(user2.getId());
            transactionRequest.setDescription("For powerbank purchase");
            transactionRequest.setAmount(BigDecimal.valueOf(10000));
            TransactionRegisterResponse response = transactionService.registerTransaction(transactionRequest);
            assertThat(response).isNotNull();

    }
    @Test
    public void getTransactionByIdTest() throws TransactionDoesNotExistException, UserNotFoundException {
    var getResponse=transactionService.getTransactionById(response.getId());
    assertThat(getResponse).isNotNull();
    assertThat(getResponse.getId()).isNotNull();;
    }

    @Test
    public void getAllTransactionsTest(){
      var  responses = transactionService.getAllTransactions(ONE, FOUR);
      assertThat(responses.size()).isEqualTo(THREE);
    }
@Test
    public void updateTransactionTest() throws UserNotFoundException, TransactionDoesNotExistException, JsonPatchException {
       var transactionResponse= transactionService.getTransactionById(response.getId());
       assertThat(transactionResponse.getDescription().contains("book")).isFalse();
        transactionService.updateTransaction(response.getId(), buildPatch());
    var updatedResponse= transactionService.getTransactionById(response.getId());
    System.out.println(updatedResponse.getDescription());
    assertThat(updatedResponse.getDescription().contains("book")).isTrue();
}

public JsonPatch buildPatch() {
    List<JsonPatchOperation> patchList = null;
    try {
        patchList = List.of(
                new ReplaceOperation(
                        new JsonPointer("/description"),
                        new TextNode("Java Text book purchase")
                )
        );
    } catch (JsonPointerException e) {
        throw new RuntimeException(e);
    }
    return new JsonPatch(patchList);
}

@Test
    public void deleteTransactionById(){
        int number= transactionService.getAllTransactions(ONE,FOUR).size();
        assertThat(number).isGreaterThan(ZERO);
        transactionService.deleteTransactionById(response.getId());
    int newNumber= transactionService.getAllTransactions(ONE,FOUR).size();
        assertThat(number).isEqualTo(newNumber-ONE);
}
}
