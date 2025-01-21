package com.sist.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
//화면 변경
public class ControlPanel  extends JPanel {
	HomePanel hp;
	ChatPanel cp;
	GenrePanel gp;
    FindPanel fp;
    DetailPanel dp;
    NewsPanel np;
    BoardList bList;
    BoardInsert bInsert;
    BoardDetail bDetail;
    BoardUpdate bUpdate;
    BoardReply bReply;
    BoardDelete bDelete;
	CardLayout card = new CardLayout();
	public ControlPanel() {
		setLayout(card);
		hp=new HomePanel(this);
		add("HOME",hp);
		cp = new ChatPanel(this);
		add("CHAT",cp);
		gp=new GenrePanel(this);
    	add("PROD",gp);
    	fp=new FindPanel(this);
    	add("FIND",fp);
    	dp=new DetailPanel(this);
    	add("DETAIL",dp);
    	np=new NewsPanel(this);
    	add("NP",np);
    	bList=new BoardList(this);
    	add("BLIST",bList);
    	bInsert=new BoardInsert(this);
    	add("BINSERT",bInsert);
    	bDetail=new BoardDetail(this);
    	add("BDETAIL",bDetail);
    	bUpdate=new BoardUpdate(this);
    	add("BUPDATE",bUpdate);
    	bReply=new BoardReply(this);
    	add("BREPLY",bReply);
    	bDelete=new BoardDelete(this); // => jsp(메소드) => URL주소
    	// delete.jsp?no=10&pwd=1234 => JSP에서는 메소드를 만들 수 없다 
    	add("BDELETE",bDelete);
	} 
	

}