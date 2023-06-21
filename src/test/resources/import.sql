CREATE TABLE book
(
    "id"          serial NOT NULL,
    name        varchar(50) NOT NULL,
    author      varchar(50) NOT NULL,
    description varchar(50) NOT NULL,
    price       real NOT NULL,
    genre       int NOT NULL,
    pic_path    varchar(50) NOT NULL,
    CONSTRAINT PK_38 PRIMARY KEY ( "id" )
);

CREATE TABLE genre
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" )
);

CREATE TABLE book_genre
(
    "id"       serial NOT NULL,
    genre_id int NOT NULL,
    book_id  int NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_10 FOREIGN KEY ( genre_id ) REFERENCES genre ( "id" ),
    CONSTRAINT FK_11 FOREIGN KEY ( book_id ) REFERENCES book ( "id" )
);

CREATE INDEX FK_2 ON book_genre
(
 genre_id
);

CREATE INDEX FK_3 ON book_genre
(
 book_id
);

CREATE TABLE person
(
    "id"         serial NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name  varchar(50) NOT NULL,
    email      varchar(50) NOT NULL,
    mob_num    varchar(50) NOT NULL,
    CONSTRAINT PK_6 PRIMARY KEY ( "id" )
);

CREATE TABLE person_cred
(
    "id"        serial NOT NULL,
    login     varchar(50) NOT NULL,
    person_id int NOT NULL,
    password  varchar(50) NOT NULL,
    locked    boolean NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_1 FOREIGN KEY ( person_id ) REFERENCES person ( "id" )
);

CREATE INDEX FK_2 ON person_cred
(
 person_id
);

CREATE TABLE role
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" )
);

CREATE TABLE person_role
(
    "id"        serial NOT NULL,
    person_id int NOT NULL,
    role_id   int NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_6_3 FOREIGN KEY ( person_id ) REFERENCES person ( "id" ),
    CONSTRAINT FK_7 FOREIGN KEY ( role_id ) REFERENCES role ( "id" )
);

CREATE INDEX FK_2 ON person_role
(
 person_id
);

CREATE INDEX FK_3 ON person_role
(
 role_id
);

CREATE TABLE "orders"
(
    "id"        serial NOT NULL,
    "date"      date NOT NULL,
    person_id int NOT NULL,
    price     int NOT NULL,
    CONSTRAINT PK_67 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_6 FOREIGN KEY ( person_id ) REFERENCES person ( "id" )
);

CREATE INDEX FK_3 ON "orders"
(
 person_id
);

CREATE TABLE status
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" )
);

CREATE TABLE order_status
(
    "id"        serial NOT NULL,
    order_id  int NOT NULL,
    status_id int NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_8 FOREIGN KEY ( status_id ) REFERENCES status ( "id" ),
    CONSTRAINT FK_9 FOREIGN KEY ( order_id ) REFERENCES "orders" ( "id" )
);

CREATE INDEX FK_2 ON order_status
(
 status_id
);

CREATE INDEX FK_3 ON order_status
(
 order_id
);

CREATE TABLE order_book
(
    "id"        serial NOT NULL,
    quanntity int8range NOT NULL,
    book_id   int NOT NULL,
    order_id  int NOT NULL,
    price     real NOT NULL,
    CONSTRAINT PK_75 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_6_1 FOREIGN KEY ( order_id ) REFERENCES "orders" ( "id" ),
    CONSTRAINT FK_6_2 FOREIGN KEY ( book_id ) REFERENCES book ( "id" )
);

CREATE INDEX FK_4 ON order_book
(
 order_id
);

CREATE INDEX FK_4_1 ON order_book
(
 book_id
);

CREATE TABLE verification_token
(
    "id"          serial NOT NULL,
    "token"       varchar(50) NOT NULL,
    person_id   int NOT NULL,
    expiry_date date NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_5 FOREIGN KEY ( person_id ) REFERENCES person ( "id" )
);

