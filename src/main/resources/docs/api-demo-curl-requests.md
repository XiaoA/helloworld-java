# API Demo Curl Requests

Run against `http://localhost:8080`.

```bash
BASE_URL=http://localhost:8080/api/v1
ACCOUNT_ID=550e8400-e29b-41d4-a716-446655440001
USER_ID=550e8400-e29b-41d4-a716-446655440002
FORM_ID=550e8400-e29b-41d4-a716-446655440003
```

## Account

### Create account
```bash
curl -X POST "$BASE_URL/accounts" \
  -H "Content-Type: application/json" \
  -d '{
    "displayName": "Wayne Legal Group",
    "accountType": "REPRESENTATIVE"
  }'
```

### Get all accounts
```bash
curl -X GET "$BASE_URL/accounts"
```

### Get one account
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID"
```

### Update account
```bash
curl -X PUT "$BASE_URL/accounts/$ACCOUNT_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "displayName": "Wayne Legal Group LLC",
    "accountType": "REPRESENTATIVE"
  }'
```

### Delete account
```bash
curl -X DELETE "$BASE_URL/accounts/$ACCOUNT_ID"
```

## User

### Create user
```bash
curl -X POST "$BASE_URL/accounts/$ACCOUNT_ID/users" \
  -H "Content-Type: application/json" \
  -d '{
    "displayName": "Selina Kyle",
    "email": "selina@example.com",
    "role": "REGISTRANT"
  }'
```

### Get users for account
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/users"
```

### Get one user
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/users/$USER_ID"
```

### Update user
```bash
curl -X PUT "$BASE_URL/accounts/$ACCOUNT_ID/users/$USER_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "displayName": "Selina Kyle",
    "email": "selina.kyle@example.com",
    "role": "REGISTRANT"
  }'
```

### Delete user
```bash
curl -X DELETE "$BASE_URL/accounts/$ACCOUNT_ID/users/$USER_ID"
```

## Form

### Create form
```bash
curl -X POST "$BASE_URL/accounts/$ACCOUNT_ID/forms" \
  -H "Content-Type: application/json" \
  -d '{
    "formType": "F1234",
    "formTitle": "Gotham City Appeal"
  }'
```

### Get forms for account
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/forms"
```

### Get one form
```bash
curl -X GET "$BASE_URL/accounts/$ACCOUNT_ID/forms/$FORM_ID"
```

### Update form
```bash
curl -X PUT "$BASE_URL/accounts/$ACCOUNT_ID/forms/$FORM_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "formType": "F1234",
    "formTitle": "Gotham City Appeal - Revised"
  }'
```

