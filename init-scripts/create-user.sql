-- Criar usuário adicional
CREATE USER ms_payments WITH PASSWORD 'ms_payments';

-- Conceder privilégios ao usuário
GRANT ALL PRIVILEGES ON DATABASE order_management_db TO ms_payments;
