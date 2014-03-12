package com.giraff.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.giraff.model.Person;


public class PersonRepositoryManager implements PersonRepository {
	
	private static final String PERSISTENCE_UNIT_NAME = "Person";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	@PersistenceContext 
	private EntityManager em = factory.createEntityManager();

   @Override
  	public Person find(Long personId) {
       Person person = em.find(Person.class,personId);
       return person;
    }
    @Override
    public List<Person> findAll() {
        TypedQuery<Person> query = em.createQuery(
                "SELECT p FROM Person p ORDER BY p.familyName", Person.class);
            return query.getResultList();
    }

    @Override
    public Person persist(Person person) {
    	long id;
        try{
        	  TypedQuery<Long> query = em.createQuery(
        		      "SELECT Max(p.id) FROM Person p", Long.class);
        		  id = query.getSingleResult();
        		  
        }catch(Exception e){
        	System.out.println("Couldn't get max ID");
        	id = 0;
        }
        try{
      		  person.setId(id);
      		  em.persist(person);
      		  em.flush();
      }catch(Exception e){
      	System.out.println("Couldn't persist");
      }
        finally{
        	try {
				em.close();
			} catch (Exception e) {
			}
        }
        return person;
    }

    @Override
    public Person merge(Person person) {
        try{
        	em.merge(person);
        	em.flush();
        }catch(Exception e){
        }
        finally{
        	try {
				em.close();
			} catch (Exception e) {
			}
        }
    	return person;
    }

}