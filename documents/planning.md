# PLANNING
### The following details the steps needed to create a standalone Java application for managing favorite recipes:
> Important: Project progress can be viewed on Tracking/Agile board [Recipe management project](https://github.com/users/msundlana/projects/2)

1. Choosing Java framework: Spring Boot for building the RESTful API. It provides easy setup, dependency 
    injection, and support for RESTful services.

2. Set up project: Create a new Spring Boot project using Maven. Define the project structure, including
    packages for controllers, services, repositories, models, and tests. 

3. Define the data model: Create entities for recipes, including fields for name, ingredients, instructions, servings,
    and whether it's vegetarian. 

4. Implement CRUD operations: Create controllers to handle requests for adding, updating, removing, and fetching recipes.
    Use service classes to encapsulate business logic and interact with the repository layer. 

5. Implement filtering and searching: Add methods to filter recipes based on criteria like vegetarian status, servings,
    ingredients, and text search within instructions. 

6. Persist data in a database: PostgreSQL for development and production environment, H2 for testing. Use Spring Data JPA 
    to interact with the database and define repositories for managing recipe entities.

7. Configure Liquibase for automatic database migration.

8. Write unit tests: Create unit tests for the service and repository classes using frameworks like JUnit and Mockito.
   Test each method to ensure correctness and robustness. 

9. Write integration tests: Implement integration tests to verify the behavior of the REST endpoints and database 
   interactions. Use frameworks like Spring Boot Test and MockMvc for testing HTTP requests and responses.

10. Add logging and tracing: Use library like log4j or Zipkin tracing server for distributed logging and tracing.

11. Document the API: Use Swagger to generate API documentation automatically. Document endpoints, request/response 
    formats, and any required parameters.

12. Document architectural choices: Provide documentation explaining the architectural decisions made, 
    including the choice of framework, database, testing strategy, and any design patterns used.

13. Create instructions for running the application: Include a README file with instructions for building and running 
    the application locally. Specify any dependencies, configuration settings, and database setup steps. 

14. Set up a Git repository: Create a public repository on GitHub. Push code, documentation, and tests to the repository.

> These steps, creates a production-ready Java application for managing favorite recipes, complete with RESTful API,
> database persistence, unit tests, integration tests, and documentation.
