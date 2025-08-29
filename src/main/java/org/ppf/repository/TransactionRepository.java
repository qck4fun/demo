package org.ppf.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.reactive.mutiny.Mutiny;
import org.ppf.model.Transaction;

import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepositoryBase<Transaction, Long> {

    Mutiny.SessionFactory sessionFactory;

    public TransactionRepository(Mutiny.SessionFactory sessionFactory) { // false positive warning - bean in injected fine
        this.sessionFactory = sessionFactory;
    }

    /**
     * Retrieves all transactions associated with a specific account number.
     * <p>
     * This method executes a query to fetch all {@link Transaction} entities where the
     * {@code ownAccountNumber} matches the given account number. It also performs
     * <strong>fetch joins</strong> to eagerly load related entities:
     * <ul>
     *     <li>{@code counterPartyAccount}</li>
     *     <li>{@code transactionType}</li>
     *     <li>{@code statement}</li>
     * </ul>
     * <p>
     * The method is reactive and returns a {@link Uni} representing the asynchronous computation.
     *
     * @param accountNbr the account number for which transactions are to be retrieved; must not be {@code null}
     * @return a {@link Uni} that emits a list of {@link Transaction} objects associated with the given account number.
     *         The list will be empty if no transactions are found.
     */
    public Uni<List<Transaction>> findTransactionByAccountNbr(String accountNbr) {
        return sessionFactory.withSession(session ->
                session.createSelectionQuery(
                                "SELECT t FROM Transaction t " +
                                        "LEFT JOIN FETCH t.counterPartyAccount " +
                                        "LEFT JOIN FETCH t.transactionType " +
                                        "LEFT JOIN FETCH t.statement " +
                                        "WHERE t.ownAccountNumber = :accountNbr",
                                Transaction.class
                        )
                        .setParameter("accountNbr", accountNbr)
                        .getResultList()
        );
    }
}
