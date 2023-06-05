package africa.semicolon.demo.services;

import africa.semicolon.demo.dtos.request.TransactionRegisterRequest;
import africa.semicolon.demo.dtos.response.ApiResponse;
import africa.semicolon.demo.dtos.response.TransactionRegisterResponse;
import africa.semicolon.demo.dtos.response.TransactionResponse;
import africa.semicolon.demo.exceptions.TransactionDoesNotExistException;
import africa.semicolon.demo.exceptions.TransactionRegistrationFailedException;
import africa.semicolon.demo.exceptions.UserNotFoundException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;

public interface TransactionService {
    TransactionRegisterResponse registerTransaction(TransactionRegisterRequest transactionRequest) throws TransactionRegistrationFailedException, UserNotFoundException;

    TransactionResponse getTransactionById(Long transactionId) throws TransactionDoesNotExistException, UserNotFoundException;

    List<TransactionResponse> getAllTransactions(int page, int items);

    void updateTransaction(Long id, JsonPatch patch) throws TransactionDoesNotExistException, JsonPatchException;

    ApiResponse deleteTransactionById(Long id);
}
