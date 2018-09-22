/*Table structure for table account */
DROP TABLE account;

CREATE TABLE account (
  accountid int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  name varchar(255) NOT NULL,
  balance int NOT NULL,
  pin int NOT NULL,
  PRIMARY KEY (accountid)
);
/*Data for the table account */
INSERT INTO account ("NAME", BALANCE, PIN)  VALUES ('pizanut',59500,1234),('nana',20500,4256),('nunoy',40000,8409),('natee',86500,8975);



/*Table structure for table history */
DROP TABLE history;

CREATE TABLE history (
  historyid int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  accountid int NOT NULL,
  method varchar(45) NOT NULL,
  amount int NOT NULL,
  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  balance int NOT NULL,
  PRIMARY KEY (historyid),
  CONSTRAINT accountid_history FOREIGN KEY (accountid) REFERENCES account (accountid) ON DELETE NO ACTION ON UPDATE NO ACTION
) ;

/*Data for the table history */
INSERT INTO history (ACCOUNTID, "METHOD", AMOUNT, "TIME", BALANCE)  VALUES (1,'deposit',50000,'2018-07-15 01:00:01',60000),(2,'withdraw',1000,'2018-08-01 01:50:30',20000),(1,'withdraw',500,'2018-08-07 02:51:41',59500),(1,'deposit',2000,'2018-08-08 02:33:33',61500),(2,'deposit',1000,'2018-08-15 06:51:21',21000),(2,'withdraw',500,'2018-08-16 00:00:10',20500),(4,'deposit',2000,'2018-08-16 04:30:15',80000),(3,'deposit',1000,'2018-08-18 00:50:19',40000),(3,'withdraw',500,'2018-09-01 02:50:50',39500),(4,'deposit',1000,'2018-09-02 04:00:10',81000),(1,'withdraw',1000,'2018-09-02 13:50:30',60500),(3,'deposit',500,'2018-09-05 05:45:15',40000),(4,'deposit',500,'2018-09-10 06:50:20',81500),(1,'withdraw',1000,'2018-09-11 07:30:11',59500),(4,'deposit',5000,'2018-09-11 09:58:33',86500);