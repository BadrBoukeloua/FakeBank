package com.yassirTest.fakeBank.Services;

import com.yassirTest.fakeBank.CustomExceptions.AccountNotFoundException;
import com.yassirTest.fakeBank.Models.Entity.Account;
import com.yassirTest.fakeBank.Models.EntityDTO.AccountDTO;
import com.yassirTest.fakeBank.Repoaitories.AccountRepo;
import com.yassirTest.fakeBank.Repoaitories.BankTransactionRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private BankTransactionRepo bankTransactionRepo;

    public AccountDTO createAccount(AccountDTO accountdto) {
        Account account = AccountDTO.toENTITY(accountdto);

        account.setPassword(hashPassword(account.getPassword()));

        return AccountDTO.toDTO(accountRepo.save(account));
    }

    public AccountDTO getAccount(Long accountId) {
        Account existingAccount = accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));
        return AccountDTO.toDTO(existingAccount);
    }

    public void deleteAccount(Long accountId) {
        Account existingAccount = accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));

        accountRepo.delete(existingAccount);
    }

    public AccountDTO updateAccount(Long accountId, AccountDTO accountdto) {
        accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));
        accountdto.setPassword(hashPassword(accountdto.getPassword()));
        accountdto.setAccountId(accountId);
        Account updatedAccount = AccountDTO.toENTITY(accountdto);

        return AccountDTO.toDTO(accountRepo.save(updatedAccount));
    }

    public BigDecimal getBalance(Long accountId) {
        Account existingAccount = accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));
        return existingAccount.getBalance();
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
