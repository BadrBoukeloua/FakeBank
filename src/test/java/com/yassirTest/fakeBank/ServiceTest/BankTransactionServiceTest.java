package com.yassirTest.fakeBank.ServiceTest;


import com.yassirTest.fakeBank.CustomExceptions.InsufficientFundsException;
import com.yassirTest.fakeBank.Models.Entity.Account;
import com.yassirTest.fakeBank.Models.Entity.BankTransaction;
import com.yassirTest.fakeBank.Models.EntityDTO.AccountDTO;
import com.yassirTest.fakeBank.Models.EntityDTO.BankTransactionDTO;
import com.yassirTest.fakeBank.Models.EntityDTO.CustomerDTO;
import com.yassirTest.fakeBank.Models.Enums.TransactionType;
import com.yassirTest.fakeBank.Repoaitories.AccountRepo;
import com.yassirTest.fakeBank.Repoaitories.BankTransactionRepo;
import com.yassirTest.fakeBank.Services.AccountService;
import com.yassirTest.fakeBank.Services.BankTransactionService;
import jakarta.transaction.InvalidTransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankTransactionServiceTest {

        @Mock
        private AccountRepo accountRepo;

        @Mock
        private BankTransactionRepo bankTransactionRepo;

        @InjectMocks
        private BankTransactionService bankTransactionService;

        @InjectMocks
        private AccountService accountService;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateTransaction_Deposit() throws InvalidTransactionException {



                AccountDTO accountDTOReceiver = AccountDTO.builder()
                                .accountId(2L)
                                .username("john_doe")
                                .balance(BigDecimal.valueOf(1000))
                                .build();

                BankTransactionDTO transactionDTO = BankTransactionDTO.builder()
                                .transactionType(TransactionType.DEPOSIT)
                                .receiver(accountDTOReceiver)
                                .amount(BigDecimal.valueOf(1000))
                                .build();

                Account receiver = AccountDTO.toENTITY(accountDTOReceiver);

                BankTransaction savedTransaction = BankTransactionDTO.toENTITY(transactionDTO);

                when(accountRepo.findById(2L)).thenReturn(Optional.of(receiver));
                when(bankTransactionRepo.save(any(BankTransaction.class))).thenReturn(savedTransaction);

                BankTransactionDTO createdTransaction = bankTransactionService.createTransaction(transactionDTO);

                assertNotNull(createdTransaction);
                assertEquals(BigDecimal.valueOf(1000), createdTransaction.getAmount());
                assertEquals(TransactionType.DEPOSIT, createdTransaction.getTransactionType());
                verify(accountRepo, times(1)).save(receiver);

                assertEquals(BigDecimal.valueOf(2000), receiver.getBalance());
        }

        @Test
        void testCreateTransaction_Transfer() throws InvalidTransactionException {


                AccountDTO accountDTOSender = AccountDTO.builder()
                                .accountId(1L)
                                .username("john_doe")
                                .balance(BigDecimal.valueOf(1000))
                                .build();

                AccountDTO accountDTOReceiver = AccountDTO.builder()
                                .accountId(2L)
                                .username("jane_doe")
                                .balance(BigDecimal.valueOf(500))
                                .build();

                BankTransactionDTO transactionDTO = BankTransactionDTO.builder()
                                .transactionType(TransactionType.TRANSFER)
                                .sender(accountDTOSender)
                                .receiver(accountDTOReceiver)
                                .amount(BigDecimal.valueOf(500))
                                .build();

                Account sender = AccountDTO.toENTITY(accountDTOSender);

                Account receiver = AccountDTO.toENTITY(accountDTOReceiver);

                BankTransaction savedTransaction = BankTransactionDTO.toENTITY(transactionDTO);

                when(accountRepo.findById(1L)).thenReturn(Optional.of(sender));
                when(accountRepo.findById(2L)).thenReturn(Optional.of(receiver));
                when(bankTransactionRepo.save(any(BankTransaction.class))).thenReturn(savedTransaction);

                BankTransactionDTO createdTransaction = bankTransactionService.createTransaction(transactionDTO);

                assertNotNull(createdTransaction);
                assertEquals(BigDecimal.valueOf(500), createdTransaction.getAmount());
                assertEquals(TransactionType.TRANSFER, createdTransaction.getTransactionType());

                verify(accountRepo, times(1)).save(sender);
                verify(accountRepo, times(1)).save(receiver);

                assertEquals(BigDecimal.valueOf(500), sender.getBalance());
                assertEquals(BigDecimal.valueOf(1000), receiver.getBalance());
        }

        @Test
        void testCreateTransaction_Transfer_InsufficientFunds() {

                AccountDTO accountDTOSender = AccountDTO.builder()
                                .accountId(1L)
                                .username("john_doe")
                                .balance(BigDecimal.valueOf(100))
                                .build();

                AccountDTO accountDTOReceiver = AccountDTO.builder()
                                .accountId(2L)
                                .username("jane_doe")
                                .balance(BigDecimal.valueOf(500))
                                .build();

                BankTransactionDTO transactionDTO = BankTransactionDTO.builder()
                                .transactionType(TransactionType.TRANSFER)
                                .sender(accountDTOSender)
                                .receiver(accountDTOReceiver)
                                .amount(BigDecimal.valueOf(500))
                                .build();

                Account sender = AccountDTO.toENTITY(accountDTOSender);

                Account receiver = AccountDTO.toENTITY(accountDTOReceiver);

                when(accountRepo.findById(1L)).thenReturn(Optional.of(sender));
                when(accountRepo.findById(2L)).thenReturn(Optional.of(receiver));

                assertThrows(InsufficientFundsException.class,
                                () -> bankTransactionService.createTransaction(transactionDTO));
        }
}
