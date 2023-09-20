drop table if exists build_type;

create table build_type
(
    build_type_no bigint        auto_increment    primary key,
    name          varchar(10)   not null          comment '건물 유형 이름 ex) 아파트, 빌라, 오피스텔 등'
);

drop table if exists category;

create table category
(
    category_no bigint          auto_increment    primary key,
    name        varchar(50)     not null          comment '카테고리 이름 ex) 아파트, 시공 후기, 베란다 확장 ...'
);

drop table if exists bookmark;

create table bookmark
(
    bookmark_no   bigint                  auto_increment    primary key,
    target_no     bigint                                    not null,
    user_no       bigint                                    not null,
    type          enum ('NORMAL', 'VOTE') default 'NORMAL'  not null,
    created_date  datetime                default sysdate() not null,
    modified_date datetime                default sysdate() not null,
    constraint fk_user_to_bookmark
        foreign key (user_no) references user (user_no) on delete cascade
);

drop table if exists company;

create table company
(
    company_no      bigint          auto_increment    primary key,
    user_no         bigint                            not null,
    registration_no varchar(255)                      not null,
    company_name    varchar(255)                      not null,
    contact_number  varchar(255)                      not null,
    rating          varchar(255)                      null,
    status          enum ('Y', 'N') default 'Y'       not null,
    approval        enum ('Y', 'N') default 'N'       not null,
    created_date    datetime        default sysdate() not null,
    modified_date   datetime        default sysdate() not null,
    constraint fk_user_to_company
        foreign key (user_no) references user (user_no) on delete cascade
);

drop table if exists comment_like;

create table comment_like
(
    like_no       bigint    auto_increment   primary key,
    user_no       bigint                     not null,
    comment_no    bigint                     not null,
    created_date  datetime default sysdate() not null,
    modified_date datetime default sysdate() not null,
    constraint fk_comment_to_commentlike
        foreign key (comment_no) references comment (comment_no) on delete cascade,
    constraint fk_user_to_commentlike
        foreign key (user_no) references user (user_no) on delete cascade
);

drop table if exists comment;

create table comment
(
    comment_no        bigint                      auto_increment    primary key,
    post_no           bigint                                        not null,
    parent_comment_no bigint                                        null,
    user_no           bigint                                        not null,
    content           varchar(1000)                                 not null,
    status            enum ('VISIBLE', 'DELETED') default 'VISIBLE' not null,
    created_date      datetime                    default sysdate() not null,
    modified_date     datetime                    default sysdate() not null,
    constraint fk_comment_to_comment
        foreign key (parent_comment_no) references comment (comment_no) on delete cascade,
    constraint fk_post_to_comment
        foreign key (post_no) references post (post_no) on delete cascade,
    constraint fk_user_to_comment
        foreign key (user_no) references user (user_no) on delete cascade
);

drop table if exists post_like;

create table post_like
(
    like_no       bigint auto_increment primary key,
    user_no       bigint                        not null,
    post_no       bigint                        not null,
    created_date  datetime(6) default sysdate() not null,
    modified_date datetime(6) default sysdate() not null,
    constraint fk_post_to_postlike
        foreign key (post_no) references post (post_no) on delete cascade,
    constraint fk_user_to_postlike
        foreign key (user_no) references user (user_no) on delete cascade
);

drop table if exists post;

create table post
(
    post_no       bigint                      auto_increment    primary key,
    user_no       bigint                                        not null,
    title         varchar(255)                                  not null,
    content       varchar(255)                                  not null,
    type          enum ('NORMAL', 'VOTE')     default 'NORMAL'  not null,
    status        enum ('DELETED', 'VISIBLE') default 'VISIBLE' not null,
    view_count    int                         default 0         not null,
    created_date  datetime(6)                 default sysdate() not null,
    modified_date datetime(6)                 default sysdate() not null,
    constraint fk_user_to_post
        foreign key (user_no) references user (user_no) on delete cascade
);

drop table if exists user;

create table user
(
    user_no       bigint      auto_increment    primary key,
    profile_no    bigint                                                    null,
    nickname      varchar(100)                                              not null,
    email         varchar(100)                                              not null,
    password      varchar(100)                                              null,
    role          enum ('ADMIN', 'ANONYMOUS', 'EMPLOYEE', 'SOCIAL', 'USER') not null,
    status        enum ('ACTIVATED', 'LEFT', 'NOT_AVAILABLE')               null,
    quit_date     datetime(6)                                               null,
    created_date  datetime(6) default sysdate()                             not null,
    modified_date datetime(6) default sysdate()                             not null,
    constraint user_uk01
        unique (email),
    constraint user_uk02
        unique (nickname),
    constraint fk_image_to_user
        foreign key (profile_no) references image (image_no) on delete cascade
);

drop table if exists image;

create table image
(
    image_no  bigint       auto_increment    primary key,
    image_url varchar(500) not null comment '이미지 url'
);
