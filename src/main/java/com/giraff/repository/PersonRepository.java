package com.giraff.repository;

import java.util.List;

import com.giraff.model.Person;

public interface PersonRepository {

	Person find(Long personId);

	List<Person> findAll();

	Person persist(Person person);

	Person merge(Person person);


}