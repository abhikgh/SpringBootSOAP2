DROP TABLE IF EXISTS EMPLOYEE;
  
CREATE TABLE EMPLOYEE (
    id INT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    email VARCHAR(250) DEFAULT NULL
);

INSERT INTO EMPLOYEE
    (id,first_name, last_name, email) 
VALUES
    (1,'Lokesh', 'Gupta', 'abc@gmail.com'),
    (2,'Deja', 'Vu', 'xyz@email.com'),
    (3,'Caption', 'America', 'cap@marvel.com');