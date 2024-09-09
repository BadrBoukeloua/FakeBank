package com.yassirTest.fakeBank.Services;


import com.yassirTest.fakeBank.CustomExceptions.CustomerNotFoundException;
import com.yassirTest.fakeBank.Models.Entity.Customer;
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

        customerRepo.delete(existingCustomer);
    }

    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found"));

        customerDTO.setCustomerId(customerId);
        Customer updatedCustomer = CustomerDTO.toENTITY(customerDTO);

        return CustomerDTO.toDTO(customerRepo.save(updatedCustomer));
    }


}
