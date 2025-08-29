package org.ppf.utils;


import org.ppf.model.Account;
import org.ppf.model.Statement;
import org.ppf.model.Transaction;
import org.ppf.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionTestData {

        public static Account getOwnAccount() {
            return Account.builder()
                    .number("2002222222")
                    .name("OWN ACCOUNT")
                    .code("0300")
                    .build();
        }

        public static Account getCounterpartyAccount() {
            return Account.builder()
                    .number("0000009504010019")
                    .name("PPF BANKA A.S.")
                    .code("6000")
                    .build();
        }

        public static TransactionType getTransactionType() {
            return TransactionType.builder()
                    .code(1012209)
                    .type("DPO")
                    .build();
        }

        public static Statement getStatement() {
            return Statement.builder()
                    .number("196")
                    .period("2022")
                    .build();
        }

        public static Transaction getTransaction1() {
            Account own = getOwnAccount();
            Account counter = getCounterpartyAccount();
            TransactionType trxType = getTransactionType();
            Statement stmt = getStatement();

            return Transaction.builder()
                    .id("20221019:0000000219")
                    .amount(BigDecimal.valueOf(1500.0))
                    .currency("CZK")
                    .bankref("PS221019SO314822")
                    .transactionId("4831716")
                    .bookingDate(LocalDate.of(2022, 10, 19))
                    .postingDate(LocalDate.of(2022, 10, 19))
                    .creditDebitIndicator("CRDT")
                    .ownAccountNumber(own.getNumber())
                    .productBankRef("PS221019SO314822")
                    .specificSymbol("12")
                    .variableSymbol("12")
                    .detail1("Posílám peníze")
                    .statement(stmt)
                    .transactionType(trxType)
                    .counterPartyAccount(counter)
                    .build();
        }

    public static Transaction getTransaction2() {
        Account own = getOwnAccount();
        Account counter = getCounterpartyAccount();
        TransactionType trxType = getTransactionType();
        Statement stmt = getStatement();

        return Transaction.builder()
                .id("20221019:0000000220")
                .amount(BigDecimal.valueOf(1999.0))
                .currency("CZK")
                .bankref("PS221019SO314822")
                .transactionId("4831701")
                .bookingDate(LocalDate.of(2022, 10, 19))
                .postingDate(LocalDate.of(2022, 10, 19))
                .creditDebitIndicator("CRDT")
                .ownAccountNumber(own.getNumber())
                .productBankRef("PS221019SO314822")
                .specificSymbol("12")
                .variableSymbol("12")
                .detail1("Trvalý příkaz 8")
                .statement(stmt)
                .transactionType(trxType)
                .counterPartyAccount(counter)
                .build();
    }

    public static Transaction getTransaction3() {
        Account own = getOwnAccount();
        Account counter = getCounterpartyAccount();
        TransactionType trxType = getTransactionType();
        Statement stmt = getStatement();

        return Transaction.builder()
                .id("20221020:0000000221")
                .amount(BigDecimal.valueOf(2000.0))
                .currency("CZK")
                .bankref("PS221020SO314823")
                .transactionId("4831720")
                .bookingDate(LocalDate.of(2022, 10, 20))
                .postingDate(LocalDate.of(2022, 10, 20))
                .creditDebitIndicator("CRDT")
                .ownAccountNumber(counter.getNumber())
                .productBankRef("PS221020SO314823")
                .specificSymbol("61")
                .variableSymbol("61")
                .detail1("Na dárky")
                .statement(stmt)
                .transactionType(trxType)
                .counterPartyAccount(own)
                .build();
    }
}
