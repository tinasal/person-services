package com.giraff.repository;

import java.util.ArrayList;
import java.util.List;

import com.giraff.model.Person;
import com.giraff.model.Person.Gender;


public class PersonRepositoryStub implements PersonRepository {
	
   @Override
  	public Person find(Long personId) {
	   if (personId == 7777L) {
		   return null;
	   }
	   return createPerson("Tina", "Salen", Gender.Female, "tina-sal@hotmail.com", "", 123L);
    }
    @Override
    public List<Person> findAll() {
        return createPersonListTest();
    }

    @Override
    public Person persist(Person person) {
    	person.setId(123L);
        return person;
    }

    @Override
    public Person merge(Person person) {
     	// search db to see if we have person with id
     	// select * from Person where id = ?
     	// if rs size == 0
     	//insert into Person table
     	//else
     	//update the Person
    	return person;
    }
    private List<Person> createPersonListTest() {
    	List<Person> personList = new ArrayList<Person>();
    	personList.add(createPerson("Tina", "Salen", Gender.Female, "tina-sal@hotmail.com", "", 123L));
    	personList.add(createPerson("Anders", "Salen", Gender.Male, "as@hotmail.com", "", 124L));
    	return personList;
    }
    private Person createPerson(String givenName, String familyName, Gender gender, String mbox, String homepage, Long id) {
    	Person person = new Person();
        person.setFamilyName(familyName);
        person.setGender(gender);
        person.setGivenName(givenName);
        person.setHomepage(homepage);
        person.setMbox(mbox);
        person.setName(givenName + " " + familyName);
        person.setId(id);

    	return person;
    }
}