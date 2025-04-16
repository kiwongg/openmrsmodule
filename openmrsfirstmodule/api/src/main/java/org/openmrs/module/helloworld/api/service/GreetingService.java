package org.openmrs.module.helloworld.api.service;

import org.openmrs.module.helloworld.api.dao.GreetingDAO;
import org.openmrs.module.helloworld.api.exception.GreetingNotFoundException;
import org.openmrs.module.helloworld.api.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GreetingService {

    @Autowired
    private GreetingDAO greetingDAO;

    public Greeting save(Greeting greeting) {
        return greetingDAO.save(greeting);
    }

    public Greeting getByUuid(String uuid) {
        Optional<Greeting> greetingOptional = greetingDAO.getByUuid(uuid);
        return greetingOptional.orElseThrow(() -> new GreetingNotFoundException(uuid));
    }

    public Greeting getActiveByUuid(String uuid) {
        Optional<Greeting> greetingOptional = greetingDAO.getActiveByUuid(uuid);
        return greetingOptional.orElseThrow(() -> new GreetingNotFoundException(uuid));
    }

    public List<Greeting> getAll() {
        return greetingDAO.getAll();
    }

    public List<Greeting> getAllIncludingDeleted() {
        return greetingDAO.getAllIncludingDeleted();
    }



    public void hardDelete(String uuid) {
        Greeting greeting = getByUuid(uuid);
        greetingDAO.hardDelete(greeting);
    }

    public void restore(String uuid) {
        Greeting greeting = getByUuid(uuid);
        greetingDAO.restore(greeting);
    }
}