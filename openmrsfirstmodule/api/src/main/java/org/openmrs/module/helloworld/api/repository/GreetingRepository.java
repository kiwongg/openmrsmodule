package org.openmrs.module.helloworld.api.repository;

import org.openmrs.module.helloworld.api.model.Greeting;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GreetingRepository {

    private static final Map<String, Greeting> greetings = new HashMap<>();

    public List<Greeting> findAll() {
        return greetings.values().stream()
                .filter(g -> g.getDeletedAt() == null)
                .collect(Collectors.toList());
    }

    public Optional<Greeting> findByUuid(String uuid) {
        Greeting greeting = greetings.get(uuid);
        return (greeting != null && greeting.getDeletedAt() == null)
                ? Optional.of(greeting) : Optional.empty();
    }

    public Greeting save(Greeting greeting) {
        if (greeting.getUuid() == null) {
            greeting.setUuid(UUID.randomUUID().toString());
            greeting.setCreatedAt(LocalDateTime.now());
        }
        greeting.setUpdatedAt(LocalDateTime.now());
        greetings.put(greeting.getUuid(), greeting);
        return greeting;
    }

    public boolean delete(String uuid) {
        Greeting greeting = greetings.get(uuid);
        if (greeting != null && greeting.getDeletedAt() == null) {
            greeting.setDeletedAt(LocalDateTime.now());
            greetings.put(uuid, greeting);
            return true;
        }
        return false;
    }
}
