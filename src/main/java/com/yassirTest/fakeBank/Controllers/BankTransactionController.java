package com.yassirTest.fakeBank.Controllers;


import com.yassirTest.fakeBank.Models.EntityDTO.BankTransactionDTO;
import com.yassirTest.fakeBank.Services.BankTransactionService;
import jakarta.transaction.InvalidTransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class BankTransactionController {

    @Autowired
    private BankTransactionService bankTransactionService;

    @PostMapping
    public ResponseEntity<BankTransactionDTO> createTransaction(
            @RequestBody @Validated BankTransactionDTO bankTransactionDTO) throws InvalidTransactionException {
        BankTransactionDTO createdTransaction = bankTransactionService.createTransaction(bankTransactionDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BankTransactionDTO>> getAllTransactions() throws InvalidTransactionException {
        List<BankTransactionDTO> allTransactions = bankTransactionService.getAllTransactions();
        return new ResponseEntity<>(allTransactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankTransactionDTO> getTransactionById(@PathVariable("id") Long transactionId) throws InvalidTransactionException {
        BankTransactionDTO transactionDTO = bankTransactionService.getTransactionById(transactionId);
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public List<BankTransactionDTO> getTransactionsForAccount(@PathVariable("id") Long accountId) throws InvalidTransactionException {
        return bankTransactionService.getAccountTransaction(accountId);
    }
}
