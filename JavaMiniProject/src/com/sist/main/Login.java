package com.sist.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
	JLabel la1,la2;
	JTextField tf;
	JPasswordField pf;
	JButton b1,b2,b3;
	public Login()
	{
	   	la1=new JLabel("ID");
        la2=new JLabel("Password");
        tf=new JTextField();
        pf=new JPasswordField();
        b1=new JButton("로그인");
        b3=new JButton("회원가입");
        b2=new JButton("취소");
        
        // 배치 => 실행과 동시에 실행 명령 => 초기화 => 생성자
        setLayout(null);
        la1.setBounds(10, 15, 80, 30);
        tf.setBounds(95, 15, 200, 30);
        add(la1);add(tf);
        
        la2.setBounds(10, 50, 80, 30);
        pf.setBounds(95, 50, 200, 30);
        add(la2);add(pf);
        
        JPanel p=new JPanel();
        p.setOpaque(false);
        p.add(b1);
        p.add(b3);
        p.add(b2);
        p.setBounds(10, 90, 285, 35);
        add(p);
		
		setSize(330,180);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	
}