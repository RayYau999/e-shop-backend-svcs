How to start the applications?
1. Go to the root directory of the project (e.g. e-shop-backend-svcs) and run ```docker compose up --build -d```
2. If you need to try with the sandbox paypal payment? You can run ```./ngrok http 8081``` to expose the local pymt-service port 8081 to internet, and then use the ngrok url to set in paypal sandbox webhook setting. (e.g. https://xxxx-xx-xx-xx-xx.ngrok-free.app/webhook/paypal)
   - "Apps and Credentials" -> Choose your appication -> add the path in "Sandbox Webhooks"


Features:
1. Microservices architecture:
- eshop-api-gateway: It serves as the entry point for all client requests, routing them to the services / act as MCP client chatbot
- eshop-login-svc: It handles user authentication and authorization, allowing users to log in and manage their accounts.
- eshop-pymt-svc: It manages payment processing, including handling payment requests and integrating with payment gateways./ act as MCP server
- payment-library: It provides common utilities and functions for payment processing, such as payment validation, transaction management, and integration with external payment providers.
- security-library: It provides common security utilities and functions, such as jwtUtils, password hashing, token generation, and authentication/authorization mechanisms.

2. Dockerization:
- Each service is containerized using Docker, allowing for easy deployment and scalability.
- Docker Compose is used to manage and orchestrate the multi-container application, making it easier to start and stop the entire application with a single command.

3. Kafka and SMTP integration:
- The application integrates with Kafka for event-driven communication between services, allowing for asynchronous processing and decoupling of services.
- The application also integrates with an SMTP server for sending emails, such as order confirmations.

4. MySQL database:
- The application uses MySQL as the database to store user information, payment details, and other relevant data.
- The database will run the prescript in data.sql file to create the necessary tables and insert sample data when the application starts.

5. Security:
- The application implements security best practices, such as password hashing and JWT-based authentication, to protect user data and ensure secure communication between services.
- The security-library provides common security utilities that can be used across all services to maintain a consistent security approach.
- The password is hashed by BCrypt algorithm, which is a strong hashing algorithm that adds salt to the password before hashing, making it more resistant to brute-force attacks and rainbow table attacks.
- JWT (JSON Web Token) is used for authentication and authorization. It allows the application to securely transmit information between parties as a JSON object, which can be verified and trusted because it is digitally signed. JWTs are commonly used for stateless authentication in web applications, allowing users to authenticate once and receive a token that can be used for subsequent requests without needing to re-authenticate.
- The signature of JWT is encrypted by HMAC SHA256 algorithm, which is a widely used cryptographic hash function that provides a secure way to verify the integrity and authenticity of data. It uses a secret key to generate a unique hash value for the input data, making it resistant to tampering and forgery. In the context of JWT, HMAC SHA256 is used to sign the token, ensuring that it has not been altered and can be trusted by the receiving party.

6. MCP (Model Context Protocol) integration:
- The application integrates with MCP to enable communication between the eshop-api-gateway (acting as the MCP client) and the eshop-pymt-svc (acting as the MCP server).
- MCP allows the client to get all the tools from the server through SSE (Server-Sent Events), although its better to use Spring webflux for this, but I want to embedded in this project so I choose to merge with SpringWebMVC.

7. PayPal webhook integration:
- The application integrates with PayPal's webhook to receive payment notifications and updates from PayPal.
- This allows the application to process payment events in real-time and update the order status accordingly.
- Ngrok is used to expose the local pymt-service port to the internet, allowing PayPal to send webhook notifications to the application during development and testing.