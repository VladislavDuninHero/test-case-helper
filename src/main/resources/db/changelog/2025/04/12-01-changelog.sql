-- liquibase formatted sql

-- changeset wladw:1744405857862-1
CREATE TABLE permissions
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    permission_type VARCHAR(255),
    role_id         BIGINT,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-2
CREATE TABLE project
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(255),
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP,
    CONSTRAINT pk_project PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-3
CREATE TABLE role
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(255),
    user_id   BIGINT,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-4
CREATE TABLE test_case
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    title        VARCHAR(255),
    test_suit_id BIGINT,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP,
    CONSTRAINT pk_test_case PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-5
CREATE TABLE test_case_data
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255),
    test_case_id BIGINT,
    CONSTRAINT pk_test_case_data PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-6
CREATE TABLE test_case_expected_result
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255),
    test_case_id BIGINT,
    CONSTRAINT pk_test_case_expected_result PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-7
CREATE TABLE test_case_precondition
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255),
    test_case_id BIGINT,
    CONSTRAINT pk_test_case_precondition PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-8
CREATE TABLE test_case_step
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255),
    test_case_id BIGINT,
    CONSTRAINT pk_test_case_step PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-9
CREATE TABLE test_suite
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(255),
    tag         VARCHAR(255),
    project_id  BIGINT,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP,
    CONSTRAINT pk_test_suite PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-10
CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    login      VARCHAR(255),
    password   VARCHAR(255),
    email      VARCHAR(255),
    is_enabled BOOLEAN,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset wladw:1744405857862-11
ALTER TABLE permissions
    ADD CONSTRAINT FK_PERMISSIONS_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

-- changeset wladw:1744405857862-12
ALTER TABLE role
    ADD CONSTRAINT FK_ROLE_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset wladw:1744405857862-13
ALTER TABLE test_case_data
    ADD CONSTRAINT FK_TEST_CASE_DATA_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id);

-- changeset wladw:1744405857862-14
ALTER TABLE test_case_expected_result
    ADD CONSTRAINT FK_TEST_CASE_EXPECTED_RESULT_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id);

-- changeset wladw:1744405857862-15
ALTER TABLE test_case
    ADD CONSTRAINT FK_TEST_CASE_ON_TEST_SUIT FOREIGN KEY (test_suit_id) REFERENCES test_suite (id);

-- changeset wladw:1744405857862-16
ALTER TABLE test_case_precondition
    ADD CONSTRAINT FK_TEST_CASE_PRECONDITION_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id);

-- changeset wladw:1744405857862-17
ALTER TABLE test_case_step
    ADD CONSTRAINT FK_TEST_CASE_STEP_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id);

-- changeset wladw:1744405857862-18
ALTER TABLE test_suite
    ADD CONSTRAINT FK_TEST_SUITE_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);