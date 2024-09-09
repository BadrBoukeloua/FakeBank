package com.yassirTest.fakeBank.ServiceTest;


import com.yassirTest.fakeBank.CustomExceptions.CustomerNotFoundException;
import com.yassirTest.fakeBank.Models.Entity.Customer;
import com.yassirTest.fakeBank.Models.EntityDTO.CustomerDTO;
import com.yassirTest.fakeBank.Repoaitories.CustomerRepo;
import com.yassirTest.fakeBank.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("John Doe").build();
        Customer customer = CustomerDTO.toENTITY(customerDTO);

        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);

        assertNotNull(createdCustomer);
        assertEquals("John Doe", createdCustomer.getName());
    }

    @Test
    void testGetCustomer() throws CustomerNotFoundException {
        Long customerId = 1L;
        Customer customer = Customer.builder().customerId(customerId).name("John Doe").build();

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomer(customerId);

        assertNotNull(customerDTO);
        assertEquals("John Doe", customerDTO.getName());
    }

    @Test
    void testGetCustomerNotFound() {
        Long customerId = 1L;

        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(customerId));
    }

}
