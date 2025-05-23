# Secure-JWT-custom-Authentication-System-with-Spring-Security
Developed a robust authentication and authorization system as part of a backend security module using Spring Boot and Spring Security. This project includes complete JWT (JSON Web Token) integration for stateless session management and BCrypt-based password encryption for secure user credential storage.

Implemented a custom authentication provider to validate user credentials against encrypted values in the database, and built a JWT authorization filter to inspect incoming requests for valid tokens before granting access to protected resources. Also ensured that routes like /register and /login remain public while securing all others via role-based access control.

This project demonstrates real-world security by Implementing end-to-end JWT authentication and encryption using Spring Security, covering most of the core concepts used in modern backend APIs.
