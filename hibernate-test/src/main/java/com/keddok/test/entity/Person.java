package com.keddok.test.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author RMakhmutov
 * @since 29.05.2015
 */
@NamedQueries({
        @NamedQuery (
                name="getPersonAge",
                query="SELECT p.age FROM Person p WHERE p.id = :personId"
        )
})
@Entity
@DynamicUpdate
@Table(name = "_person")
public class Person {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String fio;

    @Column (nullable = false)
    private Integer age;

    @Column(name = "birth_date")
    private Date birthDate;

    public Person() {
    }

    public Person(Integer id, String fio, Integer age) {
        this.id = id;
        this.fio = fio;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
