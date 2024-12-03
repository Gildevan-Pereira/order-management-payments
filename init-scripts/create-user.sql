-- Criar usuário adicional
CREATE USER MS_PAYMENTS WITH PASSWORD 'MS_PAYMENTS';

-- Conceder privilégios ao usuário
GRANT ALL PRIVILEGES ON DATABASE mspaymentsdb TO MS_PAYMENTS;
