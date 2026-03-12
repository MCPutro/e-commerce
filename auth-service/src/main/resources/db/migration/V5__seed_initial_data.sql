-- V5__seed_initial_data.sql
-- Insert default permissions

-- Auth permissions
INSERT INTO permissions (id, name, resource, action, description) VALUES
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a01', 'auth:login', 'auth', 'login', 'Login to the system'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a02', 'auth:register', 'auth', 'register', 'Register new account'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a03', 'auth:manage-profile', 'auth', 'manage-profile', 'Manage own profile');

-- User permissions
INSERT INTO permissions (id, name, resource, action, description) VALUES
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a04', 'user:read', 'user', 'read', 'View user details'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a05', 'user:write', 'user', 'write', 'Create and update users'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a06', 'user:delete', 'user', 'delete', 'Delete users');

-- Product permissions
INSERT INTO permissions (id, name, resource, action, description) VALUES
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a07', 'product:read', 'product', 'read', 'View products'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a08', 'product:write', 'product', 'write', 'Create and update products'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a09', 'product:delete', 'product', 'delete', 'Delete products');

-- Cart permissions
INSERT INTO permissions (id, name, resource, action, description) VALUES
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0a', 'cart:manage', 'cart', 'manage', 'Manage own cart');

-- Order permissions
INSERT INTO permissions (id, name, resource, action, description) VALUES
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0b', 'order:create', 'order', 'create', 'Create orders'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0c', 'order:read', 'order', 'read', 'View own orders'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0d', 'order:read_all', 'order', 'read_all', 'View all orders'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0e', 'order:write', 'order', 'write', 'Update order status');

-- Payment permissions
INSERT INTO permissions (id, name, resource, action, description) VALUES
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0f', 'payment:process', 'payment', 'process', 'Process payments'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a10', 'payment:refund', 'payment', 'refund', 'Refund transactions');

-- Role permissions
INSERT INTO permissions (id, name, resource, action, description) VALUES
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a11', 'role:read', 'role', 'read', 'View roles'),
    ('a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a12', 'role:write', 'role', 'write', 'Create and update roles');
