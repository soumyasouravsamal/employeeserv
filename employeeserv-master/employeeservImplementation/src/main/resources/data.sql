drop table if exists EMPLOYEE;
drop table if exists ADDRESS;

CREATE TABLE ADDRESS(
    ADR_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    CITY VARCHAR(255) NOT NULL,
    COUNTRY VARCHAR(255) NOT NULL,
    LINE1 VARCHAR(255) NOT NULL,
    LINE2 VARCHAR(255),
    STATE VARCHAR(255) NOT NULL,
    ZIP_CODE INTEGER NOT NULL
);

CREATE TABLE EMPLOYEE(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    ADDRESS_ADR_ID INTEGER NOT NULL
);

INSERT INTO ADDRESS (LINE1,LINE2,CITY,STATE,COUNTRY,ZIP_CODE) VALUES ('LINE1','LINE2','CITY','STATE','COUNTRY',100);
INSERT INTO EMPLOYEE (FIRST_NAME,LAST_NAME,ADDRESS_ADR_ID) VALUES ('FIRST NAME','LAST NAME', 1);