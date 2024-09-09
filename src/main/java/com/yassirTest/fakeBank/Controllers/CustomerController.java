package com.yassirTest.fakeBank.Controllers;


import com.yassirTest.fakeBank.CustomExceptions.CustomerNotFoundException;
import com.yassirTest.fakeBank.Models.EntityDTO.AccountDTO;
import com.yassirTest.fakeBank.Models.EntityDTO.CustomerDTO;
import com.yassirTest.fakeBank.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Validated CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);  
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO = customerService.getCustomer(customerId);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK); 
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long customerId, 
                                                      @RequestBody @Validated CustomerDTO customerDTO) throws CustomerNotFoundException {
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long customerId) throws CustomerNotFoundException { 
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountDTO>> getCustomerAccounts(@PathVariable("id") Long customerId) {
        List<AccountDTO> customerAccounts = customerService.customerAccounts(customerId);
        return new ResponseEntity<>(customerAccounts, HttpStatus.OK);
    }
}
