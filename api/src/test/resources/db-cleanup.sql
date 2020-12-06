begin;
set constraints all deferred;
commit;

begin;

truncate table drug cascade;
alter sequence drug_sequence restart with 1;

commit;

begin;
set constraints all immediate;
commit;
