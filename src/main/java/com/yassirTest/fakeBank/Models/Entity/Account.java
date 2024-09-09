package com.yassirTest.fakeBank.Models.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    @NotNull
    private Customer customer;
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    @Size(min = 8)
    private String password;

    @CreatedDate
    @Column(updatable = false)
    @NotNull
    private LocalDateTime createdAt;

    @LastModifiedDate
    @NotNull
    private LocalDateTime updatedAt;

    @Column(precision = 19, scale = 2,nullable = false)
    @NotNull
    @Min(0)
    private BigDecimal balance;
}