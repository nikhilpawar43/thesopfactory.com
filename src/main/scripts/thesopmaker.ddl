
    alter table authorities 
        drop constraint FKk91upmbueyim93v469wj7b2qh;

    alter table questions 
        drop constraint FK124be202h7u31e4s1pl9cdqnd;

    alter table sop_details 
        drop constraint FK554vmoqd5rxqv6sea58dbd72g;

    alter table sop_details 
        drop constraint FK71a6xb1eide24x585okcdnunm;

    alter table sops 
        drop constraint FKa0ensy5brxrah0drs1qdyexgo;

    alter table sops 
        drop constraint FK72qu9y6x1p1e9c1mvr5qdm5ii;

    drop table if exists authorities cascade;

    drop table if exists departments cascade;

    drop table if exists options cascade;

    drop table if exists questions cascade;

    drop table if exists sop_details cascade;

    drop table if exists sops cascade;

    drop table if exists users cascade;

    drop sequence hibernate_sequence;

    create sequence hibernate_sequence start 1 increment 1;

    create table authorities (
        user_id int8 not null,
        role varchar(255)
    );

    create table departments (
        id int8 not null,
        department_name varchar(255),
        primary key (id)
    );

    create table options (
        id int8 not null,
        question_id int8,
        value varchar(255),
        primary key (id)
    );

    create table questions (
        id int8 not null,
        page varchar(255),
        question varchar(255),
        department_id int8,
        primary key (id)
    );

    create table sop_details (
        id int8 not null,
        userInput varchar(255),
        userOptionId int8 not null,
        question_id int8,
        sop_id int8,
        primary key (id)
    );

    create table sops (
        id int8 not null,
        creation_date timestamp,
        department_id int8,
        user_id int8,
        primary key (id)
    );

    create table users (
        id int8 not null,
        email_id varchar(255),
        enabled boolean not null,
        firstname varchar(255),
        lastname varchar(255),
        password varchar(255),
        username varchar(255) not null,
        primary key (id)
    );

    alter table authorities 
        add constraint FKk91upmbueyim93v469wj7b2qh 
        foreign key (user_id) 
        references users;

    alter table questions 
        add constraint FK124be202h7u31e4s1pl9cdqnd 
        foreign key (department_id) 
        references departments;

    alter table sop_details 
        add constraint FK554vmoqd5rxqv6sea58dbd72g 
        foreign key (question_id) 
        references questions;

    alter table sop_details 
        add constraint FK71a6xb1eide24x585okcdnunm 
        foreign key (sop_id) 
        references sops;

    alter table sops 
        add constraint FKa0ensy5brxrah0drs1qdyexgo 
        foreign key (department_id) 
        references departments;

    alter table sops 
        add constraint FK72qu9y6x1p1e9c1mvr5qdm5ii 
        foreign key (user_id) 
        references users;

    alter table users 
        add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
