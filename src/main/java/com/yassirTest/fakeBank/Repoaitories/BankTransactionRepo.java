package com.yassirTest.fakeBank.Repoaitories;


import com.yassirTest.fakeBank.Models.Entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionRepo extends JpaRepository<BankTransaction, Long> {
    @Query("SELECT t FROM BankTransaction t WHERE t.sender.accountId = :account OR t.receiver.accountId = :account")
    public List<BankTransaction> findBySenderOrReceiver(Long account);

}
