CREATE TABLE payments_table (
    id SERIAL PRIMARY KEY,          -- Chave primária com incremento automático
    order_id INT NOT NULL,          -- ID do pedido
    order_date TIMESTAMP NOT NULL,  -- Data do pedido
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- Data de criação com valor padrão
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,           -- Data de atualização
    removed_at TIMESTAMP,           -- Data de remoção
    client_cpf VARCHAR(11) NOT NULL, -- CPF do cliente
    card_number VARCHAR(255) NOT NULL, -- Número do cartão
    amount numeric(10, 2) NOT NULL,     -- Valor
    status varchar(15) NOT NULL,  -- Status
    receipt_url VARCHAR(255)        -- URL do recibo
);


