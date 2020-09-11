-- auto-generated definition
create table collector
(
    id         int auto_increment
        primary key,
    id_device  int          null,
    created    datetime     null,
    groupName  varchar(20)  null,
    headerName varchar(100) null,
    value      double       null
);

create index created
    on collector (created, groupName, headerName);

create index id_device
    on collector (id_device, created, headerName);

create index id_device_2
    on collector (id_device);

