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
implements ActionListener,Runnable, MouseListener
{
	//네트워크 통신
	Socket s;
	OutputStream out;
	BufferedReader in;
	
	MenuForm mf = new MenuForm(); // 포함 클래스 => 있는 그대로 사용
	ControlPanel cp = new ControlPanel();
	Login login = new Login();
	Register register = new Register();
	CardLayout card = new CardLayout();
	
	// 배치
	
	int selectRow=-1; // 테이블에서 선택이 안된 상태 
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
		login.b3.addActionListener(this);
		
		//회원가입
		register.b3.addActionListener(this);
		
		mf.b6.addActionListener(this); // 채팅 
		mf.b1.addActionListener(this); // 홈 
		mf.b2.addActionListener(this); // 맛집
		mf.b3.addActionListener(this); // 검색
		
		// Chat => Socket 
		cp.cp.tf.addActionListener(this);
		cp.cp.table.addMouseListener(this);
		cp.cp.b2.addActionListener(this);// 정보보기
		cp.cp.b1.addActionListener(this);// 쪽지보내기
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try
				{
					out.write((Function.EXIT+"|\n").getBytes());
				}catch(Exception ex) {}
			}
			
		});
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
		}
		new ClientMainFrame();
	}
	//서버에서 응답 받기 => 쓰레드
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			while(true)
			{
				String msg=in.readLine();
				// 서버에서 보낸값을 받는다 
				StringTokenizer st=
						new StringTokenizer(msg,"|");
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
					  String id=st.nextToken();
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
				  case Function.MYEXIT:
				  {
					  dispose();
					  System.exit(0);
				  }
				  break;
				  // 남아 있는 사람 처리 
				  case Function.EXIT:
				  {
					  String yid=st.nextToken();
					  for(int i=0;i<cp.cp.model.getRowCount();i++)
					  {
						  String id=cp.cp.model.getValueAt(i, 0).toString();
						  if(yid.equals(id))
						  {
							  cp.cp.model.removeRow(i);
							  break;
						  }
					  }
				  }
				  break;
				}
			}
		}catch(Exception ex) {}
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
		//회원가입으로 이동
		else if(e.getSource()==login.b3)
		{
			login.setVisible(false);
			register.setVisible(true);
		}
		else if(e.getSource()==register.b3)
		{
			login.setVisible(true);
			register.setVisible(false);
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

		else if(e.getSource()==cp.cp.b2)
		{
			if(selectRow==-1)
			{
				JOptionPane.showMessageDialog(this, 
						"정보볼 대상을 선택하세요");
				return;
			}
			
			String id=cp.cp.model.getValueAt(selectRow, 0)
					  .toString();
			
			MemberVO vo=mDao.memberInfo(id);
			
			String info="이름:"+vo.getName()+"\n"
					   +"성별:"+vo.getSex()+"\n"
					   +"이메일:"+vo.getEmail()+"\n"
					   +"생년월일:"+vo.getBirthday().toString()+"\n"
					   +"주소:"+vo.getAddr1()+" "+vo.getAddr2() +"\n"
					   +"등록일:"+vo.getRegdate().toString();
			JOptionPane.showMessageDialog(this, info);
			
		}
		else if(e.getSource()==cp.cp.b1)
		{
			if(selectRow==-1)
			{
				JOptionPane.showMessageDialog(this, 
						"귓속말 보낼 대상을 선택하세요");
				return;
			}
			
			String id=cp.cp.model.getValueAt(selectRow, 0)
					  .toString();
			
			MemberVO vo=mDao.memberInfo(id);
			
			String info="이름:"+vo.getName()+"\n"
					   +"성별:"+vo.getSex()+"\n"
					   +"이메일:"+vo.getEmail()+"\n"
					   +"생년월일:"+vo.getBirthday().toString()+"\n"
					   +"주소:"+vo.getAddr1()+" "+vo.getAddr2() +"\n"
					   +"등록일:"+vo.getRegdate().toString();
			JOptionPane.showMessageDialog(this, info);
			JOptionPane.showMessageDialog(this, "쪽지");
			
		}
		// chat처리 
		else if(e.getSource()==cp.cp.tf)
		{
			String msg=cp.cp.tf.getText();
			if(msg.trim().length()<1)
			{
				cp.cp.tf.requestFocus();
				return;
			}
			
			try
			{
			  out.write((Function.WAITCHAT+"|"
					  +msg+"\n").getBytes());	
			}catch(Exception ex){}
			
			cp.cp.tf.setText("");
		}
		else if(e.getSource()==mf.b6)
		{
			cp.card.show(cp, "CHAT");
		}
		else if(e.getSource()==mf.b1)
		{
			cp.card.show(cp, "HOME");
		}
		else if(e.getSource()==mf.b2)
		{
			cp.card.show(cp, "PROD");
		}
		else if(e.getSource()==mf.b3)
		{
			cp.card.show(cp, "FIND");
		}
	}
	public void connection(MemberVO vo)
	{
		try
		{
			s=new Socket("localhost",3355);
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
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==cp.cp.table) 
		{
			selectRow=cp.cp.table.getSelectedRow();
			String myId=getTitle();
			String id=cp.cp.model.getValueAt(selectRow, 0).toString();
			if(myId.equals(id))
			{
				cp.cp.b1.setEnabled(false);
				cp.cp.b2.setEnabled(false);
			}
			else {
				cp.cp.b1.setEnabled(true);
				cp.cp.b2.setEnabled(true);
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