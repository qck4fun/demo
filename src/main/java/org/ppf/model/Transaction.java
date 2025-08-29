package org.ppf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name = "\"Transaction\"")
@NoArgsConstructor
@AllArgsConstructor  // SQL Server requires quotes due to keyword being used as table name
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trxId;

    private BigDecimal amount;

    private String currency;

    private String id;

    private String bankref;

    private String transactionId;

    private LocalDate bookingDate;

    private LocalDate postingDate;

    private String creditDebitIndicator;

    private String ownAccountNumber;

    @ManyToOne
    @JoinColumn(name = "counterPartyAccount")
    private Account counterPartyAccount;

    private String detail1;
    private String detail2;
    private String detail3;
    private String detail4;

    private String productBankRef;

    @ManyToOne
    @JoinColumn(name = "transactionType")
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "statement")
    private Statement statement;

    private String constantSymbol;
    private String specificSymbol;
    private String variableSymbol;
}
