-- liquibase formatted sql

-- changeset wladw:1748985182041-1
CREATE TABLE permissions
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    permission_type VARCHAR(255) NULL,
    role_id         BIGINT NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-2
CREATE TABLE project
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    created_at    datetime NOT NULL,
    updated_at    datetime NULL,
    CONSTRAINT pk_project PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-3
CREATE TABLE `role`
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(255) NULL,
    user_id   BIGINT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-4
CREATE TABLE test_case
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    title        VARCHAR(255) NULL,
    test_suit_id BIGINT NULL,
    created_at   datetime NOT NULL,
    updated_at   datetime NULL,
    CONSTRAINT pk_test_case PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-5
CREATE TABLE test_case_data
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255) NULL,
    test_case_id BIGINT NULL,
    CONSTRAINT pk_test_case_data PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-6
CREATE TABLE test_case_expected_result
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255) NULL,
    test_case_id BIGINT NULL,
    CONSTRAINT pk_test_case_expected_result PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-7
CREATE TABLE test_case_precondition
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255) NULL,
    test_case_id BIGINT NULL,
    CONSTRAINT pk_test_case_precondition PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-8
CREATE TABLE test_case_run_result
(
    id                        BIGINT AUTO_INCREMENT NOT NULL,
    test_suite_run_session_id BIGINT NULL,
    test_case_id              BIGINT NULL,
    actual_result             VARCHAR(255) NULL,
    status                    VARCHAR(255) NULL,
    comment                   VARCHAR(255) NULL,
    created_at                datetime NOT NULL,
    updated_at                datetime NULL,
    CONSTRAINT pk_test_case_run_result PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-9
CREATE TABLE test_case_step
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    step         VARCHAR(255) NULL,
    test_case_id BIGINT NULL,
    CONSTRAINT pk_test_case_step PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-10
CREATE TABLE test_suite
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    tag           VARCHAR(255) NULL,
    project_id    BIGINT NULL,
    created_at    datetime NOT NULL,
    updated_at    datetime NULL,
    CONSTRAINT pk_test_suite PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-11
CREATE TABLE test_suite_run_session
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    test_suite_id BIGINT NULL,
    start_time    datetime NULL,
    end_time      datetime NULL,
    user_id       BIGINT NULL,
    environment   VARCHAR(255) NULL,
    status        VARCHAR(255) NULL,
    created_at    datetime NOT NULL,
    updated_at    datetime NULL,
    CONSTRAINT pk_test_suite_run_session PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-12
CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    login      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    email      VARCHAR(255) NULL,
    is_enabled BOOLEAN NULL,
    created_at datetime NOT NULL,
    updated_at datetime NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset wladw:1748985182041-13
ALTER TABLE permissions
    ADD CONSTRAINT FK_PERMISSIONS_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

-- changeset wladw:1748985182041-14
ALTER TABLE `role`
    ADD CONSTRAINT FK_ROLE_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset wladw:1748985182041-15
ALTER TABLE test_case_data
    ADD CONSTRAINT FK_TEST_CASE_DATA_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-16
ALTER TABLE test_case_expected_result
    ADD CONSTRAINT FK_TEST_CASE_EXPECTED_RESULT_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-17
ALTER TABLE test_case
    ADD CONSTRAINT FK_TEST_CASE_ON_TEST_SUIT FOREIGN KEY (test_suit_id) REFERENCES test_suite (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-18
ALTER TABLE test_case_precondition
    ADD CONSTRAINT FK_TEST_CASE_PRECONDITION_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-19
ALTER TABLE test_case_run_result
    ADD CONSTRAINT FK_TEST_CASE_RUN_RESULT_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-20
ALTER TABLE test_case_run_result
    ADD CONSTRAINT FK_TEST_CASE_RUN_RESULT_ON_TEST_SUITE_RUN_SESSION FOREIGN KEY (test_suite_run_session_id) REFERENCES test_suite_run_session (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-21
ALTER TABLE test_case_step
    ADD CONSTRAINT FK_TEST_CASE_STEP_ON_TEST_CASE FOREIGN KEY (test_case_id) REFERENCES test_case (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-22
ALTER TABLE test_suite
    ADD CONSTRAINT FK_TEST_SUITE_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-23
ALTER TABLE test_suite_run_session
    ADD CONSTRAINT FK_TEST_SUITE_RUN_SESSION_ON_TEST_SUITE FOREIGN KEY (test_suite_id) REFERENCES test_suite (id) ON DELETE CASCADE;

-- changeset wladw:1748985182041-24
ALTER TABLE test_suite_run_session
    ADD CONSTRAINT FK_TEST_SUITE_RUN_SESSION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;