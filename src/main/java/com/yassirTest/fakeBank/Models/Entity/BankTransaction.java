package com.yassirTest.fakeBank.Models.Entity;


import com.yassirTest.fakeBank.Models.Enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_account_id", referencedColumnName = "accountId")
    private Account sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_account_id", referencedColumnName = "accountId")
    private Account receiver;

    @Column(precision = 19, scale = 2)
    @NotNull
    @Min(1)
    private BigDecimal amount;

    @CreatedDate
    @Column(updatable = false)
    @NotNull
    private LocalDateTime transactionTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType transactionType;
}
