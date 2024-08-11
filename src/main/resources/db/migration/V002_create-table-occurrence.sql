CREATE TABLE occurrence (
    id UUID PRIMARY KEY,
    delivery_id UUID REFERENCES delivery(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_occurrence_delivery_id ON occurrence(delivery_id);