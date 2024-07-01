## Start the app
### PostgreSQL Installation and Setup:
- **Install PostgreSQL:**
    - Download and install PostgreSQL from the [official PostgreSQL website](https://www.postgresql.org/download/) for your operating system.
    - During installation, set up the initial database and credentials (username and password). Note these as they will be used in your application configuration.
- **Create a New Database:**
    - Use a GUI tool like pgAdmin or the command line interface (`psql`) to manage your PostgreSQL databases.
    - Create a new database for your application. Named a database `user_management`.
    - I added `db.changelog-master.yaml`, so you don't need to configure the database manually. 
- **Configure `application.properties`:**
    - Set up the connection details for PostgreSQL in the `application.properties` or `application.yml` file:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/user_management
      spring.datasource.username=username
      spring.datasource.password=password
      spring.jpa.hibernate.ddl-auto=update
      ```

### MongoDB Installation and Configuration:
- **Install MongoDB:**
    - Download and install MongoDB from the [official MongoDB website](https://www.mongodb.com).
    - Set up the MongoDB environment, including the creation of directories for your database and log files.
- **Create a New Database:**
    - Use the MongoDB shell or a GUI like MongoDB Compass to create a new database named `Podisearch`.
    - Create new document `collection`
- **Configure `application.properties`:**
    - Include MongoDB URI and the database name in your application configuration:
      ```properties
      spring.data.mongodb.uri=mongodb+srv://username:password@cluster0.gnfdl.mongodb.net/?retryWrites=true&w=majority
      spring.data.mongodb.database=Podisearch
      ```
      Replace `username` and `password` with your MongoDB credentials

### Google OAuth Configuration:
- **Setup Google API Console:**
    - Go to the [Google API Console](https://console.developers.google.com/) and create a new project.
    - Enable the Google+ API and create credentials to get your `client-id` and `client-secret`.
- **Configure `application.properties`:**
    - Set up your application with the client ID, client secret, and scopes needed for Google OAuth:
      ```properties
      spring.security.oauth2.client.registration.google.client-id=your-google-client-id
      spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret
      spring.security.oauth2.client.registration.google.scope=profile,email
      spring.security.oauth2.resourceserver.jwt.issuer-uri=https://accounts.google.com
      spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://www.googleapis.com/oauth2/v3/certs
      ```
      `your-google-client-id` and `your-google-client-secret` with the actual credentials provided by Google.


### Start the Application with Maven:
- **Using the Command Line:**
    - Open your terminal or command prompt.
    - Navigate to the directory of your project where the `pom.xml` file is located.
    - Run the following command to start your application:
      > mvn spring-boot:run
      
      This command will compile your project if necessary, and start the Spring Boot application.

### Verify the Application is Running:
- **Check Logs and Output:**
  - Review the startup logs for any errors or warnings that might need addressing.
  - Ensure that the connections to PostgreSQL and MongoDB are established without errors.
- **Testing by Postman**
  - You can check how the app works by using [Postman](https://lively-crater-449036.postman.co/workspace/Team-Workspace~5bc02c0b-1741-4b9d-89dd-d1dfd9a8a2ff/collection/23959398-b2acf049-55e2-4473-b0e0-2e6257d16949?action=share&creator=23959398) to test its endpoints.
---
## TODO:
### Backend:
1. Fix registration system OAuth2
2. Add rating system for podcasts (with sorting capability in searches)
3. Implement new search criteria.
   - Change searching engine (use Elasticsearch for example)
   - Search by date
   - Search by podcast creator
   - Search by Collection name
   - Search by Podcast name

### Frontend:
1. Add admin panel
2. Add auth and register panel and implement OAuth2
4. Include a link to the time code.
