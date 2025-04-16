package org.openmrs.module.helloworld.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.helloworld.api.exception.GreetingNotFoundException;
import org.openmrs.module.helloworld.api.model.Greeting;
import org.openmrs.module.helloworld.api.service.GreetingService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/greetings")
public class GreetingController {

    private static final Log log = LogFactory.getLog(GreetingController.class);

    @Autowired
    private GreetingService greetingService;

    @GetMapping
    public ResponseEntity<List<Greeting>> getAllGreetings() {
        return ResponseEntity.ok(greetingService.getAll());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Greeting>> getAllIncludingDeleted() {
        return ResponseEntity.ok(greetingService.getAllIncludingDeleted());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Greeting> getGreeting(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok(greetingService.getActiveByUuid(uuid));
        } catch (GreetingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
        if (greeting.getMessage() == null || greeting.getMessage().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(greetingService.save(greeting));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable String uuid, @RequestBody Greeting updated) {
        try {
            Greeting existing = greetingService.getActiveByUuid(uuid);
            existing.setMessage(updated.getMessage());
            return ResponseEntity.ok(greetingService.save(existing));
        } catch (GreetingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> hardDeleteGreeting(@PathVariable String uuid) {
        try {
            greetingService.hardDelete(uuid);
            return ResponseEntity.ok().build();
        } catch (GreetingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/restore/{uuid}")
    public ResponseEntity<Void> restoreGreeting(@PathVariable String uuid) {
        try {
            greetingService.restore(uuid);
            return ResponseEntity.ok().build();
        } catch (GreetingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}