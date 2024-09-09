# Banking API Documentation

## Project Overview

This project is a simple Banking API built with Spring Boot, Spring Data JPA, and MySQL. The API allows users to manage bank accounts, perform transactions (transfer, deposit, withdrawal), and manage customers.

## Technologies Used

- **Spring Boot**: REST API development
- **Spring Data JPA**: Database persistence
- **MySQL**: Database
- **JUnit & Mockito**: Testing

## Core Features

### Accounts

- Create, update, delete, and retrieve bank accounts.
- Retrieve accounts balance.
- Passwords are securely hashed using BCrypt.

### Transactions

- Perform transactions between accounts (transfer, deposit, and withdrawal).
- Retrieve all transactions or transactions specific to an account.
- Ensures balance validation and exception handling (e.g., insufficient funds).

### Customers

- Create, update, delete, and retrieve customer details.
- Retrieve all accounts associated with a specific customer.

## Architecture Overview

### Entities

- **Account**: Represents a bank account linked to a customer with a balance.
- **Customer**: Represents a bank customer.
- **BankTransaction**: Represents a bank transaction, such as a transfer, deposit, or withdrawal.

### DTOs

- **AccountDTO**: Used to transfer account data between the service and controller layers.
- **CustomerDTO**: Used to transfer customer data.
- **BankTransactionDTO**: Handles the transfer of transaction data.

### Services

- **AccountService**: Business logic for managing accounts (create, update, delete, retrieve).
- **BankTransactionService**: Business logic for managing transactions.
- **CustomerService**: Business logic for managing customer details.

### Repositories

- **AccountRepo**: Repository for managing Account entities.
- **BankTransactionRepo**: Repository for managing BankTransaction entities.
- **CustomerRepo**: Repository for managing Customer entities.

### Controllers

- **AccountController**: Exposes API endpoints for account management.
- **BankTransactionController**: Exposes API endpoints for transaction management.
- **CustomerController**: Exposes API endpoints for customer management.

## API Endpoints

### Account Endpoints

- **POST /api/accounts**: Create a new account.
- **GET /api/accounts/{id}**: Retrieve an account by ID.
- **PUT /api/accounts/{id}**: Update an existing account.
- **DELETE /api/accounts/{id}**: Delete an account.
- **GET /api/accounts/{id}/balance**: Retrieve the balance for a specific account.

### Transaction Endpoints

- **POST /api/transactions**: Perform a transaction (transfer, deposit, withdrawal).
- **GET /api/transactions**: Retrieve all transactions.
- **GET /api/transactions/{id}**: Retrieve a transaction by ID.
- **GET /api/transactions/account/{id}**: Retrieve all transactions for an account.

### Customer Endpoints

- **POST /api/customers**: Create a new customer.
- **GET /api/customers/{id}**: Retrieve a customer by ID.
- **GET /api/customers**: Retrieve all customers.
- **PUT /api/customers/{id}**: Update a customer by ID.
- **DELETE /api/customers/{id}**: Delete a customer.
- **GET /api/customers/{id}/accounts**: Retrieve all accounts associated with a customer.

## Database Setup

- The project connects to a MySQL database using the provided JDBC URI, username, and password.
- Database schema updates are managed using `spring.jpa.hibernate.ddl-auto=update`, which automatically applies changes based on the entity definitions.

## Testing

- JUnit and Mockito are used for unit testing.
- Service tests mock the repository layer to ensure isolation of business logic.
- Key methods are tested, including account creation, transactions, and exception handling (e.g., insufficient funds, account not found).

## How to Run the Project

1. Clone the repository.
2. Configure the MySQL database settings in the `application.properties` file.
3. Run the project using a suitable Java IDE (used IntelliJ IDEA).
4. Access the API at `http://localhost:8080`.

## Potential Features to Add

- **Separate DTOs for Request and Response**: Currently, the same DTOs are used for both request and response. Separating these can provide better control over which fields are exposed (e.g., hiding sensitive information like passwords).
- **Storing Passwords in a Separate Table**: For added security, passwords could be stored in a separate table, linked to the Account entity, to improve password management.
- **Pagination for Transactions**: Adding pagination for large transaction datasets would improve performance and usability.
- **Advanced Validation**: Adding more comprehensive validation for request data (e.g., valid email, minimum balance).
- **Better Handling For Deletion**: Now i just prevent any deletion of a Customer with assosiated Accounts , and any Account with associated Transactions but it can be handeled in better ways (e.g., Soft deletion , closing).
- **Better Test Coverage**: Adding a test for every function in my business logic and testing the controllers and their behavior.
