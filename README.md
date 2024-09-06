## Security Configuration

### Default Credentials
By default, the application uses Spring Security with basic in-memory authentication. The default credentials are:

- **Username:** `username`
- **Password:** `password`

These can be modified in the `application.properties` file:

```properties
spring.security.user.name=username
spring.security.user.password=password
spring.security.user.roles=USER



CSRF Protection
This application uses CSRF (Cross-Site Request Forgery) protection for POST, PUT, and other non-GET requests. To interact with the API programmatically (e.g., via curl, Postman, or any other HTTP client), you must include the CSRF token.


GET http://localhost:8085/api/incident/getCsrfToken
