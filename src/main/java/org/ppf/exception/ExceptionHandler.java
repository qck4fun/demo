package org.ppf.exception;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;


/**
 * Global exception handler for JAX-RS that maps all {@link Throwable} exceptions to HTTP responses.
 * <p>
 * This class implements {@link ExceptionMapper<Throwable>} and is annotated with {@link Provider}
 * so that it is automatically discovered by the JAX-RS runtime.
 * <p>
 * Behavior:
 * <ul>
 *     <li>If the exception (or its root cause) is a {@link ResourceNotFoundException},
 *         returns a {@link Response.Status#NOT_FOUND} (404) with a structured {@link ErrorResponse} body.</li>
 *     <li>For all other exceptions, returns a {@link Response.Status#INTERNAL_SERVER_ERROR} (500)
 *         with the exception message as the response body.</li>
 * </ul>
 * <p>
 * The handler also logs exceptions:
 * <ul>
 *     <li>{@link ResourceNotFoundException}s are logged at WARN level.</li>
 *     <li>Other exceptions are logged at ERROR level.</li>
 * </ul>
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable throwable) {
        Throwable cause = unwrap(throwable);
        ErrorResponse resp = ErrorResponse.builder()
                .path(uriInfo.getPath())
                .message(throwable.getMessage())
                .timestamp(Instant.now())
                .build();

        if (cause instanceof ResourceNotFoundException) {
            // it might very well be error
            Log.warnf("%s thrown - %s".formatted(cause.getClass().getSimpleName(), cause.getMessage()));
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(resp)
                    .build();
        }

        // fallback for all other RuntimeExceptions
        Log.errorf("%s thrown - %s".formatted(cause.getClass().getSimpleName(), cause.getMessage()));
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(cause.getMessage())
                .build();
    }

    private Throwable unwrap(Throwable exception) {
        // unwrap reactive wrapper exceptions
        if (exception.getCause() != null && !(exception instanceof ResourceNotFoundException)) {
            return exception.getCause();
        }
        return exception;
    }
}
