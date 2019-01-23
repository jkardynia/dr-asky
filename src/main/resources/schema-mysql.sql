

    create table intent_air_quality (
       id integer not null,
        profile_id integer,
        primary key (id)
    ) engine=InnoDB;

    create table intent_air_quality_locations (
       intent_air_quality_id integer not null,
        locations_id integer not null
    ) engine=InnoDB;

    create table intent_find_bus (
       id integer not null,
        profile_id integer,
        primary key (id)
    ) engine=InnoDB;

    create table intent_find_bus_locations (
       intent_find_bus_id integer not null,
        locations_id integer not null
    ) engine=InnoDB;

    create table location (
       id integer not null,
        city varchar(255),
        country varchar(255),
        flat_number varchar(255),
        house_number varchar(255),
        lat varchar(255),
        lng varchar(255),
        name varchar(255),
        post_code varchar(255),
        street varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table profile (
       id integer not null,
        username varchar(255),
        home_location_id integer,
        timezone varchar(255),
        locale varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table bus_route (
       id integer not null,
        direction varchar(255),
        line_number varchar(255),
        stop_name varchar(255),
        from_id integer,
        to_id integer,
        primary key (id)
    ) engine=InnoDB;

    alter table intent_air_quality_locations
       add constraint UK_imv2i382pvbdbyx6rwh7cfrdl unique (locations_id);

    alter table intent_find_bus_locations
       add constraint UK_8vm6e5amn7viu91uswtnfqh7 unique (locations_id);

    alter table intent_air_quality 
       add constraint FKhpb94uwj6g9p5vo50ndeymqjv 
       foreign key (profile_id) 
       references profile (id);

    alter table intent_air_quality_locations
       add constraint FK78bua06jb65kkmi0adpw58hyi 
       foreign key (locations_id) 
       references location (id);

    alter table intent_air_quality_locations
       add constraint FK4sf7x6n47y06hok4rrj8gc54j 
       foreign key (intent_air_quality_id) 
       references intent_air_quality (id);

    alter table intent_find_bus 
       add constraint FK5v2eng5e7mo6e9hnwcuci1ko2 
       foreign key (profile_id) 
       references profile (id);

    alter table intent_find_bus_locations
       add constraint FK1gwa91a6ewd1t80mlx22dvmka 
       foreign key (locations_id) 
       references location (id);

    alter table intent_find_bus_locations
       add constraint FKlx8gducrhkt9t2mhnn9gu4l89 
       foreign key (intent_find_bus_id) 
       references intent_find_bus (id);

    alter table profile
       add constraint FKffq40r025795kpqto462bcx4l 
       foreign key (home_location_id)
       references location (id);

    alter table bus_route
           add constraint FKffq40r025795kpqto462zxc0l
           foreign key (from_id)
           references location (id);

    alter table bus_route
               add constraint FKffq40r025795kpqto462zxc1l
               foreign key (to_id)
               references location (id);
