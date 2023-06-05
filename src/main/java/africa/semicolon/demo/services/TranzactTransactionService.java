package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.TransactionRegisterRequest;
import africa.semicolon.demo.dtos.response.ApiResponse;
import africa.semicolon.demo.dtos.response.CustomerResponse;
import africa.semicolon.demo.dtos.response.TransactionRegisterResponse;
import africa.semicolon.demo.dtos.response.TransactionResponse;
import africa.semicolon.demo.exceptions.TransactionDoesNotExistException;
import africa.semicolon.demo.exceptions.TransactionRegistrationFailedException;
import africa.semicolon.demo.exceptions.UserNotFoundException;
import africa.semicolon.demo.models.Payment;
import africa.semicolon.demo.models.Status;
import africa.semicolon.demo.models.Transaction;
import africa.semicolon.demo.repository.TransactionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import static africa.semicolon.demo.utils.AppUtils.ZERO;
import static africa.semicolon.demo.utils.AppUtils.buildPageRequest;
import static africa.semicolon.demo.utils.ExceptionUtils.TRANSACTION_NOT_FOUND;
import static africa.semicolon.demo.utils.ExceptionUtils.TRANSACTION_REGISTRATION_FAILED;
import static africa.semicolon.demo.utils.ResponseUtils.*;
import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

@Service
@AllArgsConstructor
public class TranzactTransactionService implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;

    @Override
    public TransactionRegisterResponse registerTransaction(TransactionRegisterRequest transactionRequest) throws TransactionRegistrationFailedException, UserNotFoundException {
        customerService.getCustomerById(transactionRequest.getSellerId());
        customerService.getCustomerById(transactionRequest.getBuyerId());
        Transaction transaction = createTransaction(transactionRequest);
        Transaction savedTransaction =transactionRepository.save(transaction);
        if(savedTransaction.getId()==null) throw new TransactionRegistrationFailedException(String.format(TRANSACTION_REGISTRATION_FAILED,transactionRequest.getAmount()));
        return TransactionRegisterResponse.builder().id(transaction.getId()).message(TRANSACTION_REGISTRATION_SUCCESS).build();
    }

    @Override
    public TransactionResponse getTransactionById(Long transactionId) throws TransactionDoesNotExistException, UserNotFoundException {
        var transaction =transactionRepository.findById(transactionId).orElseThrow(()->new TransactionDoesNotExistException(String.format(TRANSACTION_NOT_FOUND, transactionId)));
        return buildTransactionResponse(transaction);
    }

    private TransactionResponse buildTransactionResponse(Transaction transaction) throws UserNotFoundException {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getPayment().getAmount())
                .description(transaction.getDescription())
                .buyerName((customerService.getCustomerById(transaction.getBuyerId()).getEmail()))
                .sellerName((customerService.getCustomerById(transaction.getSellerId()).getEmail())).build();
    }

    @Override
    public List<TransactionResponse> getAllTransactions(int page, int items) {
       Pageable pages= buildPageRequest(page, items);
       var transactions=transactionRepository.findAll(pages);
       List<TransactionResponse> transactionResponses =transactions.stream().map(transaction-> {
           try {
               return buildTransactionResponse(transaction);
           } catch (UserNotFoundException e) {
               throw new RuntimeException(e);
           }
       }).toList();

        return transactionResponses;
    }

    @Override
    public void updateTransaction(Long id, JsonPatch patch) throws TransactionDoesNotExistException, JsonPatchException {
        ObjectMapper mapper = new ObjectMapper();
        var transaction= transactionRepository.findById(id).orElseThrow(()->new TransactionDoesNotExistException(String.format(TRANSACTION_NOT_FOUND,id)));
       JsonNode transactionNode= mapper.convertValue(transaction, JsonNode.class);
       var updatedTransactionNode=patch.apply(transactionNode);
       Transaction updatedTransaction= mapper.convertValue(updatedTransactionNode, Transaction.class);
       updatedTransaction.setLastModifiedAt(LocalDateTime.now());
       transactionRepository.save(updatedTransaction);

    }

    @Override
    public ApiResponse deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
       return ApiResponse.builder()
                .message(TRANSACTION_DELETED_SUCCESSFULLY)
                .build();
    }

    private static Transaction createTransaction(TransactionRegisterRequest transactionRequest) {
        Payment payment = new Payment();
        payment.setAmount(transactionRequest.getAmount());
        payment.setStatus(Status.PENDING);

        Transaction transaction = Transaction.builder()
                .buyerId(transactionRequest.getBuyerId())
                .sellerId(transactionRequest.getSellerId())
                .description(transactionRequest.getDescription())
                .payment(payment)
                .build();
        return transaction;
    }
}
