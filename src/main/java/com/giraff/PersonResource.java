package com.giraff;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.giraff.model.Person;
import com.giraff.repository.PersonRepository;
import com.giraff.repository.PersonRepositoryManager;
import com.giraff.repository.PersonRepositoryStub;

@Path("persons") //http://localhost:8080/person-services/webapi/persons
public class PersonResource {
	
	private PersonRepository personRepository = new PersonRepositoryManager();//PersonRepositoryStub();
	
	@DELETE
	@Path("{personId}") //http://localhost:8080/person-services/webapi/persons/person
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam ("personId") String personId) {
		
		if (personId == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		try {
			personRepository.delete(personId);
		} catch (NumberFormatException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.ok().build();
	}
	@PUT
	@Path("{personId}") //http://localhost:8080/person-services/webapi/persons/person
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateWithXML(Person person) {
		
		if (person == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		person = personRepository.merge(person);
		
		return Response.ok().entity(person).build();
	}

	@POST
	@Path("person") //http://localhost:8080/person-services/webapi/persons/person
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPersonWithXML(Person person) {
		if (person == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		personRepository.persist(person);
		
		return Response.ok().entity(person).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getAll() {

		return personRepository.findAll();
	}
	@GET
	@Path("{personId}") //http://localhost:8080/person-services/webapi/persons/123
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("personId") String personId) {
		if (personId == null || personId.length() < 1) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Person person = personRepository.find(personId);
		if (person == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(person).build();
	}
	@GET
	@Path("{personId}/gender") //http://localhost:8080/person-services/webapi/persons/123/gender
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersonGender(@PathParam("personId") String personId) {
		if (personId == null || personId.length() < 1) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Person person = personRepository.find(personId);
		if (person == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(person.getGender()).build();
	}

}
