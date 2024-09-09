package com.yassirTest.fakeBank.Models.EntityDTO;


import com.yassirTest.fakeBank.Models.Entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO {

    private Long accountId;
    private CustomerDTO customer;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal balance;

    public static AccountDTO toDTO(Account account) {
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .customer(CustomerDTO.toDTO(account.getCustomer()))
                .username(account.getUsername())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .balance(account.getBalance())
                .build();
    }

    public static Account toENTITY(AccountDTO accountDTO) {
        return Account.builder()
                .accountId(accountDTO.getAccountId())
                .customer(CustomerDTO.toENTITY(accountDTO.getCustomer()))
                .username(accountDTO.getUsername())
                .password(accountDTO.getPassword())
                .createdAt(accountDTO.getCreatedAt())
                .updatedAt(accountDTO.getUpdatedAt())
                .balance(accountDTO.getBalance())
                .build();
    }
}
