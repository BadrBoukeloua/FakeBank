package com.yassirTest.fakeBank.Models.EntityDTO;


import com.yassirTest.fakeBank.Models.Entity.BankTransaction;
import com.yassirTest.fakeBank.Models.Enums.TransactionType;
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
public class BankTransactionDTO {

    private Long transactionId;
    private AccountDTO sender;
    private AccountDTO receiver;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private TransactionType transactionType;

    public static BankTransactionDTO toDTO(BankTransaction bankTransaction) {

        BankTransactionDTO bankTransactionDTO = BankTransactionDTO.builder()
                .transactionId(bankTransaction.getTransactionId())
                .amount(bankTransaction.getAmount())
                .transactionTime(bankTransaction.getTransactionTime())
                .transactionType(bankTransaction.getTransactionType())
                .sender(AccountDTO.toDTO(bankTransaction.getSender()))
                .receiver(AccountDTO.toDTO(bankTransaction.getReceiver()))
                .build();

       return bankTransactionDTO ;
    }

    public static BankTransaction toENTITY(BankTransactionDTO transactionDTO) {

        BankTransaction bankTransaction = BankTransaction.builder()
                .transactionId(transactionDTO.getTransactionId())
                .amount(transactionDTO.getAmount())
                .transactionTime(transactionDTO.getTransactionTime() != null ? transactionDTO.getTransactionTime()
                        : LocalDateTime.now())
                .transactionType(transactionDTO.getTransactionType())
                .sender(AccountDTO.toENTITY(transactionDTO.getSender()))
                .receiver(AccountDTO.toENTITY(transactionDTO.getReceiver()))
                .build();

       return bankTransaction ;
    }
}
