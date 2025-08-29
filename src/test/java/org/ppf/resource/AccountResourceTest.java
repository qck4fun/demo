package org.ppf.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.ppf.utils.TransactionMatcher;
import org.junit.jupiter.api.Test;
import org.ppf.exception.ResourceNotFoundException;
import org.ppf.service.AccountService;
import org.ppf.service.TransactionMapper;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.ppf.utils.TransactionTestData.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class AccountResourceTest {

    @InjectMock
    AccountService accountService;

    @Test
    void getTransactionsSingle() {
        var account = getOwnAccount();
        var trx1 = getTransaction1();

        when(accountService.getTransactionsForAccount(any()))
                .thenReturn(Uni.createFrom().item(List.of(TransactionMapper.toAccountTransactionsOut(trx1))));

        given()
                .pathParam("accountNbr", account.getNumber())
                .when()
                .get("/accounts/{accountNbr}/transactions")
                .then()
                .statusCode(200)
                .body("[0]", new TransactionMatcher(trx1));

        verify(accountService).getTransactionsForAccount(any());
    }

    @Test
    void getTransactionsMultiple() {
        var account = getOwnAccount();
        var trx1 = getTransaction1();
        var trx2 = getTransaction2();

        when(accountService.getTransactionsForAccount(any())).thenReturn(Uni.createFrom().item(List.of(
                TransactionMapper.toAccountTransactionsOut(trx1),
                TransactionMapper.toAccountTransactionsOut(trx2))
        ));

        given()
                .pathParam("accountNbr", account.getNumber())
                .when()
                .get("/accounts/{accountNbr}/transactions")
                .then()
                .statusCode(200)
                .body("[0]", new TransactionMatcher(trx1))
                .body("[1]", new TransactionMatcher(trx2));

        verify(accountService).getTransactionsForAccount(any());
    }

    @Test
    void getTransactionsEmptyResult() {
        var account = getCounterpartyAccount();

        when(accountService.getTransactionsForAccount(any())).thenReturn(Uni.createFrom().item(emptyList()));

        given()
                .pathParam("accountNbr", account.getNumber())
                .when()
                .get("/accounts/{accountNbr}/transactions")
                .then()
                .statusCode(200)
                .body("", org.hamcrest.Matchers.hasSize(0));

        verify(accountService).getTransactionsForAccount(any());
    }

    @Test
    void getTransactionsNonExistingAccount() {
        when(accountService.getTransactionsForAccount(any())).thenThrow(ResourceNotFoundException.class);

        given()
                .pathParam("accountNbr", "123456789")
                .when()
                .get("/accounts/{accountNbr}/transactions")
                .then()
                .log().body()
                .statusCode(404);

        verify(accountService).getTransactionsForAccount(any());
    }
}
