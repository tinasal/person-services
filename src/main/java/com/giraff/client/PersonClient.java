package com.giraff.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.giraff.model.Person;

public class PersonClient {
	
	private Client client;
	
	public PersonClient() {
		client = ClientBuilder.newClient();
	}
	public Person get(String id) {
		//http://localhost:8080/person-services/webapi/persons/123
		WebTarget target = client.target("http://localhost:8080/person-services/webapi/");
		
		Response response = target.path("persons/" + id).request(MediaType.APPLICATION_JSON).get(Response.class);
		if (response.getStatus() != 200) { //200 = OK
			throw new RuntimeException(response.getStatus() + "there was an error on the server");
		}
		
		return response.readEntity(Person.class);
	}
	public List<Person> get() {
		//http://localhost:8080/person-services/webapi/persons
		WebTarget target = client.target("http://localhost:8080/person-services/webapi/");
		
		List<Person> response = target.path("persons").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Person>>(){});
		
		return response;
	}
	public Person createWithXML(Person person) {
		//http://localhost:8080/person-services/webapi/persons/person
		WebTarget target = client.target("http://localhost:8080/person-services/webapi/");
		
		Response response = target.path("persons/person")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_XML));
		
		if (response.getStatus() != 200) { //200 = OK
			throw new RuntimeException(response.getStatus() + "there was an error on the server");
		}
		return response.readEntity(Person.class);
	}
	public Person updateWithXML(Person person) {
		//http://localhost:8080/person-services/webapi/persons/person
		WebTarget target = client.target("http://localhost:8080/person-services/webapi/");
		
		Response response = target.path("persons/" + person.getId())
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(person, MediaType.APPLICATION_XML));
		
		if (response.getStatus() != 200) { //200 = OK
			throw new RuntimeException(response.getStatus() + "there was an error on the server");
		}
		return response.readEntity(Person.class);
	}

}
