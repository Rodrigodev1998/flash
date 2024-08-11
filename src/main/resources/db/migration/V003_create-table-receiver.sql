CREATE TABLE receiver (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receiver_name VARCHAR(255) NOT NULL,
    receiver_street VARCHAR(255) NOT NULL,
    receiver_number VARCHAR(50) NOT NULL,
    receiver_complement VARCHAR(255) NOT NULL,
    receiver_neighborhood VARCHAR(255) NOT NULL
);

CREATE INDEX idx_receiver_name ON receiver(receiver_name);