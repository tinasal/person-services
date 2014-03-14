package com.giraff.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

//import org.apache.log4j.Logger;
import org.junit.Test;

import com.giraff.model.Person;
import com.giraff.model.Person.Gender;

public class PersonClientTest {

	//private  static final Logger logger = Logger.getLogger(PersonClient.class);
	
	@Test
	public void testDelete() {
		PersonClient client = new PersonClient();

		Person person = createPerson("Alfred", "Salen", Gender.Male, "as@home.se", "");
		person = client.createWithXML(person);
		assertNotNull(person);

		client.delete(person.getId().toString());
		try {
			person = client.get(person.getId().toString());
		} catch (Exception e) {
			if (e.getMessage().startsWith("404")) {
//				logger.info("Person not found in get, ok since we just deleted it.");
				// its ok, we got a 404 not found
				person = null;
			}
		}
		assertNull(person);
	}
	
	@Test
	public void testPutNewEntity() {
		//checks that its idempotent, if it not exists it will create a new person
		PersonClient client = new PersonClient();
		//First try to delete person to make sure it not exists
		client.delete("122");
		//create a new person with update (put)
		Person person = createPerson("Alfred", "Salen", Gender.Male, "as@home.se", "");
		person.setId(122L);
		person = client.updateWithXML(person);
		assertNotNull(person);
	}
	@Test
	public void testPutExists() {
		
		//First create a new person, then update it with new name.
		Person person = createPerson("Anders", "Salen", Gender.Male, "as@home.se", "");
	
		PersonClient client = new PersonClient();
		person = client.createWithXML(person);
		assertNotNull(person);
	
		person.setGivenName("Anton");
		person = client.updateWithXML(person);
		assertNotNull(person);
		assertEquals("Anton", person.getGivenName());
//		logger.info("Nytt namn" + person.getGivenName());
	}
		
	@Test
	public void testCreate() {
		Person person = createPerson("Tina", "Salen", Gender.Female, "ts@home.se", "");

		PersonClient client = new PersonClient();
		person = client.createWithXML(person);
		
		assertNotNull(person);
		assertNotNull(person.getId());
	}
	
	@Test
	public void testGet() {
		
		PersonClient client = new PersonClient();

		Person person = createPerson("Tina", "Salen", Gender.Female, "ts@home.se", "");
		person = client.createWithXML(person);
		assertNotNull(person);
		assertNotNull(person.getId());
		person = client.get(person.getId().toString());
		assertNotNull(person);
		
	}
	@Test
	public void testGetList() {

		PersonClient client = new PersonClient();
		
		Person person = createPerson("Tina", "Salen", Gender.Female, "ts@home.se", "");
		person = client.createWithXML(person);

		List<Person> persons = client.get();
		assertNotNull(persons);
	}
	@Test(expected=RuntimeException.class)
	public void testGetWithBadRequest() {
		PersonClient client = new PersonClient();
		client.get("");
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
