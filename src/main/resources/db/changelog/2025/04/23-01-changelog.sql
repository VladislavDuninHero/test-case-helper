-- liquibase formatted sql

-- changeset wladw:1753220908613-1
CREATE TABLE teams
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    team_name VARCHAR(255) NULL,
    created_at    datetime NOT NULL,
    updated_at    datetime NULL,
    CONSTRAINT pk_teams PRIMARY KEY (id)
);

-- changeset wladw:1753220908613-2
CREATE TABLE user_team
(
    user_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    CONSTRAINT pk_user_team PRIMARY KEY (user_id, team_id)
);

-- changeset wladw:1753220908613-3
ALTER TABLE project
    ADD team_id BIGINT NULL;

-- changeset wladw:1753220908613-4
ALTER TABLE project
    ADD CONSTRAINT FK_PROJECT_ON_TEAM FOREIGN KEY (team_id) REFERENCES teams (id);

-- changeset wladw:1753220908613-5
ALTER TABLE user_team
    ADD CONSTRAINT FK_USER_TEAM_ON_TEAM FOREIGN KEY (team_id) REFERENCES teams (id);

-- changeset wladw:1753220908613-6
ALTER TABLE user_team
    ADD CONSTRAINT FK_USER_TEAM_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

