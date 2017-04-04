package com.keddok.test.service;

import com.keddok.test.entity.Person;

/**
 * @author RMakhmutov
 * @since 31.03.2017
 */
public interface PersonService {
    Person getPerson(Integer id);
    Integer getPersonAge(Integer id);
}
