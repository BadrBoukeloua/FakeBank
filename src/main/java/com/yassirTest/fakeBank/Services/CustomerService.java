package com.yassirTest.fakeBank.Services;


import com.yassirTest.fakeBank.CustomExceptions.CustomerNotFoundException;
import com.yassirTest.fakeBank.CustomExceptions.InvalidDeletionException;
import com.yassirTest.fakeBank.Models.Entity.Account;
import com.yassirTest.fakeBank.Models.Entity.Customer;
import com.yassirTest.fakeBank.Models.EntityDTO.AccountDTO;
import com.yassirTest.fakeBank.Models.EntityDTO.CustomerDTO;
import com.yassirTest.fakeBank.Repoaitories.AccountRepo;
import com.yassirTest.fakeBank.Repoaitories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerDTO.toENTITY(customerDTO);
        return CustomerDTO.toDTO(customerRepo.save(customer));
    }

    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer existingCustomer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found"));
        return CustomerDTO.toDTO(existingCustomer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> allCustomers = customerRepo.findAll();
        List<CustomerDTO> allCustomersDTO = new ArrayList<>();

        if (!allCustomers.isEmpty()) {
            for (Customer allCustomer : allCustomers) {
                allCustomersDTO.add(CustomerDTO.toDTO(allCustomer));
            }
        }

        return allCustomersDTO;
    }

    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Customer existingCustomer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found"));
        List<Account> customerAccounts = accountRepo.findByCustomerCustomerId(customerId);
        if (!customerAccounts.isEmpty()) {
            throw new InvalidDeletionException(
                    "Cannot delete customer with ID " + customerId + " as it has associated accounts");
        }
        customerRepo.delete(existingCustomer);
    }

    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found"));

        customerDTO.setCustomerId(customerId);
        Customer updatedCustomer = CustomerDTO.toENTITY(customerDTO);

        return CustomerDTO.toDTO(customerRepo.save(updatedCustomer));
    }

    public List<AccountDTO> customerAccounts(Long customerId) {
        List<Account> customerAccounts = accountRepo.findByCustomerCustomerId(customerId);
        List<AccountDTO> customerAccountsDTO = new ArrayList<>();

        if (!customerAccounts.isEmpty()) {
            for (Account customerAccount : customerAccounts) {
                customerAccountsDTO.add(AccountDTO.toDTO(customerAccount));
            }
        }

        return customerAccountsDTO;
    }
}
