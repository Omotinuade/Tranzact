package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.demo.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.demo.dtos.response.GetCustomerResponse;
import africa.semicolon.demo.exceptions.CustomerRegistrationFailedException;
import org.springframework.stereotype.Service;


public interface CustomerService {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) throws CustomerRegistrationFailedException;
    GetCustomerResponse getCustomerById(Long Id);
}

