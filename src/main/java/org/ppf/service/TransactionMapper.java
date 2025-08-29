package org.ppf.service;

import org.ppf.model.Transaction;
import org.ppf.model.TransactionOut;

import java.time.format.DateTimeFormatter;

/**
 * Utility class for mapping {@link Transaction} entities to {@link TransactionOut} DTOs.
 * <p>
 * This class provides static mapping methods and is not meant to be instantiated.
 */
public class TransactionMapper {

    private TransactionMapper() {}

    public static TransactionOut toAccountTransactionsOut(Transaction transaction) {

        TransactionOut.Amount amount = TransactionOut.Amount.builder()
                .currency(transaction.getCurrency())
                .value(transaction.getAmount())
                .build();

        TransactionOut.Details details = TransactionOut.Details.builder()
                .detail1(transaction.getDetail1())
                .detail2(transaction.getDetail2())
                .detail3(transaction.getDetail3())
                .detail4(transaction.getDetail4())
                .build();

        var counterAccount = transaction.getCounterPartyAccount();
        TransactionOut.CounterPartyAccount cpa = counterAccount != null
                ? TransactionOut.CounterPartyAccount.builder()
                .accountName(counterAccount.getName())
                .accountNumber(counterAccount.getNumber())
                .bankCode(counterAccount.getCode())
                .build()
                : null;

        return TransactionOut.builder()
                .id(transaction.getId())
                .bankref(transaction.getBankref())
                .ownAccountNumber(transaction.getOwnAccountNumber())
                .creditDebitIndicator(transaction.getCreditDebitIndicator())
                .transactionId(transaction.getTransactionId())
                .productBankRef(transaction.getProductBankRef())
                .specificSymbol(transaction.getSpecificSymbol())
                .variableSymbol(transaction.getVariableSymbol())
                .transactionType(transaction.getTransactionType() != null ? transaction.getTransactionType().getType() : null)
                .transactionTypeCode(transaction.getTransactionType() != null ? transaction.getTransactionType().getCode().toString() : null)
                .statementNumber(transaction.getStatement() != null ? transaction.getStatement().getNumber() : null)
                .statementPeriod(transaction.getStatement() != null ? transaction.getStatement().getPeriod() : null)
                .bookingDate(transaction.getBookingDate() != null ? transaction.getBookingDate().format(DateTimeFormatter.ISO_DATE) : null)
                .postingDate(transaction.getPostingDate() != null ? transaction.getPostingDate().format(DateTimeFormatter.ISO_DATE) : null)
                .amount(amount)
                .details(details)
                .counterPartyAccount(cpa)
                .build();
    }
}
