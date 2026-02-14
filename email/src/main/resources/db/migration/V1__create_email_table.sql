
CREATE TYPE email_status AS ENUM(
    'PENDING',
    'SENDING',
    'SENT',
    'FAILED',
    'RETRYING'
);

CREATE TABLE IF NOT EXISTS emails (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    email_to VARCHAR(100) NOT NULL,
    email_from VARCHAR(100) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body VARCHAR(600) NOT NULL,
    status email_status NOT NULL,
    send_at TIMESTAMP NOT NULL DEFAULT now()
);