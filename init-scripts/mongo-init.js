db = db.getSiblingDB('order_management_db');
// Criar usuário adicional
db.createUser({
  user: "ms_payments",
  pwd: "ms_payments",
//  Conceder privilégios ao usuário
  roles: [
    { role: "readWrite", db: "order_management_db" }
  ]
});