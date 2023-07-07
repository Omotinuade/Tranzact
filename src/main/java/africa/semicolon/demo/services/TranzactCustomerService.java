package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.demo.dtos.response.ApiResponse;
import africa.semicolon.demo.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.demo.dtos.response.CustomerResponse;
import africa.semicolon.demo.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.demo.exceptions.ProfileUpdateFailedException;
import africa.semicolon.demo.exceptions.UserNotFoundException;
import africa.semicolon.demo.models.BankAccount;
import africa.semicolon.demo.models.BioData;
import africa.semicolon.demo.models.Customer;
import africa.semicolon.demo.repository.CustomerRepository;
import africa.semicolon.demo.services.cloud.CloudinaryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.List;

import static africa.semicolon.demo.utils.AppUtils.buildPageRequest;
import static africa.semicolon.demo.utils.ExceptionUtils.USER_REGISTRATION_FAILED;
import static africa.semicolon.demo.utils.ExceptionUtils.USER_WITH_ID_NOT_FOUND;
import static africa.semicolon.demo.utils.ResponseUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class TranzactCustomerService implements CustomerService{

    private final CustomerRepository customerRepository;
//    @Autowired
//    public TranzactCustomerService(CustomerRepository customerRepository){
//        this.customerRepository = customerRepository;
//    }

    private ModelMapper modelMapper;
    private CloudinaryService cloudService;
    @Override
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) throws CustomerRegistrationFailedException {
       BioData biodata=modelMapper.map(customerRegistrationRequest, BioData.class);
       Customer customer=new Customer();
       customer.setBioData(biodata);
       customer.setBankAccount(new BankAccount());
        Customer savedCustomer = customerRepository.save(customer);
        boolean isSaved = savedCustomer.getId() != null;

        if (!isSaved) throw  new CustomerRegistrationFailedException(String.format(USER_REGISTRATION_FAILED,customerRegistrationRequest.getEmail() ));

        return buildCustomerRegistrationResponse(customer.getId());
    }

    @Override
    public CustomerResponse getCustomerById(Long id) throws UserNotFoundException {
      Customer foundCustomer=  customerRepository.findById(id).orElseThrow(()->new UserNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id)));
      var customerResponse =buildCustomerResponse(foundCustomer);
//      log.info("registration successful{}", customerResponse);
        return customerResponse;
    }

    @Override
    public List<CustomerResponse> getAllCustomers(int page, int itemsPerPage) {
        Pageable pageable = buildPageRequest(page, itemsPerPage);
       var customerPage= customerRepository.findAll(pageable);
       var customers = customerPage.getContent();
        return customers.stream().map(this::buildCustomerResponse).toList();
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
         ApiResponse.builder()
                .message(USER_DELETED_SUCCESSFULLY)
                .build();

    }

    @Override
    public ApiResponse updateCustomerDetail(Long id, JsonPatch updateForm, MultipartFile image) throws UserNotFoundException, ProfileUpdateFailedException {
        ObjectMapper mapper = new ObjectMapper();
        var customer = customerRepository.findById(id).orElseThrow(()->new UserNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id)));
       JsonNode customerNode = mapper.convertValue(customer, JsonNode.class);

        try {
           JsonNode updatedNode = updateForm.apply(customerNode);
            Customer updatedCustomer = mapper.convertValue(updatedNode,Customer.class);
            String url=cloudService.upload(image.getBytes());
            updatedCustomer.setProfileImage(url);
            updatedCustomer.setLastModifiedAt(LocalDateTime.now());
            customerRepository.save(updatedCustomer);
        } catch (Exception e) {
            throw new ProfileUpdateFailedException(e.getMessage());
        }
        return ApiResponse.builder()
                .message(PROFILE_UPDATE_SUCCESS).build();
    }

    private CustomerResponse buildCustomerResponse(Customer foundCustomer) {
        return CustomerResponse.builder()
                .email(foundCustomer.getBioData().getEmail())
                .name(foundCustomer.getFirstname() + " " + foundCustomer.getLastname())
                .id(foundCustomer.getId())
                .build();
    }

    private static CustomerRegistrationResponse buildCustomerRegistrationResponse(Long customerId) {
        CustomerRegistrationResponse response = new CustomerRegistrationResponse();
        response.setId(customerId);
        response.setMessage(USER_REGISTRATION_SUCCESS);
        return response;
    }
}
