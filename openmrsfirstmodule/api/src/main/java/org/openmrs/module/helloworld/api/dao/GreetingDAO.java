package org.openmrs.module.helloworld.api.dao;

import org.hibernate.SessionFactory;
import org.openmrs.module.helloworld.api.model.Greeting;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class GreetingDAO {

    private static final Log log = LogFactory.getLog(GreetingDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    // Save or update a greeting
    public Greeting save(Greeting greeting) {
        try {
            if (greeting.getUuid() == null) {
                greeting.setCreatedAt(LocalDateTime.now());
            } else {
                greeting.setUpdatedAt(LocalDateTime.now());
            }
            sessionFactory.getCurrentSession().saveOrUpdate(greeting);
            return greeting;
        } catch (Exception e) {
            log.error("Error saving greeting", e);
            throw new RuntimeException("Failed to save greeting", e);
        }
    }

    // Get greeting by UUID (including soft-deleted ones)
    public Optional<Greeting> getByUuid(String uuid) {
        try {
            Greeting greeting = sessionFactory.getCurrentSession().get(Greeting.class, uuid);
            return Optional.ofNullable(greeting);
        } catch (Exception e) {
            log.error("Error fetching greeting with UUID: " + uuid, e);
            throw new RuntimeException("Failed to fetch greeting", e);
        }
    }

    // Get active greeting by UUID (excluding soft-deleted ones)
    public Optional<Greeting> getActiveByUuid(String uuid) {
        try {
            return sessionFactory.getCurrentSession()
                    .createQuery("FROM Greeting g WHERE g.uuid = :uuid AND g.deletedAt IS NULL", Greeting.class)
                    .setParameter("uuid", uuid)
                    .uniqueResultOptional();
        } catch (Exception e) {
            log.error("Error fetching active greeting with UUID: " + uuid, e);
            throw new RuntimeException("Failed to fetch active greeting", e);
        }
    }

    // Get all active greetings
    public List<Greeting> getAll() {
        try {
            return sessionFactory.getCurrentSession()
                    .createQuery("FROM Greeting g WHERE g.deletedAt IS NULL ORDER BY g.createdAt DESC", Greeting.class)
                    .list();
        } catch (Exception e) {
            log.error("Error fetching greetings", e);
            throw new RuntimeException("Failed to fetch greetings", e);
        }
    }

    // Soft delete a greeting
    public void softDelete(Greeting greeting) {
        try {
            greeting.setDeletedAt(LocalDateTime.now());
            sessionFactory.getCurrentSession().saveOrUpdate(greeting);
        } catch (Exception e) {
            log.error("Error soft deleting greeting with UUID: " + greeting.getUuid(), e);
            throw new RuntimeException("Failed to soft delete greeting", e);
        }
    }

    // Hard delete a greeting (permanently remove from database)
    public void hardDelete(Greeting greeting) {
        try {
            sessionFactory.getCurrentSession().delete(greeting);
        } catch (Exception e) {
            log.error("Error hard deleting greeting with UUID: " + greeting.getUuid(), e);
            throw new RuntimeException("Failed to hard delete greeting", e);
        }
    }

    // Get all greetings including soft-deleted ones (for admin purposes)
    public List<Greeting> getAllIncludingDeleted() {
        try {
            return sessionFactory.getCurrentSession()
                    .createQuery("FROM Greeting g ORDER BY g.createdAt DESC", Greeting.class)
                    .list();
        } catch (Exception e) {
            log.error("Error fetching all greetings including deleted", e);
            throw new RuntimeException("Failed to fetch all greetings", e);
        }
    }

    // Restore a soft-deleted greeting
    public void restore(Greeting greeting) {
        try {
            greeting.setDeletedAt(null);
            greeting.setUpdatedAt(LocalDateTime.now());
            sessionFactory.getCurrentSession().saveOrUpdate(greeting);
        } catch (Exception e) {
            log.error("Error restoring greeting with UUID: " + greeting.getUuid(), e);
            throw new RuntimeException("Failed to restore greeting", e);
        }
    }
}