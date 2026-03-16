-- V7__seed_default_admin_user.sql
-- Create default admin/owner user
-- Default password: password123 (BCrypt hashed)
-- Password hash: $2a$10$ARKx.CsTskGFiBiDJuMP6.F5WHNzUy1LDEg8ts32bKBeBysCzngmO

INSERT INTO users (id, email, password_hash, name, role_id, active) VALUES
    ('c1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5c01', 
     'admin@ecommerce.com', 
     '$2a$10$ARKx.CsTskGFiBiDJuMP6.F5WHNzUy1LDEg8ts32bKBeBysCzngmO',
     'System Administrator', 
     'b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 
     true);
