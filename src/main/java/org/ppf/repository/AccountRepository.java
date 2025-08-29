package org.ppf.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.ppf.model.Account;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<Account, Long> {

    /**
     * Finds an account by its account number.
     * <p>
     * This method queries the underlying repository for an account with the given account number.
     * If multiple accounts exist (which should not happen), only the first matching result is returned.
     * The method returns a reactive {@link Uni} representing the asynchronous computation.
     *
     * @param accountNbr the account number to search for; must not be {@code null}
     * @return a {@link Uni} that emits the {@link Account} with the given account number,
     *         or {@code null} if no such account exists
     */
    @WithSession
    public Uni<Account> findByAccountNbr(String accountNbr) {
        return find("number", accountNbr).firstResult();
    }
}
