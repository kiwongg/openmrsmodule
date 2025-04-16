package org.openmrs.module.helloworld.api.exception;

public class GreetingNotFoundException extends RuntimeException {
    public GreetingNotFoundException(String uuid) {
        super("Greeting with UUID " + uuid + " not found.");
    }
}
