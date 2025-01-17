package com.sist.main;
import java.awt.*;
import java.awt.event.*;
import com.sist.dao.*;
import com.sist.vo.*;
import javax.swing.*;
public class Register extends JFrame 
implements ActionListener
{
	String gul="맑은 고딕";
	JLabel register;
	JTextField id,pwd,cpwd,name,email,bir1,bir2,bir3,post,addr1,addr2,phone;
	JTextPane content;
	JLabel idLa,pwdLa,cpwdLa,nameLa,emailLa,sexLa,birthdayLa,postLa,addr1La,addr2La,phoneLa,contentLa;
	JButton b1,b2,b3;
	JRadioButton man,woman;
	ButtonGroup sex;

	public Register() {
		

		register = new JLabel("회원가입");
		register.setBounds(20,20,560,44);
		register.setFont(new Font(gul,Font.BOLD,36));
		add(register);
		
		nameLa= new JLabel("이름");
		nameLa.setFont(new Font(gul,Font.PLAIN,14));
		name = new JTextField();
		nameLa.setBounds(20,94,560,20);
		name.setBounds(20,119,560,50);
		add(nameLa);add(name);
		
		idLa= new JLabel("ID");
		idLa.setFont(new Font(gul,Font.PLAIN,14));
		id = new JTextField();
		b1 = new JButton("중복확인");
		idLa.setBounds(20,179,560,20);
		id.setBounds(20,204,400,50);
		b1.setBounds(440,204,140,50);
		add(idLa);add(id);add(b1);
		
		emailLa= new JLabel("이메일");
		emailLa.setFont(new Font(gul,Font.PLAIN,14));
		email = new JTextField();
		emailLa.setBounds(20,264,560,20);
		email.setBounds(20,289,560,50);
		add(emailLa);add(email);
		
		pwdLa= new JLabel("비밀번호");
		pwdLa.setFont(new Font(gul,Font.PLAIN,14));
		pwd = new JTextField();
		pwdLa.setBounds(20,344,560,20);
		pwd.setBounds(20,369,560,50);
		add(pwdLa);add(pwd);
		
		cpwdLa= new JLabel("비밀번호 확인");
		cpwdLa.setFont(new Font(gul,Font.PLAIN,14));
		cpwd = new JTextField();
		cpwdLa.setBounds(20,424,560,20);
		cpwd.setBounds(20,449,560,50);
		add(cpwdLa);add(cpwd);
		
		phoneLa= new JLabel("전화번호");
		phoneLa.setFont(new Font(gul,Font.PLAIN,14));
		phone = new JTextField();
		phoneLa.setBounds(20,504,560,20);
		phone.setBounds(20,529,560,50);
		add(phoneLa);add(phone);
		
		birthdayLa= new JLabel("전화번호");
		birthdayLa.setFont(new Font(gul,Font.PLAIN,14));
		bir1 = new JTextField();
		bir2 = new JTextField();
		bir3 = new JTextField();
		birthdayLa.setBounds(20,584,560,20);
		bir1.setBounds(20,609,150,50);
		bir2.setBounds(225,609,150,50);
		bir3.setBounds(430,609,150,50);
		add(birthdayLa);add(bir1);add(bir2);add(bir3);
		
		sexLa= new JLabel("성별");
		sexLa.setFont(new Font(gul,Font.PLAIN,14));
		man = new JRadioButton("남자");
		woman = new JRadioButton("여자");
		sex = new ButtonGroup();
		sex.add(man);
		sex.add(woman);
		sexLa.setBounds(20,664,560,20);
		man.setBounds(50,689,80,20);
		man.setSelected(true);
		woman.setBounds(130,689,80,20);
		add(sexLa); add(man);add(woman);
		
		
		b2 = new JButton("회원가입");
		b3 = new JButton("가입취소");
		b2.setBounds(140,759,150,50);
		b3.setBounds(310,759,150,50);
		add(b2);add(b3);
		
		
		setLayout(null);
		setSize(600,855);
		setLocationRelativeTo(null);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
