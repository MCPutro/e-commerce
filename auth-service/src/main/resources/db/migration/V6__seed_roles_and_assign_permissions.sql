-- V6__seed_roles_and_assign_permissions.sql
-- Insert default roles

-- Customer role
INSERT INTO roles (id, name, description) VALUES
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'customer', 'Regular customer with basic access');

-- Assign customer permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a01'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a02'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a03'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a07'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0a'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0b'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0c'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b01', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0f');

-- Staff role
INSERT INTO roles (id, name, description) VALUES
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'staff', 'Staff member with operational access');

-- Assign staff permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a01'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a03'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a07'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a08'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0c'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0d'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0e'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b02', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a11');

-- Owner role
INSERT INTO roles (id, name, description) VALUES
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'owner', 'Owner with full access');

-- Assign owner permissions (all permissions)
INSERT INTO role_permissions (role_id, permission_id) VALUES
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a01'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a02'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a03'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a04'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a05'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a06'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a07'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a08'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a09'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0a'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0b'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0c'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0d'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0e'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a0f'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a10'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a11'),
    ('b1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5b03', 'a1ee16c0-7c0a-4f59-8c8a-1e1b5c5e5a12');
