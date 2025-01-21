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
		
		
	} 
	

}