package com.yassirTest.fakeBank.Models.EntityDTO;


import com.yassirTest.fakeBank.Models.Entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {

    private Long customerId;
    private String name;


    public static CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .build();
    }

    public static Customer toENTITY(CustomerDTO customerDTO) {
        return Customer.builder()
                .customerId(customerDTO.getCustomerId())
                .name(customerDTO.getName())
                .build();
    }
}
