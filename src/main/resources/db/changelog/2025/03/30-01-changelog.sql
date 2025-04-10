-- liquibase formatted sql

-- changeset wladw:1743341912766-1
CREATE SEQUENCE IF NOT EXISTS permissions_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-2
CREATE SEQUENCE IF NOT EXISTS project_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-3
CREATE SEQUENCE IF NOT EXISTS role_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-4
CREATE SEQUENCE IF NOT EXISTS test_case_data_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-5
CREATE SEQUENCE IF NOT EXISTS test_case_expected_result_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-6
CREATE SEQUENCE IF NOT EXISTS test_case_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-7
CREATE SEQUENCE IF NOT EXISTS test_case_precondition_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-8
CREATE SEQUENCE IF NOT EXISTS test_case_step_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-9
CREATE SEQUENCE IF NOT EXISTS test_suite_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-10
CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 1;

-- changeset wladw:1743341912766-11
CREATE TABLE permissions
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    permission_type VARCHAR                                 NOT NULL,
    role_id         BIGINT,
    CONSTRAINT permissions_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-12
CREATE TABLE project
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title       VARCHAR,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR,
    CONSTRAINT project_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-13
CREATE TABLE role
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    role_name VARCHAR                                 NOT NULL,
    user_id   BIGINT,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-14
CREATE TABLE test_case
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title        VARCHAR,
    test_suit_id BIGINT,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT test_case_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-15
CREATE TABLE test_case_data
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    step         VARCHAR,
    test_case_id BIGINT,
    CONSTRAINT test_case_data_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-16
CREATE TABLE test_case_expected_result
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    step         VARCHAR,
    test_case_id BIGINT,
    CONSTRAINT test_case_expected_result_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-17
CREATE TABLE test_case_precondition
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    step         VARCHAR,
    test_case_id BIGINT,
    CONSTRAINT test_case_precondition_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-18
CREATE TABLE test_case_step
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    step         VARCHAR,
    test_case_id BIGINT,
    CONSTRAINT test_case_step_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-19
CREATE TABLE test_suite
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title       VARCHAR,
    project_id  BIGINT,
    description VARCHAR,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    tag         VARCHAR,
    CONSTRAINT test_suite_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-20
CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    login      VARCHAR                                 NOT NULL,
    password   VARCHAR,
    email      VARCHAR,
    is_enabled BOOLEAN,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

-- changeset wladw:1743341912766-21
ALTER TABLE permissions
    ADD CONSTRAINT permissions_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE NO ACTION;

-- changeset wladw:1743341912766-22
ALTER TABLE role
    ADD CONSTRAINT role_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

-- changeset wladw:1743341912766-23
ALTER TABLE test_case_data
    ADD CONSTRAINT test_case_data_test_case_id_fk FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE NO ACTION;

-- changeset wladw:1743341912766-24
ALTER TABLE test_case_expected_result
    ADD CONSTRAINT test_case_expected_result_test_case_id_fk FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE NO ACTION;

-- changeset wladw:1743341912766-25
ALTER TABLE test_case_precondition
    ADD CONSTRAINT test_case_precondition_test_case_id_fk FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE NO ACTION;

-- changeset wladw:1743341912766-26
ALTER TABLE test_case_step
    ADD CONSTRAINT test_case_step_test_case_id_fk FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE NO ACTION;

-- changeset wladw:1743341912766-27
ALTER TABLE test_case
    ADD CONSTRAINT test_case_test_suite_id_fk FOREIGN KEY (test_suit_id) REFERENCES test_suite (id) ON DELETE NO ACTION;

-- changeset wladw:1743341912766-28
ALTER TABLE test_suite
    ADD CONSTRAINT test_suite_project_id_fk FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE NO ACTION;

