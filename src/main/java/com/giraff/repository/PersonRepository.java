package com.giraff.repository;

import java.util.List;

import com.giraff.model.Person;

public interface PersonRepository {

	Person find(String personId);

	List<Person> findAll();

	Person persist(Person person);

	Person merge(Person person);

	void delete(String personId);


}