CREATE TABLE delivery (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id UUID NOT NULL REFERENCES profile(id),
    receiver_id UUID NOT NULL REFERENCES receiver(id),
    status VARCHAR(50),
    order TIMESTAMPTZ,
    finished TIMESTAMPTZ
);

CREATE INDEX idx_delivery_profile_id ON delivery(profile_id);
CREATE INDEX idx_delivery_receiver_id ON delivery(receiver_id);
CREATE INDEX idx_delivery_status ON delivery(status);
