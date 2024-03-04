# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table categoria (
  id                            bigserial not null,
  nome                          varchar(60) not null,
  constraint pk_categoria primary key (id)
);

create table contador (
  id                            bigserial not null,
  csv                           integer,
  excel                         integer,
  pdf                           integer,
  biogasdata                    integer,
  notatecnica                   integer,
  constraint pk_contador primary key (id)
);

create table escala (
  id                            bigserial not null,
  valor                         bigint,
  intervalo                     bigint,
  legenda                       varchar(255),
  porte                         bigint,
  constraint pk_escala primary key (id)
);

create table estado (
  id                            bigserial not null,
  ibge_code                     bigint,
  nome                          varchar(255),
  sigla                         varchar(255),
  regiao                        varchar(255),
  constraint pk_estado primary key (id)
);

create table log (
  id                            bigserial not null,
  mensagem                      varchar(500) not null,
  navegador                     varchar(100),
  versao                        varchar(100),
  so                            varchar(100),
  data_cadastro                 timestamp not null,
  constraint pk_log primary key (id)
);

create table municipio (
  id                            bigserial not null,
  nome                          varchar(255),
  uf                            varchar(255),
  estado_id                     bigint,
  constraint pk_municipio primary key (id)
);

create table situacao (
  id                            bigserial not null,
  nome                          varchar(60) not null,
  constraint pk_situacao primary key (id)
);

create table unidade (
  id                            bigserial not null,
  codigo                        integer not null,
  ano_de_inicio_operacao        varchar(255) not null,
  biogas_para_energia_termica   boolean not null,
  biogas_para_energia_eletrica  boolean not null,
  biogas_para_energia_mecanica  boolean not null,
  biogas_para_biometano         boolean not null,
  valor_estimado                boolean not null,
  latitude                      float not null,
  longitude                     float not null,
  producao_substrato_dia        float not null,
  producao_substrato_ano        float not null,
  producao_biogas_dia           float not null,
  producao_biogas_ano           float not null,
  situacao_id                   bigint,
  escala_id                     bigint,
  categoria_id                  bigint,
  municipio_id                  bigint,
  status                        varchar(9) not null,
  data_cadastro                 timestamp not null,
  data_alteracao                timestamp not null,
  constraint ck_unidade_status check (status in ('APROVADO','AVALIAR','REPROVADO')),
  constraint uq_unidade_codigo unique (codigo),
  constraint pk_unidade primary key (id)
);

create table usuario (
  id                            bigserial not null,
  nome                          varchar(80) not null,
  email                         varchar(60) not null,
  senha                         varchar(255) not null,
  papel                         varchar(13),
  status                        boolean not null,
  data_cadastro                 date,
  data_alteracao                date,
  ultimo_acesso                 timestamp not null,
  constraint ck_usuario_papel check (papel in ('USUARIO','SUPERVISOR','GERENTE','ADMINISTRADOR')),
  constraint uq_usuario_email unique (email),
  constraint pk_usuario primary key (id)
);

alter table municipio add constraint fk_municipio_estado_id foreign key (estado_id) references estado (id) on delete restrict on update restrict;
create index ix_municipio_estado_id on municipio (estado_id);

alter table unidade add constraint fk_unidade_situacao_id foreign key (situacao_id) references situacao (id) on delete restrict on update restrict;
create index ix_unidade_situacao_id on unidade (situacao_id);

alter table unidade add constraint fk_unidade_escala_id foreign key (escala_id) references escala (id) on delete restrict on update restrict;
create index ix_unidade_escala_id on unidade (escala_id);

alter table unidade add constraint fk_unidade_categoria_id foreign key (categoria_id) references categoria (id) on delete restrict on update restrict;
create index ix_unidade_categoria_id on unidade (categoria_id);

alter table unidade add constraint fk_unidade_municipio_id foreign key (municipio_id) references municipio (id) on delete restrict on update restrict;
create index ix_unidade_municipio_id on unidade (municipio_id);


# --- !Downs

alter table municipio drop constraint if exists fk_municipio_estado_id;
drop index if exists ix_municipio_estado_id;

alter table unidade drop constraint if exists fk_unidade_situacao_id;
drop index if exists ix_unidade_situacao_id;

alter table unidade drop constraint if exists fk_unidade_escala_id;
drop index if exists ix_unidade_escala_id;

alter table unidade drop constraint if exists fk_unidade_categoria_id;
drop index if exists ix_unidade_categoria_id;

alter table unidade drop constraint if exists fk_unidade_municipio_id;
drop index if exists ix_unidade_municipio_id;

drop table if exists categoria cascade;

drop table if exists contador cascade;

drop table if exists escala cascade;

drop table if exists estado cascade;

drop table if exists log cascade;

drop table if exists municipio cascade;

drop table if exists situacao cascade;

drop table if exists unidade cascade;

drop table if exists usuario cascade;

