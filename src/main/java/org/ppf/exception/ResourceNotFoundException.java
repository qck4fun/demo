package org.ppf.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> resourceType, String attribute, Object resourceId) {
        super("%s was not found using %s: '%s'".formatted(resourceType.getSimpleName(), attribute, resourceId));
    }
}