CREATE INDEX FK_2 ON verification_token
(
 person_id
);CREATE TABLE book
  (
      "id"          serial NOT NULL,
      name        varchar(50) NOT NULL,
      author      varchar(50) NOT NULL,
      description varchar(50) NOT NULL,
      price       real NOT NULL,
      genre       int NOT NULL,
      pic_path    varchar(50) NOT NULL,
      CONSTRAINT PK_38 PRIMARY KEY ( "id" )
  );

CREATE TABLE genre
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" )
);

CREATE TABLE book_genre
(
    "id"       serial NOT NULL,
    genre_id int NOT NULL,
    book_id  int NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_10 FOREIGN KEY ( genre_id ) REFERENCES genre ( "id" ),
    CONSTRAINT FK_11 FOREIGN KEY ( book_id ) REFERENCES book ( "id" )
);

CREATE INDEX FK_2 ON book_genre
(
 genre_id
);

CREATE INDEX FK_3 ON book_genre
(
 book_id
);

CREATE TABLE person
(
    "id"         serial NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name  varchar(50) NOT NULL,
    email      varchar(50) NOT NULL,
    mob_num    varchar(50) NOT NULL,
    CONSTRAINT PK_6 PRIMARY KEY ( "id" )
);

CREATE TABLE person_cred
(
    "id"        serial NOT NULL,
    login     varchar(50) NOT NULL,
    person_id int NOT NULL,
    password  varchar(50) NOT NULL,
    locked    boolean NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_1 FOREIGN KEY ( person_id ) REFERENCES person ( "id" )
);

CREATE INDEX FK_2 ON person_cred
(
 person_id
);

CREATE TABLE role
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" )
);

CREATE TABLE person_role
(
    "id"        serial NOT NULL,
    person_id int NOT NULL,
    role_id   int NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_6_3 FOREIGN KEY ( person_id ) REFERENCES person ( "id" ),
    CONSTRAINT FK_7 FOREIGN KEY ( role_id ) REFERENCES role ( "id" )
);

CREATE INDEX FK_2 ON person_role
(
 person_id
);

CREATE INDEX FK_3 ON person_role
(
 role_id
);

CREATE TABLE "orders"
(
    "id"        serial NOT NULL,
    "date"      date NOT NULL,
    person_id int NOT NULL,
    price     int NOT NULL,
    CONSTRAINT PK_67 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_6 FOREIGN KEY ( person_id ) REFERENCES person ( "id" )
);

CREATE INDEX FK_3 ON "orders"
(
 person_id
);

CREATE TABLE status
(
    "id"   serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" )
);

CREATE TABLE order_status
(
    "id"        serial NOT NULL,
    order_id  int NOT NULL,
    status_id int NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_8 FOREIGN KEY ( status_id ) REFERENCES status ( "id" ),
    CONSTRAINT FK_9 FOREIGN KEY ( order_id ) REFERENCES "orders" ( "id" )
);

CREATE INDEX FK_2 ON order_status
(
 status_id
);

CREATE INDEX FK_3 ON order_status
(
 order_id
);

CREATE TABLE order_book
(
    "id"        serial NOT NULL,
    quanntity int8range NOT NULL,
    book_id   int NOT NULL,
    order_id  int NOT NULL,
    price     real NOT NULL,
    CONSTRAINT PK_75 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_6_1 FOREIGN KEY ( order_id ) REFERENCES "orders" ( "id" ),
    CONSTRAINT FK_6_2 FOREIGN KEY ( book_id ) REFERENCES book ( "id" )
);

CREATE INDEX FK_4 ON order_book
(
 order_id
);

CREATE INDEX FK_4_1 ON order_book
(
 book_id
);

CREATE TABLE verification_token
(
    "id"          serial NOT NULL,
    "token"       varchar(50) NOT NULL,
    person_id   int NOT NULL,
    expiry_date date NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_5 FOREIGN KEY ( person_id ) REFERENCES person ( "id" )
);

CREATE INDEX FK_2 ON verification_token
(
 person_id
);