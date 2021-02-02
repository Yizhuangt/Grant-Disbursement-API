insert into house_hold (id, house_hold_type) values (101,'Landed');

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (0, sysdate(), 'M', 'single', 'Jack', 'Student', '', 201, 101);

insert into family_member (annual_income, dob, gender, marital_status, name, occupation_type, spouse, id, house_hold_id) 
values (40000, sysdate(), 'M', 'married', 'David', 'Employed', 'Amanda', 202, 101);
