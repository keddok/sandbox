package com.keddok.test.service;

import com.keddok.test.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RMakhmutov
 * @since 04.04.2017
 */
@Service
@Transactional
public class PersonServiceJdbcTemplateImpl implements PersonService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Person getPerson(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("personId", id);

        return namedParameterJdbcTemplate.queryForObject("select id, fio, age from _person where id = :personId",
                params, (rs, rowNum) -> new Person(rs.getInt("id"), rs.getString("fio"), rs.getInt("age")));
    }

    @Override
    public Integer getPersonAge(Integer id) {
        return getPerson(id).getAge();
    }
}
