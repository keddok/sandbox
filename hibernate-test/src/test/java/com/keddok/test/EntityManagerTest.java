package com.keddok.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.keddok.test.entity.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

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
        updatePerson(1);
    }

    @After
    public void after()
    {
        EntityManagerFactory emf = em.getEntityManagerFactory();
        em.close();
        emf.close();
    }

    @Test
    public void testHibernateMetricsWithMultiUpdate() {
        for (int i=0; i<10000; i++)
            updatePerson(i);
    }

    private void updatePerson(int age) {
        em.getTransaction().begin();
        /// do some actions
        Person person = em.find(Person.class, 1);
        person.setAge(age);
        person.setFio("FIO");

        em.getTransaction().commit();
    }

    @Test
    public void nullParameterQuerySuccess() {
        Date birthDate = new Date();
        List<Integer> result = em.createQuery(
                "select e.id from Person e where (e.birthDate = :birthDate or :isBirthDateNull = true)")
                .setParameter("birthDate", birthDate).setParameter("isBirthDateNull", birthDate == null)
                .setMaxResults(1)
                .getResultList();
        assert result != null && result.size() > 0;
    }

    @Test
    public void nullParameterQueryFail() {
        Date birthDate = new Date();
        List<Integer> result = em.createQuery(
                "select e.id from Person e where (e.birthDate = :birthDate or :birthDate is null)")
                .setParameter("birthDate", birthDate)
                .setMaxResults(1)
                .getResultList();
        assert result != null && result.size() > 0;
    }
}
