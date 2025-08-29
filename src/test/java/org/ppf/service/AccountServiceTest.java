package org.ppf.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.ppf.utils.TransactionTestData;
import org.junit.jupiter.api.Test;
import org.ppf.exception.ResourceNotFoundException;
import org.ppf.model.Transaction;
import org.ppf.model.TransactionOut;
import org.ppf.repository.AccountRepository;
import org.ppf.repository.TransactionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class AccountServiceTest {

    @Inject
    AccountService accountService;

    @InjectMock
    AccountRepository accountRepository;

    @InjectMock
    TransactionRepository transactionRepository;

    @Test
    void testGetTransactionsForAccount_Success() {
        var account = TransactionTestData.getOwnAccount();
        Transaction tx1 = TransactionTestData.getTransaction1();
        Transaction tx2 = TransactionTestData.getTransaction2();
        List<Transaction> transactions = List.of(tx1, tx2);
        List<TransactionOut> expected = transactions.stream()
                .map(TransactionMapper::toAccountTransactionsOut)
                .toList();

        when(accountRepository.findByAccountNbr(account.getNumber())).thenReturn(Uni.createFrom().item(account));
        when(transactionRepository.findTransactionByAccountNbr(account.getNumber())).thenReturn(Uni.createFrom().item(transactions));

        List<TransactionOut> result = accountService.getTransactionsForAccount(account.getNumber()).await().indefinitely();

        assertEquals(expected, result);
        verify(accountRepository).findByAccountNbr(account.getNumber());
        verify(transactionRepository).findTransactionByAccountNbr(account.getNumber());
    }

    @Test
    void testGetTransactionsForAccount_AccountNotFound() {
        String accountNbr = "nonexistent";

        when(accountRepository.findByAccountNbr(accountNbr)).thenReturn(Uni.createFrom().nullItem());

        assertThrows(ResourceNotFoundException.class, () -> accountService.getTransactionsForAccount(accountNbr).await().indefinitely());

        verify(accountRepository).findByAccountNbr(accountNbr);
        verify(transactionRepository, never()).findTransactionByAccountNbr(anyString());
    }

    @Test
    void testGetTransactionsForAccount_NoTransactions() {
        var account = TransactionTestData.getCounterpartyAccount();

        when(accountRepository.findByAccountNbr(account.getNumber())).thenReturn(Uni.createFrom().item(account));
        when(transactionRepository.findTransactionByAccountNbr(account.getNumber())).thenReturn(Uni.createFrom().item(List.of()));

        List<TransactionOut> result = accountService.getTransactionsForAccount(account.getNumber()).await().indefinitely();

        assertTrue(result.isEmpty());
        verify(accountRepository).findByAccountNbr(account.getNumber());
        verify(transactionRepository).findTransactionByAccountNbr(account.getNumber());
    }
}
