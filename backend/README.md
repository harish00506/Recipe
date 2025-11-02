# Recipe App Backend

This is the backend part of the Recipe App project, built using Spring Boot and PostgreSQL.

## Project Structure

- `src/main/java/com/recipeapp`: Contains the main application code.
  - `RecipeAppApplication.java`: The entry point of the Spring Boot application.
  - `controller`: Contains the controllers for handling HTTP requests.
    - `HelloController.java`: A simple controller that returns a "Hello World" message.
  - `model`: Contains the data models (currently empty).
  - `repository`: Contains the repository interfaces for database access (currently empty).
  
- `src/main/resources`: Contains configuration files.
  - `application.properties`: Configuration properties for the Spring Boot application.

- `pom.xml`: Maven configuration file for managing dependencies and build settings.

## Getting Started

1. Clone the repository.
2. Navigate to the `backend` directory.
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
5. Access the API at `http://localhost:8080/hello` to see the "Hello World" response.

## Dependencies

This project uses the following dependencies:
- Spring Boot
- PostgreSQL Driver

## License

This project is licensed under the MIT License.