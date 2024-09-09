package com.yassirTest.fakeBank.Models.EntityDTO;


import com.yassirTest.fakeBank.Models.Entity.BankTransaction;
import com.yassirTest.fakeBank.Models.Enums.TransactionType;
import jakarta.transaction.InvalidTransactionException;
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

    public static BankTransactionDTO toDTO(BankTransaction bankTransaction) throws InvalidTransactionException {

        BankTransactionDTO bankTransactionDTO = BankTransactionDTO.builder()
                .transactionId(bankTransaction.getTransactionId())
                .amount(bankTransaction.getAmount())
                .transactionTime(bankTransaction.getTransactionTime())
                .transactionType(bankTransaction.getTransactionType())
                .build();

        switch (bankTransaction.getTransactionType()) {
            case DEPOSIT:
                if (bankTransaction.getReceiver() != null) {
                    bankTransactionDTO.setReceiver(AccountDTO.toDTO(bankTransaction.getReceiver()));
                }
                return bankTransactionDTO;
            case WITHDRAWAL:
                if (bankTransaction.getSender() != null) {
                    bankTransactionDTO.setSender(AccountDTO.toDTO(bankTransaction.getSender()));
                }
                return bankTransactionDTO;
            case TRANSFER:
                if (bankTransaction.getReceiver() != null) {
                    bankTransactionDTO.setReceiver(AccountDTO.toDTO(bankTransaction.getReceiver()));
                }
                if (bankTransaction.getSender() != null) {
                    bankTransactionDTO.setSender(AccountDTO.toDTO(bankTransaction.getSender()));
                }
                return bankTransactionDTO;
            default:
                throw new InvalidTransactionException("Invalid transaction type");
        }
    }

    public static BankTransaction toENTITY(BankTransactionDTO transactionDTO) throws InvalidTransactionException {

        BankTransaction bankTransaction = BankTransaction.builder()
                .transactionId(transactionDTO.getTransactionId())
                .amount(transactionDTO.getAmount())
                .transactionTime(transactionDTO.getTransactionTime() != null ? transactionDTO.getTransactionTime()
                        : LocalDateTime.now())
                .transactionType(transactionDTO.getTransactionType())
                .build();

        switch (transactionDTO.getTransactionType()) {
            case DEPOSIT:
                if (transactionDTO.getReceiver() != null) {
                    bankTransaction.setReceiver(AccountDTO.toENTITY(transactionDTO.getReceiver()));
                }
                return bankTransaction;
            case WITHDRAWAL:
                if (transactionDTO.getSender() != null) {
                    bankTransaction.setSender(AccountDTO.toENTITY(transactionDTO.getSender()));
                }
                return bankTransaction;
            case TRANSFER:
                if (transactionDTO.getReceiver() != null) {
                    bankTransaction.setReceiver(AccountDTO.toENTITY(transactionDTO.getReceiver()));
                }
                if (transactionDTO.getSender() != null) {
                    bankTransaction.setSender(AccountDTO.toENTITY(transactionDTO.getSender()));
                }
                return bankTransaction;
            default:
                throw new InvalidTransactionException("Invalid transaction type");
        }
    }
}
