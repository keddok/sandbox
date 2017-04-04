package com.keddok.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.keddok.test.entity.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author RMakhmutov
 * @since 29.05.2015
 */
public class EntityManagerTest {
    private EntityManager em;

    @Before
    public void init()
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("keddok");
        em = entityManagerFactory.createEntityManager();
    }

    @Test
    public void testUpdateQuery()
    {
        em.getTransaction().begin();
        /// do some actions
        Person person = em.find(Person.class, 1);
        person.setAge(15);
        person.setFio("FIO");

        em.getTransaction().commit();
    }

    @After
    public void after()
    {
        EntityManagerFactory emf = em.getEntityManagerFactory();
        em.close();
        emf.close();
    }
}
