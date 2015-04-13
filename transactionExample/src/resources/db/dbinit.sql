CREATE TABLE users
(
  id bigint NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  group_id bigint,
  CONSTRAINT users_pkey PRIMARY KEY (id),
);

CREATE TABLE groups
(
  id bigint NOT NULL,
  name character varying(255) NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (id),
);