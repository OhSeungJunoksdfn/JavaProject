package com.sist.vo;

import lombok.Data;

/*
ZIPCODE	VARCHAR2(7 BYTE)
SIDO	VARCHAR2(30 BYTE)
GUGUN	VARCHAR2(50 BYTE)
DONG	VARCHAR2(100 BYTE)
BUNJI	VARCHAR2(100 BYTE)
 */
@Data
public class ZipcodeVO {
	private String zipcode,sido,gugun,dong,bunji;
}
