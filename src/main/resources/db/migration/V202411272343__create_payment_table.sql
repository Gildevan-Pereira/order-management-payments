CREATE TABLE payments_table (
    id SERIAL PRIMARY KEY,          -- Chave primária com incremento automático
    order_id INT NOT NULL,          -- ID do pedido
    order_date TIMESTAMP NOT NULL,  -- Data do pedido
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Data de criação com valor padrão
    updated_at TIMESTAMP,           -- Data de atualização
    removed_at TIMESTAMP,           -- Data de remoção
    client_cpf VARCHAR(255) NOT NULL, -- CPF do cliente
    card_number VARCHAR(255) NOT NULL, -- Número do cartão
    amount NUMERIC NOT NULL,        -- Valor
    status VARCHAR(255) NOT NULL,   -- Status
    receipt_url VARCHAR(255)        -- URL do recibo
);
