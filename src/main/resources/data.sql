INSERT INTO accounts (
    id,
    display_name,
    account_type,
    created_at,
    updated_at
)
VALUES (
           '550e8400-e29b-41d4-a716-446655440001',
           'Wayne Legal Group',
           'REPRESENTATIVE',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO accounts (
    id,
    display_name,
    account_type,
    created_at,
    updated_at
)
VALUES (
           '550e8400-e29b-41d4-a716-446655440002',
           'Sherlock Filing Services',
           'REGISTRANT',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO users (
    id,
    account_id,
    display_name,
    email,
    role
)
VALUES (
           '660e8400-e29b-41d4-a716-446655440001',
           '550e8400-e29b-41d4-a716-446655440001',
           'Bruce Wayne',
           'bruce@example.com',
           'REPRESENTATIVE'
       );

INSERT INTO users (
    id,
    account_id,
    display_name,
    email,
    role
)
VALUES (
           '660e8400-e29b-41d4-a716-446655440002',
           '550e8400-e29b-41d4-a716-446655440001',
           'Alfred Pennyworth',
           'alfred@example.com',
           'REGISTRANT'
       );

INSERT INTO users (
    id,
    account_id,
    display_name,
    email,
    role
)
VALUES (
           '660e8400-e29b-41d4-a716-446655440003',
           '550e8400-e29b-41d4-a716-446655440002',
           'Sherlock Holmes',
           'sherlock@example.com',
           'APPLICANT'
       );

INSERT INTO forms (
    id,
    account_id,
    form_type,
    form_title,
    created_at,
    updated_at
)
VALUES (
           '770e8400-e29b-41d4-a716-446655440001',
           '550e8400-e29b-41d4-a716-446655440001',
           'F100',
           'Initial Filing Packet',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO forms (
    id,
    account_id,
    form_type,
    form_title,
    created_at,
    updated_at
)
VALUES (
           '770e8400-e29b-41d4-a716-446655440002',
           '550e8400-e29b-41d4-a716-446655440002',
           'F200',
           'Supporting Documentation',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );