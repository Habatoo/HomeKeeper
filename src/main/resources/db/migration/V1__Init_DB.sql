drop table if exists hibernate_sequence;
drop table if exists roles;
drop table if exists user_roles;
drop table if exists users;

create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );

create table roles (id integer not null, role_name varchar(20), primary key (id)) engine=InnoDB;

create table user_roles (user_id bigint(50) not null, role_id integer not null, primary key (user_id, role_id)) engine=InnoDB;
create table users (id bigint(50) not null, creation_date datetime, first_name varchar(255), last_name varchar(255), password varchar(255), user_email varchar(255), primary key (id)) engine=InnoDB;
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id);
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id);