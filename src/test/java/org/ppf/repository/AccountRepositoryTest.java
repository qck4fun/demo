package org.ppf.repository;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.ppf.utils.TransactionTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AccountRepositoryTest extends TransactionTestData {

    @Inject
    AccountRepository accountRepository;

    @Test
    @RunOnVertxContext
    void testFindByAccountNbr(UniAsserter asserter) {
        var accountInDb = getOwnAccount();

        asserter.assertThat(() -> accountRepository.findByAccountNbr(accountInDb.getNumber()), account ->
                assertAll(
                        () -> assertNotNull(account, "Account should not be null"),
                        () -> assertEquals(accountInDb.getNumber(), account.getNumber()),
                        () -> assertEquals(accountInDb.getName(), account.getName()),
                        () -> assertEquals(accountInDb.getCode(), account.getCode())
                )
        );
    }

    @Test
    @RunOnVertxContext
    void testFindByAccountNbr_NotFound(UniAsserter asserter) {
        asserter.assertThat(() -> accountRepository.findByAccountNbr("999999999"), account ->
                assertNull(account, "Account should be null for non-existing accountNbr")
        );
    }
}