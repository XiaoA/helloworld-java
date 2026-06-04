# API Demo Curl Requests

Run against `http://localhost:8080`.

```bash
BASE_URL=http://localhost:8080/api/v1
ACCOUNT_ID=550e8400-e29b-41d4-a716-446655440001
USER_ID=660e8400-e29b-41d4-a716-446655440001
FORM_ID=770e8400-e29b-41d4-a716-446655440001
TOKEN=PASTE_JWT_HERE
```

## Public

### Health check
```bash
curl -X GET "http://localhost:8080/hello"
```

### Login
```bash
curl -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "user"
  }'
```

### Login failure
```bash
curl -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "wrong-password"
  }'
```

## Authenticated

Use this header on the protected requests below:

```bash
-H "Authorization: Bearer $TOKEN"
```

### Get all accounts
```bash
curl -X GET "$BASE_URL/accounts" \
  -H "Authorization: Bearer $TOKEN"
```

### Get one account
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "Authorization: Bearer $TOKEN"
```

### Create account
```bash
curl -X POST "$BASE_URL/accounts" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "displayName": "Acme Filing Organization",
    "accountType": "REGISTRANT"
  }'
```

### Update account
```bash
curl -X PUT "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "displayName": "Wayne Legal Group LLC",
    "accountType": "REPRESENTATIVE"
  }'
```

### Delete account
```bash
curl -X DELETE "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "Authorization: Bearer $TOKEN"
```

### Get users for account
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/users" \
  -H "Authorization: Bearer $TOKEN"
```

### Get one user
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/users/$USER_ID" \
  -H "Authorization: Bearer $TOKEN"
```

### Create user
```bash
curl -X POST "$BASE_URL/accounts/$ACCOUNT_ID/users" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "displayName": "Selina Kyle",
    "email": "selina@example.com",
    "role": "REGISTRANT"
  }'
```

### Update user
```bash
curl -X PUT "$BASE_URL/accounts/$ACCOUNT_ID/users/$USER_ID" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "displayName": "Bruce Wayne",
    "email": "bruce.updated@example.com",
    "role": "REPRESENTATIVE"
  }'
```

### Delete user
```bash
curl -X DELETE "$BASE_URL/accounts/$ACCOUNT_ID/users/$USER_ID" \
  -H "Authorization: Bearer $TOKEN"
```

### Get forms for account
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/forms" \
  -H "Authorization: Bearer $TOKEN"
```

### Get one form
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/forms/$FORM_ID" \
  -H "Authorization: Bearer $TOKEN"
```

### Create form
```bash
curl -X POST "$BASE_URL/accounts/$ACCOUNT_ID/forms" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "formType": "F1234",
    "formTitle": "Gotham City Appeal"
  }'
```

### Update form
```bash
curl -X PUT "$BASE_URL/accounts/$ACCOUNT_ID/forms/$FORM_ID" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "formType": "F1234",
    "formTitle": "Initial Filing Packet - Revised"
  }'
```
