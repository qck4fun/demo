package org.ppf.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.ppf.exception.ResourceNotFoundException;
import org.ppf.model.Account;
import org.ppf.model.TransactionOut;
import org.ppf.repository.AccountRepository;
import org.ppf.repository.TransactionRepository;

import java.util.List;

@ApplicationScoped
public class AccountService {

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieves all transactions associated with a specific account number.
     * <p>
     * This method performs the following steps:
     * <ol>
     *     <li>Looks up the account by the provided account number using {@code accountRepository}.</li>
     *     <li>If the account does not exist, the returned {@link Uni} fails with a {@link ResourceNotFoundException}.</li>
     *     <li>If the account exists, fetches all transactions for that account using {@code transactionRepository}.</li>
     *     <li>Maps each transaction to a {@link TransactionOut} DTO using {@link TransactionMapper}.</li>
     * </ol>
     * <p>
     * The method is reactive and returns a {@link Uni} representing the asynchronous computation.
     *
     * @param accountNbr the account number for which transactions are to be retrieved; must not be {@code null}
     * @return a {@link Uni} that emits a list of {@link TransactionOut} objects for the given account
     * @throws ResourceNotFoundException if no account exists with the provided account number
     */
    public Uni<List<TransactionOut>> getTransactionsForAccount(String accountNbr) {
        return accountRepository.findByAccountNbr(accountNbr)
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException(Account.class, "accountNbr", accountNbr))
                .flatMap(account -> transactionRepository.findTransactionByAccountNbr(accountNbr)
                        .map(transactions -> transactions.stream()
                                .map(TransactionMapper::toAccountTransactionsOut)
                                .toList()
                        )
                );
    }
}
