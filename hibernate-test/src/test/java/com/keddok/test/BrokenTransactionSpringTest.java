package com.keddok.test;

import com.keddok.test.entity.Person;
import com.keddok.test.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author RMakhmutov
 * @since 29.05.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-broken-transaction-spring-context.xml")
public class BrokenTransactionSpringTest implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private PersonService personService;

    @Override
    public void setApplicationContext(ApplicationContext context)
    {
        this.applicationContext = context;
    }

    @Test
    @Transactional()
    public void testTransactionalQuery() {
        Person person = personService.getPerson(1);
        assert person != null;
        Integer age = person.getAge();
        Assert.notNull(age);
    }

    /**
     * This test checks the second run after first run that broke thread-bound transaction.
     * @throws InterruptedException
     */
    @Test
    public void testInvalidTransactionInThreadPool() throws InterruptedException {
        runMultipleThreads(1,2, 3000, this::getAge);
        Thread.sleep(600000); /// main thread should sleep enough for debug purposes
    }

    private List<String> runMultipleThreads(int threadCount, int repeatCount, int repeatIntervalInMilliseconds, Supplier<String> threadResultSupplier) {
        final List<String> values = Collections.synchronizedList(new ArrayList<String>());

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < repeatCount; j++) {
                    long nanoTime = System.nanoTime();
                    try {
                        String result = threadResultSupplier.get();
                        values.add(result);
                        System.out.println(result);
                        System.out.println((System.nanoTime() - nanoTime));
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    try {
                        Thread.sleep(repeatIntervalInMilliseconds);
                    } catch (InterruptedException iex){
                        System.out.println(iex.getMessage());
                    }
                }
            }).start();
        }

        return values;
    }

    /// sequential run method
    public synchronized String getFio()
    {
        try {
            return personService.getPerson(1).getFio();
        } catch (Exception ex){
            return null;
        }
    }

    /// sequential run method
    public synchronized String getAge()
    {
        try {
            return personService.getPersonAge(1).toString();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
