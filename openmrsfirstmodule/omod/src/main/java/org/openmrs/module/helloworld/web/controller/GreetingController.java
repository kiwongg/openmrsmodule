    package org.openmrs.module.helloworld.web.controller;

    import org.apache.commons.logging.Log;
    import org.apache.commons.logging.LogFactory;
    import org.openmrs.module.helloworld.api.model.Greeting;
    import org.openmrs.module.helloworld.api.repository.GreetingRepository;
    import org.openmrs.module.webservices.rest.web.RestConstants;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/rest/" + RestConstants.VERSION_1 + "/greetings")
    public class GreetingController {

        private static final Log log = LogFactory.getLog(GreetingController.class);

        @Autowired
        private GreetingRepository repository;

        @GetMapping
        public List<Greeting> getAllGreetings() {
            log.info("Fetching all greetings...");
            return repository.findAll();
        }

        @GetMapping("/{id}")
        public Greeting getGreeting(@PathVariable("id") Integer id) {
            return repository.findById(id);
        }

        @PostMapping
        public Greeting createGreeting(@RequestBody Greeting greeting) {
            return repository.save(greeting);
        }

        @PutMapping("/{id}")
        public Greeting updateGreeting(@PathVariable("id") Integer id, @RequestBody Greeting updatedGreeting) {
            Greeting greeting = repository.findById(id);
            if (greeting != null) {
                greeting.setMessage(updatedGreeting.getMessage());
                return repository.save(greeting);
            }
            return null;
        }

        @DeleteMapping("/{id}")
        public void deleteGreeting(@PathVariable("id") Integer id) {
            Greeting greeting = repository.findById(id);
            if (greeting != null) {
                repository.delete(id); // or repository.delete(greeting); both valid now
            }
        }
    }
