CREATE TABLE credit_ledger (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    amount DOUBLE,
    type VARCHAR(20), -- CREDIT / DEBIT
    reference_txn_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);