    create sequence hibernate_sequence minvalue 1000;

	create table departments (
        id int8 not null,
        department_name varchar(255),
        primary key (id)
    );

    create table options (
        id int8 not null,
        value varchar(255),
        question_id int8,
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
        active boolean not null,
        emailId varchar(255),
        firstname varchar(255),
        lastname varchar(255),
        primary key (id)
    );

    alter table options 
        add constraint FK5bmv46so2y5igt9o9n9w4fh6y 
        foreign key (question_id) 
        references questions;

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
    
    insert into departments( id, department_name ) values ( 1000, 'Computer Science' );
    insert into departments( id, department_name ) values ( 1001, 'Mechanical Engineering' );
    insert into departments( id, department_name ) values ( 1002, 'Electronics and Telecommunication' );
    insert into departments( id, department_name ) values ( 1003, 'Electrical Engineering' );
    insert into departments( id, department_name ) values ( 1004, 'Master in Business Applications' );
    
    insert into questions(id, department_id, page, question) values (1000, null, 'Department', 'Select your department: ');
    insert into questions(id, department_id, page, question) values (1001, 1000, 'Overview', 'What is your full name ?');
    insert into questions(id, department_id, page, question) values (1002, 1000, 'Overview', 'Which is your undergraduate school ?');
    insert into questions(id, department_id, page, question) values (1003, 1000, 'Overview', 'What are your favorite subjects ?');
    
    insert into questions(id, department_id, page, question) values (1004, 1000, 'Educational', 'Which programming languages did you learn ?');
    insert into questions(id, department_id, page, question) values (1005, 1000, 'Educational', 'Any knowledge on Artificial Intelligence ?');
    

    insert into options(id, question_id, value) values (1000, 1001, 'Hey there! My first name is ${value} and I am good at what I do.');
    insert into options(id, question_id, value) values (1001, 1001, 'My Name is ${value}, it means something deep! People call me Nik.');
    insert into options(id, question_id, value) values (1002, 1001, 'Hey this is ${value}, I am going to tell something about me really soon.');
    insert into options(id, question_id, value) values (1003, 1002, 'My Undergraduate school was ${value}.');
    insert into options(id, question_id, value) values (1004, 1002, '${value} was where I completed my undergraduate degree.');
    insert into options(id, question_id, value) values (1005, 1002, 'I graduated with a degree from ${value} for undergraduate degree.');
    insert into options(id, question_id, value) values (1006, 1003, '${value} where my favorite classes.');
    insert into options(id, question_id, value) values (1007, 1003, 'Favorite subjects list would include ${value}.');
    insert into options(id, question_id, value) values (1008, 1003, 'To name a few classes really interested me are ${value}.');
    
    insert into options(id, question_id, value) values (1009, 1004, 'I know ${value}.');
    insert into options(id, question_id, value) values (1010, 1004, 'I know ${value}.');
    insert into options(id, question_id, value) values (1011, 1004, 'I know ${value}.');
    
    insert into options(id, question_id, value) values (1012, 1005, 'I know ${value}.');
    insert into options(id, question_id, value) values (1013, 1005, '${value} is cool.');
    
    insert into users(id, active, emailId, firstname, lastname) values(1000, true, 'nikhilpawar43@ymail.com', 'Nikhil', 'Pawar');
    insert into users(id, active, emailId, firstname, lastname) values(1001, true, 'krishna.bang@accenture.com', 'Krishna', 'Bang');
    