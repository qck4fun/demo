package org.ppf.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionOut {
    private String id;
    private String bankref;
    private String ownAccountNumber;
    private String creditDebitIndicator;
    private String transactionId;
    private String productBankRef;
    private String specificSymbol;
    private String variableSymbol;
    private String transactionType;
    private String transactionTypeCode;
    private String statementNumber;
    private String statementPeriod;
    private String bookingDate;
    private String postingDate;
    private Amount amount;
    private Details details;
    private CounterPartyAccount counterPartyAccount;

    @Data
    @Builder
    public static class Amount {
        private String currency;
        private BigDecimal value;
    }

    @Data
    @Builder
    public static class Details {
        private String detail1;
        private String detail2;
        private String detail3;
        private String detail4;
    }

    @Data
    @Builder
    public static class CounterPartyAccount {
        private String accountName;
        private String accountNumber;
        private String bankCode;
    }
}