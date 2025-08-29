package org.ppf.resource;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.ppf.model.TransactionOut;
import org.ppf.service.AccountService;

import java.util.List;

@Path("accounts")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "Get transactions for an account",
            description = "Returns the list of transactions associated with the specified account number."
    )
    @APIResponse(responseCode = "200", description = "List of transactions",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = TransactionOut.class)
            )
    )
    @APIResponse(responseCode = "404", description = "Account not found")
    @GET
    @Path("/{accountNbr}/transactions")
    public Uni<List<TransactionOut>> getTransactions(@PathParam("accountNbr") String accountNbr) {
        return Uni.createFrom().item(accountNbr)
                .invoke(acctNbr -> Log.infof("Fetching transactions for accountNbr=%s", acctNbr))
                .flatMap(accountService::getTransactionsForAccount)
                .invoke(result -> Log.infof("Found %d transactions for accountNbr=%s", result.size(), accountNbr));
    }
}
