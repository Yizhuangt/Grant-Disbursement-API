insert into house_hold (id, house_hold_type) values (1,'Landed');
insert into house_hold (id, house_hold_type) values (2,'HDB');
insert into house_hold (id, house_hold_type) values (3,'Condominium');
insert into house_hold (id, house_hold_type) values (4,'Landed');


-- less than 16 (T) + less than 150,000 (T) + age above 50 (T) --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((10*12))), 'M', 'single', 'Jack', 'Student', '', 201, 1);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (40000, add_months(trunc(sysdate),-((50*12))), 'M', 'married', 'David', 'Employed', 'Amanda', 202, 1);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (40000, add_months(trunc(sysdate),-((60*12))), 'F', 'married', 'Amanda', 'Employed', 'David', 203, 1);


-- less than 16 (T) + less than 150,000 (F) + Husband Wife (F) --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((10*12))), 'M', 'single', 'Jack', 'Student', '', 204, 2);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (60000, add_months(trunc(sysdate),-((50*12))), 'M', 'married', 'David', 'Employed', 'June', 205, 2);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((60*12))), 'F', 'married', 'Ivy', 'Employed', 'Jill', 206, 2);


-- less than 4 (T) + less than 150,000 (T) --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((4*12))), 'M', 'single', 'Jack', 'Student', '', 207, 3);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, add_months(trunc(sysdate),-((30*12))), 'M', 'married', 'David', 'Unemployed', 'Amanda', 208, 3);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (80000, add_months(trunc(sysdate),-((31*12))), 'F', 'married', 'Amanda', 'Employed', 'David', 209, 3);


-- Family Togetherness --
-- 2 x Husband & Wife (T) + children less than 18 (F) --
insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((32*12))), 'M', 'married', 'Jack', 'Employed', 'Hazel', 210, 4);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((33*12))), 'F', 'married', 'Hazel', 'Employed', 'Jack', 211, 4);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((27*12))), 'M', 'married', 'David', 'Employed', 'Amanda', 212, 4);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (50000, add_months(trunc(sysdate),-((29*12))), 'F', 'married', 'Amanda', 'Employed', 'David', 213, 4);

