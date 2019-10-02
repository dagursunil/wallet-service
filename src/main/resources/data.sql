 
  
-- creating dummy account 
insert into account(id,player_name,sex,date_created,version) values (1000,'Sunil','M',now(),0);
insert into account(id,player_name,sex,date_created,version) values (2000,'John','M',now(),0);
insert into account(id,player_name,sex,date_created,version) values (3000,'Jenny','F',now(),0);

-- performing wallet update for account with id 1000
insert into wallet(id,account_id,amount,transcation_type,transaction_date,transaction_id,version) values (500,1000,4000,'credit',now(),1001,0);
insert into wallet(id,account_id,amount,transcation_type,transaction_date,transaction_id,version) values (501,1000,-300,'debit',now(),1002,0);
insert into wallet(id,account_id,amount,transcation_type,transaction_date,transaction_id,version) values (502,1000,6500,'credit',now(),1003,0);


