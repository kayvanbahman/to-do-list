# Todo backend

Spring Boot API for the Todo application.

## Stack

- Select one persistent database at startup: MySQL or MongoDB. Both profiles store users, refresh tokens, and todos.
- Redis: expiring email OTP codes
- Kafka: asynchronous email notification events
- Mailpit: local email inspection

## Run dependencies

```bash
docker compose --profile mysql up -d
```

For local MongoDB instead, run `docker compose --profile mongo up -d`. On the Mac server, use installed services directly; Docker is optional for local development.

Start the API with Java 17 and Maven (MySQL is the default profile):

```bash
SPRING_PROFILES_ACTIVE=mysql mvn spring-boot:run
```

To use only MongoDB for persistent data instead:

```bash
SPRING_PROFILES_ACTIVE=mongo mvn spring-boot:run
```

Set `MYSQL_URL`, `MYSQL_USERNAME`, and `MYSQL_PASSWORD` for MySQL, or `MONGODB_URI` for MongoDB. Redis, Kafka, and SMTP settings are shared by both profiles. The profiles select storage; they do not migrate data between databases. Activate exactly one of `mysql` or `mongo` at a time.

Mailpit is available at `http://localhost:8025`.

## Main endpoints

```text
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
POST /api/auth/logout
POST /api/auth/otp/request
POST /api/auth/otp/verify

GET    /api/todos
POST   /api/todos
PATCH  /api/todos/{id}
DELETE /api/todos/{id}
```

Copy the access token from the login response into the `Authorization: Bearer <token>` header for Todo requests.
