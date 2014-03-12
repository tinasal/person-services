A simple server that handles persons with a REST-api.

In the initial commit only a stubbed repository handles the persistence.
In later versions real persistence will be added with JPA and objectDB.

To test the project you can start a server (i have used Tomcat in Eclipse)
and you can reach the service with following url's:

GET list of persons:
http://localhost:8080/person-services/webapi/persons

GET person{personId}
http://localhost:8080/person-services/webapi/persons/123

GET person with gender
http://localhost:8080/person-services/webapi/persons/123/gender

POST person
http://localhost:8080/person-services/webapi/persons/person

PUT person
http://localhost:8080/person-services/webapi/persons/person

or with the Junit testclass PersonClientTest