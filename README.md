# Hello World App

This is a simple "Hello World" Proof-of-concept app to learn Java, Spring Boot, and Maven.


## Entity Shapes
Currently, the JPA relationships are as follows:
```
Account -> User (1:M)
Account -> Form (1:M) (This may need be be changed to User -> Form)```
```

### Account
```
id: UUID
displayName: String
accountType: AccountType enum
users: List<User>
forms: List<Form>
```

### User
```
id: UUID
account: Account
displayName: String
email: String
role: UserRole enum
```

There is also a 'Task' feature from an earlier learning spike, but it is not part of the current demo flow.

### Form
```
id: UUID
account: Account
formType: FormType enum
formTitle: String
```

## Routes
### Account
```
GET    /api/v1/accounts
POST   /api/v1/accounts
GET    /api/v1/accounts/{accountId}
PUT    /api/v1/accounts/{accountId}
DELETE /api/v1/accounts/{accountId}
```

### User
```
GET    /api/v1/accounts/{accountId}/users
POST   /api/v1/accounts/{accountId}/users
GET    /api/v1/accounts/{accountId}/users/{userId}
PUT    /api/v1/accounts/{accountId}/users/{userId}
DELETE /api/v1/accounts/{accountId}/users/{userId}
```

### Form
```
GET    /api/v1/accounts/{accountId}/forms
GET    /api/v1/accounts/{accountId}/forms/{formId}
POST   /api/v1/accounts/{accountId}/forms
PUT    /api/v1/accounts/{accountId}/forms/{formId}
```