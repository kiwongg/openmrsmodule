package org.openmrs.module.helloworld.api.model;
import javax.persistence.*;

@Entity
@Table(name = "greeting")
public class Greeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String message;

    // Constructors
    public Greeting() {}
    public Greeting(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
