package org.openmrs.module.helloworld.api.repository;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.openmrs.module.helloworld.api.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class GreetingRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Greeting save(Greeting greeting) {
        getSession().saveOrUpdate(greeting);
        return greeting;
    }

    public Greeting findById(Integer id) {
        return getSession().get(Greeting.class, id);
    }

    public List<Greeting> findAll() {
        return getSession().createQuery("FROM Greeting", Greeting.class).list();
    }

    public void delete(Integer id) {
        Greeting greeting = findById(id);
        if (greeting != null) {
            getSession().delete(greeting);
        }
    }
}
