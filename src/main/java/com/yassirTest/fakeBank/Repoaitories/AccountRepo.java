package com.yassirTest.fakeBank.Repoaitories;


import com.yassirTest.fakeBank.Models.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    public List<Account> findByCustomerCustomerId(Long accountId) ;

}
