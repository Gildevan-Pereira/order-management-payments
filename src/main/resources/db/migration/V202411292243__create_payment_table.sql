CREATE TABLE payments_table (
    id SERIAL PRIMARY KEY,          -- Chave primária com incremento automático
    order_id INT NOT NULL,          -- ID do pedido
    order_date TIMESTAMP NOT NULL,  -- Data do pedido
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL, -- Data de criação com valor padrão
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,           -- Data de atualização
    removed_at TIMESTAMP,           -- Data de remoção
    client_cpf VARCHAR(11) NOT NULL, -- CPF do cliente
    card_holder_name VARCHAR(50) NOT NULL, -- Nome do cartão
    card_number VARCHAR(20) NOT NULL, -- Número do cartão
    card_brand VARCHAR(20) NOT NULL, -- Bandeira do cartão
    card_cvv VARCHAR(4) NOT NULL, -- Dígito verificador do cartão
    card_type VARCHAR(10) NOT NULL, -- Dígito verificador do cartão
    card_expiration_date VARCHAR(7) NOT NULL, -- Dígito verificador do cartão
    amount numeric(10, 2) NOT NULL,     -- Valor
    status varchar(15) NOT NULL,  -- Status
    receipt_url VARCHAR(255)        -- URL do recibo
);
