insert into house_hold (id, house_hold_type) values (1,'Landed');
insert into house_hold (id, house_hold_type) values (2,'HDB');
insert into house_hold (id, house_hold_type) values (3,'Condominium');
insert into house_hold (id, house_hold_type) values (4,'Landed');
insert into house_hold (id, house_hold_type) values (5,'HDB');


-- less than 16 (F) + less than 150,000 (T) + age above 50 (T) LANDED --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((16*12))), 'M', 'single', 'Jack', 'Student', '', 201, 1);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (40000, add_months(trunc(sysdate),-((50*12))), 'M', 'married', 'David', 'Employed', 'Amanda', 202, 1);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (40000, add_months(trunc(sysdate),-((60*12))), 'F', 'single', 'Alexis', 'Employed', '', 203, 1);


-- less than 16 + 18 (T) + more than 150,000/100000 +  age above 50 (T) HDB --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((15*12))), 'M', 'single', 'William', 'Student', '', 204, 2);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (45000, add_months(trunc(sysdate),-((50*12))), 'M', 'married', 'Ben', 'Employed', 'June', 205, 2);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (45000, add_months(trunc(sysdate),-((60*12))), 'F', 'married', 'Ivy', 'Employed', 'Jill', 206, 2);


-- less than 5 (T) + more than 100,000 (T) Condo + Husband Wife + --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((4*12))), 'M', 'single', 'Jack', 'Student', '', 207, 3);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((30*12))), 'M', 'married', 'Ethan', 'Unemployed', 'Evelyn', 208, 3);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (60000, add_months(trunc(sysdate),-((31*12))), 'F', 'married', 'Evelyn', 'Employed', 'Ethan', 209, 3);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (20000, add_months(trunc(sysdate),-((20*12))), 'F', 'single', 'Paul', 'Employed', '', 210, 3);



-- less than 4 (T) + less than 150,000 (T) --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((32*12))), 'M', 'married', 'Tom', 'Employed', 'Hazel', 211, 4);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((33*12))), 'F', 'married', 'Hazel', 'Employed', 'Tom', 212, 4);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((27*12))), 'M', 'married', 'Charles', 'Employed', 'Grace', 213, 4);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((29*12))), 'F', 'married', 'Grace', 'Employed', 'Charles', 214, 4);


-- #5 (FALSE)
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((32*12))), 'M', 'single', 'Tommy', 'Employed', 'Hazel', 215, 5);



