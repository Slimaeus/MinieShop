create table categories (id integer not null auto_increment, created_at date, updated_at date, description varchar(255), name varchar(255), status smallint, title varchar(255), primary key (id)) engine=InnoDB;
create table comments (id varchar(255) not null, created_at date, updated_at date, content varchar(255), rate integer not null, product_id integer not null, user_id bigint not null, primary key (id)) engine=InnoDB;
create table images (id varchar(255) not null, created_at date, updated_at date, link varchar(255), status smallint, title varchar(255), product_id integer not null, primary key (id)) engine=InnoDB;
create table loyalty_cards (id bigint not null auto_increment, created_at date, updated_at date, point integer not null, status smallint, primary key (id)) engine=InnoDB;
create table order_details (order_id varchar(255) not null, product_id integer not null, quantity integer not null, total_price float(53) not null, primary key (order_id, product_id)) engine=InnoDB;
create table orders (id varchar(255) not null, created_at date, updated_at date, address varchar(255), customer_name varchar(255), discount_price float(53) not null, gender smallint, note varchar(255), payment_method smallint, payment_status smallint, phone_number varchar(255), shipping_cost float(53) not null, status smallint, total_bill float(53) not null, total_price float(53) not null, promotion_id bigint, user_id bigint, primary key (id)) engine=InnoDB;
create table products (id integer not null auto_increment, created_at date, updated_at date, description varchar(255), name varchar(255), price float(53) not null, status smallint, title varchar(255), category_id integer not null, primary key (id)) engine=InnoDB;
create table promotion_details (promotion_id bigint not null, user_id bigint not null, quantity integer not null, remain integer not null, primary key (promotion_id, user_id)) engine=InnoDB;
create table promotions (id bigint not null auto_increment, created_at date, updated_at date, code varchar(255), ended_at date, quantity integer not null, remain integer not null, started_at date, status smallint, title varchar(255), type smallint, value float(53) not null, primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, email varchar(50), full_name varchar(255), gender smallint, password varchar(250), phone_number varchar(10), reset_password_token varchar(255), role smallint, user_name varchar(50), loyal_card_id bigint, primary key (id)) engine=InnoDB;
alter table categories add constraint UKt8o6pivur7nn124jehx7cygw5 unique (name);
alter table products add constraint UKo61fmio5yukmmiqgnxf8pnavn unique (name);
alter table promotions add constraint UKjdho73ymbyu46p2hh562dk4kk unique (code);
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users add constraint UK_k8d0f2n7n88w1a16yhua64onx unique (user_name);
alter table comments add constraint FK6uv0qku8gsu6x1r2jkrtqwjtn foreign key (product_id) references products (id);
alter table comments add constraint FK8omq0tc18jd43bu5tjh6jvraq foreign key (user_id) references users (id);
alter table images add constraint FKghwsjbjo7mg3iufxruvq6iu3q foreign key (product_id) references products (id);
alter table order_details add constraint FKjyu2qbqt8gnvno9oe9j2s2ldk foreign key (order_id) references orders (id);
alter table order_details add constraint FK4q98utpd73imf4yhttm3w0eax foreign key (product_id) references products (id);
alter table orders add constraint FK42bki7v5u9s62olp5is82sd74 foreign key (promotion_id) references promotions (id);
alter table orders add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users (id);
alter table products add constraint FKog2rp4qthbtt2lfyhfo32lsw9 foreign key (category_id) references categories (id);
alter table promotion_details add constraint FKc2jsk6dw6cmn4g7h47gyky3k foreign key (promotion_id) references promotions (id);
alter table promotion_details add constraint FKgswisgpruxbhv8qbo6c24sdfs foreign key (user_id) references users (id);
alter table users add constraint FKrn48mhh4voih0v1ey6w7emcss foreign key (loyal_card_id) references loyalty_cards (id);
