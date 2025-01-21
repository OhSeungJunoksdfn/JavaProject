package com.sist.main;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;

import com.sist.dao.*;
import com.sist.vo.*;
import javax.swing.*;
public class Register extends JFrame 
implements ActionListener,MouseListener
{
	String gul="맑은 고딕";
	JLabel register;
	JTextField id,pwd,cpwd,name,email,bir1,bir2,bir3,post,addr1,addr2,phone;
	JTextPane content;
	JLabel idLa,pwdLa,cpwdLa,nameLa,emailLa,sexLa,birthdayLa,postLa,addr1La,addr2La,phoneLa,contentLa;
	JButton b1,b2,b3;
	JRadioButton man,woman;
	ButtonGroup sex;
	Zipcode zipcode = new Zipcode();

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
		
		idLa.setBounds(20,179,560,20);
		id.setBounds(20,204,560,50);
		add(idLa);add(id);
		
		emailLa= new JLabel("이메일");
		emailLa.setFont(new Font(gul,Font.PLAIN,14));
		email = new JTextField();
		emailLa.setBounds(20,264,560,20);
		email.setBounds(20,289,560,50);
		add(emailLa);add(email);
		
		pwdLa= new JLabel("비밀번호");
		pwdLa.setFont(new Font(gul,Font.PLAIN,14));
		pwd = new JTextField();
		pwdLa.setBounds(20,344,275,20);
		pwd.setBounds(20,369,275,50);
		add(pwdLa);add(pwd);
		
		cpwdLa= new JLabel("비밀번호 확인");
		cpwdLa.setFont(new Font(gul,Font.PLAIN,14));
		cpwd = new JTextField();
		cpwdLa.setBounds(305,344,275,20);
		cpwd.setBounds(305,369,275,50);
		add(cpwdLa);add(cpwd);
		
		
		addr1La= new JLabel("주소");
		addr2La= new JLabel("상세 주소");
		b1 = new JButton("검색");
		addr1La.setFont(new Font(gul,Font.PLAIN,14));
		addr2La.setFont(new Font(gul,Font.PLAIN,14));
		addr1 = new JTextField();
		addr2 = new JTextField();
		addr1La.setBounds(20,424,100,20);
		addr2La.setBounds(430,424,100,20);
		b1.setBounds(20,449,100,50);
		addr1.setBounds(130,449,290,50);
		addr2.setBounds(430,449,150,50);
		add(addr1La);add(addr2La);add(b1);add(addr1);add(addr2);
		
		
		
		phoneLa= new JLabel("전화번호");
		phoneLa.setFont(new Font(gul,Font.PLAIN,14));
		phone = new JTextField();
		phoneLa.setBounds(20,504,560,20);
		phone.setBounds(20,529,560,50);
		add(phoneLa);add(phone);
		
		birthdayLa= new JLabel("생일");
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
		
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2)
		{
			String idData= id.getText();
			if(idData.trim().length()<1)
			{
				id.requestFocus();
				return;
			}
			String nameData= name.getText();
			if(nameData.trim().length()<1)
			{
				name.requestFocus();
				return;
			}
			String pwdData= pwd.getText();
			if(pwdData.trim().length()<1)
			{
				pwd.requestFocus();
				return;
			}
			String cpwdData= cpwd.getText();
			if(cpwdData.trim().length()<1)
			{
				cpwd.requestFocus();
				return;
			}
			if(!pwdData.equals(cpwd.getText()))
			{
				JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
				pwd.requestFocus();
				return;
			}
			String emailData= email.getText();
				if(emailData.trim().length()>=1 && !emailData.contains("@"))
				{
					JOptionPane.showMessageDialog(this, "이메일을 잘못 입력하셨습니다.(@를 입력해주세요)");
					email.requestFocus();
					return;
				}
			
			String phoneData= phone.getText();
			if(phoneData.trim().length()<1)
			{
				phone.requestFocus();
				return;
			}
			String birLength= bir1.getText() + bir2.getText() + bir3.getText();
			String birData= (bir1.getText() + "/" + bir2.getText() + "/" + bir3.getText());
			if(birLength.trim().length()<1)
			{
				bir1.requestFocus();
				return;
			}
			String addr1Data = addr1.getText();
			String postData = addr1Data.substring(0,addr1Data.indexOf(")"));
			String addr2Data = addr2.getText();
			if(addr1Data.trim().length()<1 || addr2Data.trim().length()<1)
			{
				addr2.requestFocus();
				return;
			}
			String sexData;
			if(man.isSelected())
			{
				sexData=man.getText();
			}
			else
			{
				sexData=woman.getText();
			}
			
			MemberVO vo = new MemberVO();
			vo.setId(idData);
			vo.setName(nameData);
			vo.setPwd(pwdData);
			vo.setPhone(phoneData);
			vo.setAddr1(addr1Data);
			vo.setAddr2(addr2Data);
			vo.setEmail(emailData);
			vo.setSex(sexData);
			vo.setPost(postData);
			vo.setBirthday(birData);
			vo.setContent("");
			
			MemberDAO dao = MemberDAO.newInstance();
			boolean bCheck= dao.createMember(vo);
			
			
		}
		//주소찾기
		else if(e.getSource()==b1)
		{
			zipcode.dongTf.setText("");
			zipcode.sidoTf.setText("");
			zipcode.gugunTf.setText("");
			zipcode.setVisible(true);
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==zipcode.table)
		{
			if(e.getClickCount()==2)
			{
				int row=zipcode.table.getSelectedRow();
				String post=
						zipcode.model.getValueAt(row, 0).toString();
				String address= zipcode.model.getValueAt(row, 1).toString();
				

			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
