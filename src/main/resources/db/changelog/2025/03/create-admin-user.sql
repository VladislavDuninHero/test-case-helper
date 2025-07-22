-- liquibase formatted sql

-- changeset author:create_admin_user
-- comment: Create admin user

INSERT INTO users (login, password, email, is_enabled, created_at, updated_at)
VALUES (
           'admin',
           '$2a$12$c2mb2lNufGKiL07aJeEPXO.LaKwcDBLnASkCUkBYdTtQndNTMGHKm',
           'admin@softgamings.com',
           TRUE,
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP
       );

INSERT INTO role (user_id, role_name)
VALUES (
           (SELECT id FROM users WHERE login = 'admin'),
           'LEAD_QA'
       );

-- changeset author:add_permissions_to_lead_qa dependsOn:create_lead_qa_role
-- comment: Добавление permissions для роли LEAD_QA
INSERT INTO permissions (permission_type, role_id)
VALUES
    ('CREATE_TEST_CASES', (SELECT id FROM role WHERE user_id = '1')),
    ('READ_TEST_CASES', (SELECT id FROM role WHERE user_id = '1')),
    ('UPDATE_TEST_CASES', (SELECT id FROM role WHERE user_id = '1')),
    ('DELETE_TEST_CASES', (SELECT id FROM role WHERE user_id = '1')),
    ('CREATE_TEST_SUITE', (SELECT id FROM role WHERE user_id = '1')),
    ('READ_TEST_SUITE', (SELECT id FROM role WHERE user_id = '1')),
    ('UPDATE_TEST_SUITE', (SELECT id FROM role WHERE user_id = '1')),
    ('DELETE_TEST_SUITE', (SELECT id FROM role WHERE user_id = '1')),
    ('CREATE_PROJECT', (SELECT id FROM role WHERE user_id = '1')),
    ('READ_PROJECT', (SELECT id FROM role WHERE user_id = '1')),
    ('UPDATE_PROJECT', (SELECT id FROM role WHERE user_id = '1')),
    ('DELETE_PROJECT', (SELECT id FROM role WHERE user_id = '1')),
    ('DELETE_USER', (SELECT id FROM role WHERE user_id = '1'));