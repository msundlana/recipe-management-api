# Recipe Manager Application

A standalone Java application for managing favorite recipes.

## Dependencies

- Java 17
- Maven
- PostgreSQL

## Configuration Settings

Before running the application, make sure to update `.env` file with environmental variables used to configure the database connection settings in the `application.properties` file located in the `src/main/resources` directory:

DATABASE_NAME=recipe_db
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password

## To do 
update DATABASE_HOST if deploying application on docker or prod ev


Replace `your_username` and `your_password` with your actual database credentials. Also, ensure that the PostgreSQL server is running on your local machine.

## Database Setup

Create a PostgreSQL database named `recipe_db` using the following command:


## Building the Application

To build the application, navigate to the project directory and run the following Maven command:


The application will start, and you can access it by navigating to `http://localhost:8080` in your web browser.

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui/index.html`. You can use this interface to interact with the RESTful endpoints and explore the available operations.
Once the App is running you can also view `http://localhost:8080/actuator`. Monitoring our app, gathering
metrics,
and understanding traffic or the state of our database becomes trivial with this dependency used.
The actuator mainly exposes operational information about the running application — health, metrics, info, dump,
env, etc. It uses HTTP endpoints or JMX beans to enable us to interact with it.




