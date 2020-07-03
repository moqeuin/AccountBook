CREATE TABLE MEMBER(
	ID VARCHAR2(30) PRIMARY KEY,
	PWD VARCHAR2(30) NOT NULL,
	NAME VARCHAR2(30) NOT NULL,
	EMAIL VARCHAR2(30) UNIQUE,
	AUTH NUMBER(1) 
);

SELECT * from MEMBER;

-- 특정 기간의 내용 출력

select M.NAME, B.WDATE, B.IO_KIND, B.AMOUNT, B.CONTENT
from member m, ACT_BOOK b
where m.id = b.id AND b.id = 'bbb' AND
	b.wdate >= TO_DATE('2020/03/05 ','YYYY/MM/DD') AND  b.wdate <= TO_DATE('2020/07/02 23:59:59','YYYY/MM/DD HH24:MI:SS');

-- 입력한 데이터가 content에 포함된 row데이터들을 출력

SELECT M.NAME, B.WDATE, B.IO_KIND, B.AMOUNT, B.CONTENT
FROM MEMBER m, ACT_BOOK b
WHERE m.id = b.id AND m.id = 'abc' AND INSTR(content,'식비',1,1) != 0;