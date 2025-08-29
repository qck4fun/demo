package org.ppf.utils;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.ppf.model.Transaction;

import java.math.BigDecimal;
import java.util.Map;

public class TransactionMatcher extends TypeSafeMatcher<Map<String, Object>> {

    private final Transaction trx;
    private String mismatch;

    public TransactionMatcher(Transaction trx) {
        this.trx = trx;
    }

    @Override
    protected boolean matchesSafely(Map<String, Object> json) {
        try {
            if (!trx.getId().equals(json.get("id"))) mismatch = "id";
            else if (!trx.getBankref().equals(json.get("bankref"))) mismatch = "bankref";
            else if (!trx.getOwnAccountNumber().equals(json.get("ownAccountNumber"))) mismatch = "ownAccountNumber";
            else if (!trx.getCreditDebitIndicator().equals(json.get("creditDebitIndicator"))) mismatch = "creditDebitIndicator";
            else if (!trx.getTransactionId().equals(json.get("transactionId"))) mismatch = "transactionId";
            else if (!trx.getProductBankRef().equals(json.get("productBankRef"))) mismatch = "productBankRef";
            else if (!trx.getSpecificSymbol().equals(json.get("specificSymbol"))) mismatch = "specificSymbol";
            else if (!trx.getVariableSymbol().equals(json.get("variableSymbol"))) mismatch = "variableSymbol";
            else if (!trx.getBookingDate().toString().equals(json.get("bookingDate"))) mismatch = "bookingDate";
            else if (!trx.getPostingDate().toString().equals(json.get("postingDate"))) mismatch = "postingDate";

            // Amount
            @SuppressWarnings("unchecked")
            Map<String, Object> amount = (Map<String, Object>) json.get("amount");
            if (!trx.getCurrency().equals(amount.get("currency"))) mismatch = "amount.currency";
            else if (!new BigDecimal(trx.getAmount().toString()).equals(new BigDecimal(amount.get("value").toString()))) mismatch = "amount.value";

            // Details
            @SuppressWarnings("unchecked")
            Map<String, Object> details = (Map<String, Object>) json.get("details");
            if (!trx.getDetail1().equals(details.get("detail1"))) mismatch = "details.detail1";
            if (trx.getDetail2() != null && !trx.getDetail2().equals(details.get("detail2"))) mismatch = "details.detail2";
            if (trx.getDetail3() != null && !trx.getDetail3().equals(details.get("detail3"))) mismatch = "details.detail3";
            if (trx.getDetail4() != null && !trx.getDetail4().equals(details.get("detail4"))) mismatch = "details.detail4";

            // Counterparty account
            if (trx.getCounterPartyAccount() != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> cpa = (Map<String, Object>) json.get("counterPartyAccount");
                if (!trx.getCounterPartyAccount().getName().equals(cpa.get("accountName"))) mismatch = "counterPartyAccount.accountName";
                if (!trx.getCounterPartyAccount().getNumber().equals(cpa.get("accountNumber"))) mismatch = "counterPartyAccount.accountNumber";
                if (!trx.getCounterPartyAccount().getCode().equals(cpa.get("bankCode"))) mismatch = "counterPartyAccount.bankCode";
            }

            // Transaction type
            if (trx.getTransactionType() != null) {
                var transactionTypeCodeString = trx.getTransactionType().getCode() != null ? trx.getTransactionType().getCode().toString() : "";
                if (!trx.getTransactionType().getType().equals(json.get("transactionType"))) mismatch = "transactionType";
                if (!transactionTypeCodeString.equals(json.get("transactionTypeCode"))) mismatch = "transactionTypeCode";
            }

            // Statement
            if (trx.getStatement() != null) {
                if (!trx.getStatement().getNumber().equals(json.get("statementNumber"))) mismatch = "statementNumber";
                if (!trx.getStatement().getPeriod().equals(json.get("statementPeriod"))) mismatch = "statementPeriod";
            }

            return mismatch == null;
        } catch (Exception e) {
            mismatch = "exception: " + e.getMessage();
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("TransactionOut JSON matches Transaction, mismatch field: " + mismatch);
    }
}

