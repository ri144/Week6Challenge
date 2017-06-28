package com.example.demo.repositories;

import com.example.demo.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by student on 6/28/17.
 */
public interface PersonRepo extends CrudRepository<Person, Integer> {
    public List<Person> findAllByFirstnameAndLastnameAndMidinit(String firstname, String lastname, String midinit);
}
