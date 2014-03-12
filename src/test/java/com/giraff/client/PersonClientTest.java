package com.giraff.client;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.giraff.model.Person;
import com.giraff.model.Person.Gender;

public class PersonClientTest {

	
	@Test
	public void testPutNewEntity() {
		//checks that its idempotent, if it not exists it will create a new person
		Person person = createPerson("Alfred", "Salen", Gender.Male, "as@home.se", "");
		person.setId(122L);
		
		PersonClient client = new PersonClient();
		person = client.updateWithXML(person);
		assertNotNull(person);
		System.out.println("testPutNewEntity OK ");
	}
	@Test
	public void testPutExists() {
		
		//First create a new person, then update it with new name.
		Person person = createPerson("Anders", "Salen", Gender.Male, "as@home.se", "");
	
		PersonClient client = new PersonClient();
		person = client.createWithXML(person);
		assertNotNull(person);
	
		person.setFamilyName("Anton");
		person = client.updateWithXML(person);
		assertNotNull(person);
		
		System.out.println("tet getPerson with id: " + person.getId());
		System.out.println("id: " + person.getId());
		System.out.println("familyName: " + person.getFamilyName());
		System.out.println("givenName: " + person.getGivenName());

	}
		
	@Test
	public void testCreate() {
		Person person = createPerson("Tina", "Salen", Gender.Female, "ts@home.se", "");

		PersonClient client = new PersonClient();
		person = client.createWithXML(person);
		
		assertNotNull(person);
		assertNotNull(person.getId());
		System.out.println("testCreate OK ");
		
	}
	
	@Test
	public void testGet() {
		
		PersonClient client = new PersonClient();

		Person person = client.get("123");
		assertNotNull(person);
		
		System.out.println("tet getPerson with id: " + person.getId());
		System.out.println("id: " + person.getId());
		System.out.println("familyName: " + person.getFamilyName());
		System.out.println("givenName: " + person.getGivenName());
		
	}
	@Test
	public void testGetList() {

		PersonClient client = new PersonClient();
		List<Person> persons = client.get();
		assertNotNull(persons);
		System.out.println("test getList with 2 persons: " );
		for (Person person : persons) {
			System.out.println("id: " + person.getId());
			System.out.println("familyName: " + person.getFamilyName());
			System.out.println("givenName: " + person.getGivenName());
		}
	}
	@Test(expected=RuntimeException.class)
	public void testGetWithBadRequest() {
		PersonClient client = new PersonClient();
		client.get(null);
	}
	@Test(expected=RuntimeException.class)
	public void testGetWithNotFound() {
		PersonClient client = new PersonClient();
		client.get("7777");
	}
    private Person createPerson(String givenName, String familyName, Gender gender, String mbox, String homepage) {
    	Person person = new Person();
        person.setFamilyName(familyName);
        person.setGender(gender);
        person.setGivenName(givenName);
        person.setHomepage(homepage);
        person.setMbox(mbox);
        person.setName(givenName + " " + familyName);

    	return person;
    }	
}
