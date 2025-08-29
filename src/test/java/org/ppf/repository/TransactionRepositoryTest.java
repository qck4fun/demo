package org.ppf.repository;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.ppf.model.Transaction;

import java.math.BigDecimal;

import static org.ppf.utils.TransactionTestData.getOwnAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class TransactionRepositoryTest {

    @Inject
    TransactionRepository transactionRepository;

    @Test
    @RunOnVertxContext
    void findTransactionByAccountNbr(UniAsserter asserter) {
        var accountInDb = getOwnAccount();

        asserter.assertThat(() -> transactionRepository.findTransactionByAccountNbr(accountInDb.getNumber()), transactions -> {
            assertNotNull(transactions);
            assertEquals(5, transactions.size());
            assertEquals(0, BigDecimal.valueOf(7193.00).compareTo(transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)));
        });
    }

    @Test
    @RunOnVertxContext
    void findTransactionByAccountNbrNonExistingAccount(UniAsserter asserter) {
        asserter.assertThat(() -> transactionRepository.findTransactionByAccountNbr("NON-EXISTING-ACCOUNT"), transactions -> {
            assertNotNull(transactions);
            assertEquals(0, transactions.size());
        });
    }
}
