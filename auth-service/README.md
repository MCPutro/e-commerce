# Auth Service

Authentication and Authorization microservice for the E-Commerce platform built with Java Spring Boot.

## Features

- User registration and login
- JWT token-based authentication
- Role-Based Access Control (RBAC)
- User management (CRUD operations)
- Role and permission management
- Password encryption with BCrypt

## Tech Stack

- **Framework:** Spring Boot 3.2.0
- **Security:** Spring Security + JWT
- **Database:** PostgreSQL 15
- **ORM:** Spring Data JPA
- **Migration:** Flyway
- **Build Tool:** Maven
- **Container:** Docker

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose (optional)
- PostgreSQL 15+ (if running without Docker)

### Running with Docker Compose

```bash
cd auth-service
docker-compose up --build
```

The service will be available at `http://localhost:8081`

### Running Locally

1. Start PostgreSQL database:
```bash
# Using Docker
docker run -d --name auth-db \
  -e POSTGRES_DB=auth_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:15-alpine
```

2. Set environment variables:
```bash
export JWT_SECRET="your-secret-key-change-in-production-min-32-chars"
```

3. Build and run:
```bash
mvn clean package
java -jar target/auth-service-1.0.0-SNAPSHOT.jar
```

## API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login | No |
| POST | `/api/auth/logout` | Logout | Yes |

### Profile

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/auth/me` | Get current user profile | Yes |
| PUT | `/api/auth/me` | Update current user profile | Yes |

### User Management (requires `user:write` permission)

| Method | Endpoint | Description | Permission |
|--------|----------|-------------|------------|
| GET | `/api/users` | List all users (paginated) | user:write |
| POST | `/api/users` | Create new user | user:write |
| GET | `/api/users/{id}` | Get user by ID | user:write |
| PUT | `/api/users/{id}` | Update user | user:write |
| DELETE | `/api/users/{id}` | Delete user | user:write |

### Role Management (requires `role:write` permission)

| Method | Endpoint | Description | Permission |
|--------|----------|-------------|------------|
| GET | `/api/roles` | List all roles | role:write |
| GET | `/api/roles/{id}` | Get role by ID | role:write |
| GET | `/api/roles/name/{name}` | Get role by name | role:write |
| POST | `/api/roles` | Create new role | role:write |
| PUT | `/api/roles/{id}` | Update role | role:write |
| DELETE | `/api/roles/{id}` | Delete role | role:write |

## Default Credentials

After initial setup, you can login with:

- **Email:** admin@ecommerce.com
- **Password:** admin123

This account has the `owner` role with full access to all permissions.

## Roles and Permissions

### Default Roles

1. **Customer** - Basic access for regular users
   - Login, register, manage profile
   - View products, manage cart
   - Create and view own orders
   - Process payments

2. **Staff** - Operational access
   - All customer permissions
   - Create/edit products
   - View and update all orders
   - View roles

3. **Owner** - Full access
   - All permissions including:
   - User management (user:write)
   - Role management (role:write)
   - Product delete
   - Payment refunds

### Permission Format

Permissions follow the format: `resource:action`

Examples:
- `product:read` - View products
- `product:write` - Create/edit products
- `product:delete` - Delete products
- `order:create` - Create orders
- `user:write` - Manage users

## API Response Format

### Success Response

```json
{
  "status": "success",
  "code": 200,
  "message": "Operation completed successfully",
  "data": { ... },
  "timestamp": "2024-01-01T00:00:00Z"
}
```

### Error Response

```json
{
  "status": "error",
  "code": 400,
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "reason": "Invalid email format"
    }
  ],
  "timestamp": "2024-01-01T00:00:00Z"
}
```

### Pagination Response

```json
{
  "status": "success",
  "code": 200,
  "message": "Operation completed successfully",
  "data": {
    "data": [ ... ],
    "meta": {
      "page": 1,
      "perPage": 20,
      "totalItems": 100,
      "totalPages": 5
    }
  },
  "timestamp": "2024-01-01T00:00:00Z"
}
```

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `JWT_SECRET` | Secret key for JWT signing | (required) |
| `JWT_EXPIRATION` | Access token expiration (ms) | 86400000 (24h) |
| `JWT_REFRESH_EXPIRATION` | Refresh token expiration (ms) | 604800000 (7d) |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed origins | http://localhost:3000 |
| `SPRING_DATASOURCE_URL` | Database URL | jdbc:postgresql://localhost:5432/auth_db |
| `SPRING_DATASOURCE_USERNAME` | Database username | postgres |
| `SPRING_DATASOURCE_PASSWORD` | Database password | postgres |

## API Documentation

Swagger UI is available at: `http://localhost:8081/swagger-ui.html`

## Building

```bash
mvn clean package
```

## Testing

```bash
mvn test
```

## License

Private - E-Commerce Platform
