package com.yassirTest.fakeBank.Services;


import com.yassirTest.fakeBank.CustomExceptions.AccountNotFoundException;
import com.yassirTest.fakeBank.CustomExceptions.InsufficientFundsException;
import com.yassirTest.fakeBank.CustomExceptions.TransactionNotFoundException;
import com.yassirTest.fakeBank.Models.Entity.Account;
import com.yassirTest.fakeBank.Models.Entity.BankTransaction;
import com.yassirTest.fakeBank.Models.EntityDTO.BankTransactionDTO;
import com.yassirTest.fakeBank.Repoaitories.AccountRepo;
import com.yassirTest.fakeBank.Repoaitories.BankTransactionRepo;
import jakarta.transaction.InvalidTransactionException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BankTransactionService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private BankTransactionRepo bankTransactionRepo;

    @Transactional
    public BankTransactionDTO createTransaction(BankTransactionDTO bankTransactiondto) throws InvalidTransactionException {
        BankTransaction bankTransaction = BankTransactionDTO.toENTITY(bankTransactiondto);

        Account sender = bankTransaction.getSender() != null
                ? accountRepo.findById(bankTransaction.getSender().getAccountId())
                        .orElseThrow(() -> new AccountNotFoundException("Sender account not found"))
                : null;

        Account receiver = bankTransaction.getReceiver() != null
                ? accountRepo.findById(bankTransaction.getReceiver().getAccountId())
                        .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"))
                : null;

        BigDecimal amount = bankTransaction.getAmount();

        switch (bankTransaction.getTransactionType()) {
            case DEPOSIT:
                if (receiver == null) {
                    throw new AccountNotFoundException("Receiver account required for deposit");
                }
                receiver.setBalance(receiver.getBalance().add(amount));
                accountRepo.save(receiver);
                break;
            case WITHDRAWAL:
                if (sender == null) {
                    throw new AccountNotFoundException("Sender account required for withdrawal");
                }
                if (sender.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException("Insufficient balance");
                }
                sender.setBalance(sender.getBalance().subtract(amount));
                accountRepo.save(sender);
                break;
            case TRANSFER:
                if (sender == null || receiver == null) {
                    throw new AccountNotFoundException("Sender and Receiver accounts required for transfer");
                }
                if (sender.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException("Insufficient balance");
                }
                sender.setBalance(sender.getBalance().subtract(amount));
                receiver.setBalance(receiver.getBalance().add(amount));
                accountRepo.save(sender);
                accountRepo.save(receiver);
                break;
            default:
                throw new InvalidTransactionException("Invalid transaction type");
        }

        BankTransaction savedTransaction = bankTransactionRepo.save(bankTransaction);
        return BankTransactionDTO.toDTO(savedTransaction);
    }

    public List<BankTransactionDTO> getAllTransactions() {
        List<BankTransaction> bankTransactionList = bankTransactionRepo.findAll();
        List<BankTransactionDTO> bankTransactionDTOList = new ArrayList<>();
        for (BankTransaction bankTransaction : bankTransactionList) {

            bankTransactionDTOList.add(BankTransactionDTO.toDTO(bankTransaction));
        }
        return bankTransactionDTOList;
    }

    public List<BankTransactionDTO> getAccountTransaction(Long accountId) {

        List<BankTransaction> bankTransactionList = bankTransactionRepo.findBySenderOrReceiver(accountId);
        List<BankTransactionDTO> bankTransactionDTOList = new ArrayList<>();

        for (BankTransaction bankTransaction : bankTransactionList) {

            bankTransactionDTOList.add(BankTransactionDTO.toDTO(bankTransaction));
        }
        return bankTransactionDTOList;
    }

    public BankTransactionDTO getTransactionById(Long transactionId) {
        BankTransaction transaction = bankTransactionRepo.findById(transactionId)
                .orElseThrow(
                        () -> new TransactionNotFoundException("Transaction with ID " + transactionId + " not found"));
        return BankTransactionDTO.toDTO(transaction);
    }

}
