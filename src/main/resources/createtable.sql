#удалить таблицу
  drop table if exists sessions;

  #создать таблицу
  create table sessions (
    Timestamp varchar(32) not null,
    iduser varchar(300) not null,
    URL varchar(32) not null,
    Time varchar(32) not null,
    primary key (iduser)
  );
