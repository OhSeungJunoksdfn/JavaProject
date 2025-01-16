package com.sist.vo;

import lombok.Data;

/*
PRODUCT_NO	NUMBER
PRODUCT_NAME	VARCHAR2(300 BYTE)
PRODUCT_POSTER	VARCHAR2(300 BYTE)
PRICE	VARCHAR2(50 BYTE)
SALE	VARCHAR2(200 BYTE)
DELIVER	VARCHAR2(200 BYTE)
HIT	NUMBER
CATEGORY	VARCHAR2(30 BYTE)
 */
@Data
public class ProductVO {
	private String product_name,product_poster,price,sale,deliver,category;
	private int product_no,hit;
}
