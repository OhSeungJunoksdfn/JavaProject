package com.sist.vo;

import java.util.Date;

import lombok.Data;

/*
ID	VARCHAR2(20 BYTE)
PWD	VARCHAR2(10 BYTE)
NAME	VARCHAR2(51 BYTE)
EMAIL	VARCHAR2(100 BYTE)
SEX	CHAR(6 BYTE)
BIRTHDAY	DATE
POST	VARCHAR2(7 BYTE)
ADDR1	VARCHAR2(200 BYTE)
ADDR2	VARCHAR2(100 BYTE)
PHONE	VARCHAR2(13 BYTE)
REGDATE	DATE
CONTENT	CLOB
 */
@Data
public class MemberVO {
	private String id,pwd,name,email,sex,post,addr1,addr2,phone,content,msg,birthday;
	private Date regdate;
	
	
}
