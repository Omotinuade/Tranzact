package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.demo.dtos.response.ApiResponse;
import africa.semicolon.demo.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.demo.dtos.response.CustomerResponse;
import africa.semicolon.demo.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.demo.exceptions.ProfileUpdateFailedException;
import africa.semicolon.demo.exceptions.UserNotFoundException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CustomerService {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) throws CustomerRegistrationFailedException;
    CustomerResponse getCustomerById(Long Id) throws UserNotFoundException;

    List<CustomerResponse> getAllCustomers(int i, int i1);

    void deleteCustomerById(Long id);

    ApiResponse updateCustomerDetail(Long id, JsonPatch updateForm, MultipartFile image) throws UserNotFoundException, JsonPatchException, ProfileUpdateFailedException;
}

