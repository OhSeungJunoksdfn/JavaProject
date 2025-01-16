package com.sist.main;

//AcrylLookAndFeel 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.sist.vo.*;
import com.sist.commons.Function;
import com.sist.dao.*;
//네트워크 통신
import java.io.*;
import java.util.*;
import java.net.*;
//                                                   상속 => 재사용 => 변경
/*
 * 사용자 동작 => 로그인 , 채팅 보내기
 * 메소드 => 무조건 종료 => 다른 메소드가 호출
 * -------------------------------------------
 * 서버에서 보내는 값 : 자동화 처리
 * 								---------
 * 
 */
public class ClientMainFrame  extends JFrame
implements ActionListener,Runnable
{
	//네트워크 통신
	Socket s;
	OutputStream out;
	BufferedReader in;
	
	MenuForm mf = new MenuForm(); // 포함 클래스 => 있는 그대로 사용
	ControlPanel cp = new ControlPanel();
	Login login = new Login();
	CardLayout card = new CardLayout();
	// 배치
	//데이터베이스 가져오기
	MemberDAO mDao=MemberDAO.newInstance();
	public ClientMainFrame() {
		setLayout(null); // 사용자 정의 => 직업 배치
		mf.setBounds(10,15,1250,50);
		add(mf);
		cp.setBounds(10,95,1250,650);
		add(cp);


		setSize(1280, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(Color.white);
		//setVisible(true);		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//등록
		// 로그인
		login.b1.addActionListener(this);
		login.b2.addActionListener(this);
		login.pf.addActionListener(this);
		
		mf.b1.addActionListener(this);
		mf.b6.addActionListener(this);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
		}
		new ClientMainFrame();
	}
	//서버에서 응답 받기 => 쓰레드
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true)
			{
				String msg=in.readLine();
				StringTokenizer st= new StringTokenizer(msg,"|");
				int protocol=Integer.parseInt(st.nextToken());
				switch(protocol)
				{
				case Function.LOGIN:
				{
					String[] data= {
							st.nextToken(),
							st.nextToken(),
							st.nextToken()
					};
					cp.cp.model.addRow(data);
				}
				break;
				case Function.MYLOG:
				{
					String id = st.nextToken();
					setTitle(id);
					login.setVisible(false);
					setVisible(true);
				}
				break;
				case Function.WAITCHAT:
				{
					cp.cp.ta.append(st.nextToken()+"\n");
				}
				break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//서버에 요청 => 로그인  / 채팅 보내기
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==login.b2)
		{
			dispose();//윈도우 메모리 해제
			System.exit(0); //프로그램 종료
			
		}
		else if(e.getSource()==login.b1 || e.getSource()==login.pf)
		{
			// 유효성 검사
			String id=login.tf.getText();
			if(id.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this, "ID 입력해주세요");
				login.tf.requestFocus();
				return;
			}
			String pwd=String.valueOf(login.pf.getPassword());
			if(pwd.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this, "PASSWORD 입력해주세요");
				login.pf.requestFocus();
				return;
			}
			
			MemberVO vo = mDao.isLogin(id, pwd);
			if(vo.getMsg().equals("NOID"))
			{
				JOptionPane.showMessageDialog(this, "ID가 존재하지 않습니다.");
				login.tf.setText("");
				login.pf.setText("");
				login.tf.requestFocus();
			}
			else if(vo.getMsg().equals("NOPWD")) {
				JOptionPane.showMessageDialog(this, "PASSWORD가 틀렸습니다.");
			login.tf.setText("");
			login.pf.setText("");
			login.pf.requestFocus();
				
			}
			else {
				connection(vo);
			}
		}
		else if(e.getSource()==mf.b6)
		{
			cp.card.show(cp,"CHAT");
		}
		else if(e.getSource()==mf.b1)
		{
			cp.card.show(cp, "HOME");
		}
	}
	public void connection(MemberVO vo)
	{
		try
		{
			s=new Socket("192.168.10.110",5555);
			//서버연결
			//서버로 전송
			out=s.getOutputStream();
			//서버에서 값 받기
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			//서버로 로그인 요청
			out.write((Function.LOGIN+"|"+vo.getId()+"|"+vo.getName()+"|"+vo.getSex()+"\n").getBytes());
			//=> 반드시 => \n을 전송해야된다.
		}catch(Exception e) {}
		//서버로부터 값을 받아서 출력
		new Thread(this).start(); //run() 메소드 호출
	}
}
