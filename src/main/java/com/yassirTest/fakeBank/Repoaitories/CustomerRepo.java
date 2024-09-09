package com.yassirTest.fakeBank.Repoaitories;


import com.yassirTest.fakeBank.Models.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
}
