package com.giraff.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.giraff.model.Person;


public class PersonRepositoryManager implements PersonRepository {
	
	private static final String PERSISTENCE_UNIT_NAME = "PersonPU";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	@PersistenceContext 
	private EntityManager em = factory.createEntityManager();
	private  static final Logger logger = LogManager.getLogger(PersonRepositoryManager.class);
	
	@Override
  	public Person find(String personId) {
       Long id;
	   try {
		   id = Long.parseLong(personId);
	} catch (Exception e) {
		return null;
	}
	   Person person = em.find(Person.class, id);
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
    		id++; //add one to id

    	}catch(Exception e){
    		logger.debug("No rows found in table Person");
    		//No rows in db, id = 0
    		id = 0;
    	}
		person.setId(id);
    	try{
    		em.getTransaction().begin();
    		em.persist(person);
    		em.getTransaction().commit();
    	}catch(Exception e){
    		logger.debug("Couldn't persist in table Person");
    		throw new RuntimeException(e);
    	}
    	finally{
    		try {
    			em.close();
    		} catch (Exception e) {
        		logger.debug("Couldn't close connectionn");
        		throw new RuntimeException(e);
    		}
    	}
    	return person;
    }

    @Override
    public Person merge(Person person) {
        try{
    		em.getTransaction().begin();
        	em.merge(person);
    		em.getTransaction().commit();
        }catch(EntityNotFoundException e){
        	em.persist(person);
    		em.getTransaction().commit();
        }catch(Exception e){
    		logger.debug("Couldn't merge in table Person");
    		throw new RuntimeException(e);
        }
        finally{
        	try {
				em.close();
			} catch (Exception e) {
        		logger.debug("Couldn't close connectionn");
        		throw new RuntimeException(e);
			}
        }
    	return person;
    }
    @Override
    public void delete(String personId) {
    	Long id;
    	try {
    		id = Long.parseLong(personId);
    	} catch (NumberFormatException e) {
    		throw e;
    	}
    	try{
    		em.getTransaction().begin();
    		Person person = em.find(Person.class, id);
    		if (person != null) {
        		em.remove(person);
    		}
    		em.getTransaction().commit();
    	}catch(Exception e){
    		logger.debug("Couldn't delete from table Person");
    		throw new RuntimeException(e);
    	}
    	finally{
    		try {
    			em.close();
    		} catch (Exception e) {
      		logger.debug("Couldn't close connectionn");
        		throw new RuntimeException(e);
    		}
    	}
    	return;
    }

}