package com.yassirTest.fakeBank.ServiceTest;


import com.yassirTest.fakeBank.CustomExceptions.AccountNotFoundException;
import com.yassirTest.fakeBank.Models.Entity.Account;
import com.yassirTest.fakeBank.Models.EntityDTO.AccountDTO;
import com.yassirTest.fakeBank.Models.EntityDTO.CustomerDTO;
import com.yassirTest.fakeBank.Repoaitories.AccountRepo;
import com.yassirTest.fakeBank.Services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerId(2L)
                .name("doe_john")
                .build();
        AccountDTO accountDTO = AccountDTO.builder()
                .customer(customerDTO)
                .username("john_doe")
                .balance(BigDecimal.valueOf(1000))
                .build();

        Account account = AccountDTO.toENTITY(accountDTO);

        when(accountRepo.save(any(Account.class))).thenReturn(account);

        AccountDTO createdAccount = accountService.createAccount(accountDTO);

        assertNotNull(createdAccount);
        assertEquals("john_doe", createdAccount.getUsername());
        assertEquals(BigDecimal.valueOf(1000), createdAccount.getBalance());
    }

    @Test
    void testGetAccountNotFound() {
        Long accountId = 1L;

        when(accountRepo.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(accountId));
    }

    @Test
    void testGetBalance() {
        Long accountId = 1L;
        Account account = Account.builder().accountId(accountId).balance(BigDecimal.valueOf(500)).build();

        when(accountRepo.findById(accountId)).thenReturn(Optional.of(account));

        BigDecimal balance = accountService.getBalance(accountId);

        assertEquals(BigDecimal.valueOf(500), balance);
    }


}
