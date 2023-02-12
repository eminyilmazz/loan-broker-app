drop table if exists test_schema.loans;

drop table if exists test_schema.customer;

create table test_schema.customer
(
    tckn           bigint not null
        primary key,
    birth_date     date   not null,
    email_address  varchar(255),
    first_name     varchar(255),
    last_name      varchar(255),
    monthly_salary double precision,
    phone_number   varchar(255)
);

create table test_schema.loans
(
    id              bigserial
        primary key,
    approval_date   timestamp(6),
    approval_status boolean not null,
    due_status      boolean not null,
    loan_amount     double precision,
    customer_tckn   bigint  not null
        constraint fk8015miq5kbxoleswj45toh5v6
            references test_schema.customer
);

INSERT INTO test_schema.customer (tckn,birth_date,first_name,last_name,phone_number,email_address,monthly_salary)
VALUES
    (10000000850,'1997-06-20','Emi','Bradford','4683423427','b-emi@outlook.net','6974'),
    (10000000950,'1993-04-14','Allen','Carlson','4051433878','carlson_allen3730@yahoo.com','8687'),
    (10000000010,'1999-10-30','Colby','Rose','2623458683','colbyrose4723@icloud.net','5904'),
    (10000000810,'1995-06-22','Vivien','Munoz','4145187362','vmunoz1016@outlook.net','4335'),
    (10000000050,'1993-10-19','Eaton','Phillips','2462487285','e-phillips@outlook.net','5956');

INSERT INTO test_schema.loans (id, approval_date, approval_status, due_status, loan_amount, customer_tckn)
VALUES (1, '2021-03-08 21:38:39.161', 'true', 'true',20000, 10000000850),
       (2, '2022-01-16 22:33:52.164', 'false', 'false',0, 10000000850),
       (3, '2022-02-12 20:52:08.164', 'true', 'false',10000, 10000000850);

INSERT INTO test_schema.loans (id, approval_date, approval_status, due_status, loan_amount, customer_tckn)
VALUES (4, '2022-12-20 16:24:16.167', 'false', 'false', 0, 10000000950),
       (5, '2021-06-09 12:32:03.162', 'true', 'false',27000, 10000000950),
       (6, '2023-01-29 20:37:00.167', 'true', 'true',32500, 10000000950);

INSERT INTO test_schema.loans (id, approval_date, approval_status, due_status, loan_amount, customer_tckn)
VALUES (7, '2022-03-23 18:45:09.164', 'false', 'false',0, 10000000050),
       (8, '2021-07-20 00:16:36.162', 'false', 'false', 0, 10000000050);

INSERT INTO test_schema.loans (id, approval_date, approval_status, due_status, loan_amount, customer_tckn)
VALUES (9, '2022-12-30 23:31:11.167', 'true', 'true', 10000, 10000000810);

INSERT INTO test_schema.loans (id, approval_date, approval_status, due_status, loan_amount, customer_tckn)
VALUES (10, '2021-10-13 10:44:42.163', 'true', 'true', 10000, 10000000010),
       (11, '2022-11-03 15:26:32.166', 'false', 'false', 0, 10000000010);

SELECT setval('test_schema.loans_id_seq', 12, true);
