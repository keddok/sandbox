package com.keddok.test.service;

import com.keddok.test.entity.Person;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author RMakhmutov
 * @since 31.03.2017
 */
@Service
@Transactional
public class PersonServiceJpaImpl implements PersonService {
    @PersistenceContext
    EntityManager em;

    @Override
    public Person getPerson(Integer id) {
        return em.find(Person.class, id);
    }

    @Override
    public Integer getPersonAge(Integer id) {
        return (Integer)em.createNamedQuery("getPersonAge").setParameter("personId", id).getSingleResult();
    }
}
