package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.demo.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.demo.dtos.response.GetCustomerResponse;
import africa.semicolon.demo.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.demo.models.BioData;
import africa.semicolon.demo.models.Customer;
import africa.semicolon.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static africa.semicolon.demo.utils.ExceptionUtils.USER_REGISTRATION_FAILED;
import static africa.semicolon.demo.utils.ResponseUtils.USER_REGISTRATION_SUCCESS;

@Service
@AllArgsConstructor
public class TranzactCustomerService implements CustomerService{

    private final CustomerRepository customerRepository;
//    @Autowired
//    public TranzactCustomerService(CustomerRepository customerRepository){
//        this.customerRepository = customerRepository;
//    }

    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) throws CustomerRegistrationFailedException {
       BioData biodata=modelMapper.map(customerRegistrationRequest, BioData.class);
       Customer customer=new Customer();
       customer.setBioData(biodata);
        Customer savedCustomer = customerRepository.save(customer);
        boolean isSaved = savedCustomer.getId() != null;

        if (!isSaved) throw  new CustomerRegistrationFailedException(String.format(USER_REGISTRATION_FAILED,customerRegistrationRequest.getEmail() ));

        return buildCustomerRegistrationResponse();
    }

    @Override
    public GetCustomerResponse getCustomerById(Long Id) {
        return null;
    }

    private static CustomerRegistrationResponse buildCustomerRegistrationResponse() {
        CustomerRegistrationResponse response = new CustomerRegistrationResponse();
        response.setMessage(USER_REGISTRATION_SUCCESS);
        return response;
    }
}
