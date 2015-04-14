CREATE TABLE users
(
  id bigint NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  group_id bigint,
  CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE groups
(
  id bigint NOT NULL,
  name character varying(255) NOT NULL,
  CONSTRAINT groups_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE users_seq;
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_seq');

CREATE SEQUENCE groups_seq;
ALTER TABLE groups ALTER COLUMN id SET DEFAULT nextval('groups_seq');

ALTER TABLE users ADD COLUMN manager_id bigint;