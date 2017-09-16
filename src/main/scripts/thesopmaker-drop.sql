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