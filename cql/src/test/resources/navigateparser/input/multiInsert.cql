from teststream
    insert into stream s1 select
    insert into stream s2 select a
    insert into stream s3 select id,name  where id > 10;

from (select * from teststream)
    insert into stream s1 select *
    insert into stream s2 select a
    insert into stream s3 select ,namewhere id > 10;

