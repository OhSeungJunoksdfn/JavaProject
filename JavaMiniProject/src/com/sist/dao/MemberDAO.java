package com.sist.dao;
//로그인 /회원가입 / 회원 탈퇴 / 회원수정 => 오라클 연결
import java.sql.*;
import java.util.Date;
import com.sist.vo.*;
public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static MemberDAO dao;
	private final String URL = "jdbc:oracle:thin:@211.238.142.124:1521:XE";
	
	public MemberDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
	}
	//WEB => JDBC => DBCP
	//DBCP => 미리 연결하는 방식을 사용하는 방식으로 작동된다 => 재사용 (반환) => WEB에서 일반화
	//JDBC => 연결 /닫기 => 연결에 시간소모가 있다
	//2. Connection을 한개만 생성
	public static MemberDAO newInstance()
	{
		if(dao==null)
			dao=new MemberDAO();
		return dao;
	}
	
	//3. 연결
	public void getConnection()
	{
		try {
			conn = DriverManager.getConnection(URL,"hr_1","happy");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//4. 닫기
	public void disConnection()
	{
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//기능
	//1.로그인
	public MemberVO isLogin(String id,String pwd)
	{
		MemberVO vo = new MemberVO();
		try {
			// 1. 연결
			getConnection();
			//2. sql문장 전송; => id 존재여부 확인
			String sql = "SELECT COUNT(*) FROM member WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			
			if(count==0)
			{
				vo.setMsg("NOID");
			}
			else
			{
				//비밀번호 확인
				sql = "SELECT id , pwd, name,sex FROM member WHERE  id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				//결과값
				rs = ps.executeQuery();
				rs.next();
				vo.setId(rs.getString(1));
				vo.setName(rs.getString(3));
				vo.setSex(rs.getString(4));
				String db_pwd = rs.getString(2);
				//비밀번호 검사
				if(db_pwd.equals(pwd)) //로그인
				{
					vo.setMsg("OF");

				}
				else //비밀번호 틀림
				{
					vo.setMsg("NOPWD");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			disConnection();
		}
		return vo;
	}
	// 회원 정보 
   /*
    *   private String id,pwd,name,sex,email,address,msg;
        private Date regdate,birthday;
    */
   public MemberVO memberInfo(String id)
   {
	   MemberVO vo=new MemberVO();
	   try
	   {
		   getConnection();
		   String sql="SELECT name,sex,email,addr1,addr2,"
				     +"regdate,birthday "
				     +"FROM member "
				     +"WHERE id=?";
		   ps=conn.prepareStatement(sql);
		   ps.setString(1, id);
		   ResultSet rs=ps.executeQuery();
		   rs.next();
		   vo.setName(rs.getString(1));
		   vo.setSex(rs.getString(2));
		   vo.setEmail(rs.getString(3));
		   vo.setAddr1(rs.getString(4));
		   vo.setAddr2(rs.getString(5));
		   vo.setRegdate(rs.getDate(6));
		   vo.setBirthday(rs.getString(7));
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
	   return vo;
   }
	//2. 회원가입
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
   
   public boolean createMember(MemberVO vo) {
	   boolean bCheck = true;
	   try {
		getConnection();
		
		String sql = "SELECT COUNT(*) FROM member WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, vo.getId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		if(rs.getInt(1)==1) {
			bCheck=false;
			return bCheck;
		}
		rs.close();
		
		sql= "INSERT INTO member(id,pwd,name,email,sex,birthday,post,addr1,addr2,phone,regdate,content) "
				+ "VALUES(?,?,?,?,?,TO_CHAR(?,YY/MM/DD),?,?,?,?,SYSDATE,?)";
		ps=conn.prepareStatement(sql);
		ps.setString(1, vo.getId());
		ps.setString(2,vo.getPwd());
		ps.setString(3, vo.getName());
		ps.setString(4, vo.getEmail());
		ps.setString(5, vo.getSex());
		ps.setString(6, vo.getBirthday());
		ps.setString(7, vo.getPost());
		ps.setString(8, vo.getAddr1());
		ps.setString(9, vo.getAddr2());
		ps.setString(10, vo.getPhone());
		ps.setString(11, vo.getContent());
		ps.executeUpdate();

		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}finally {
		disConnection();
	}
	   return bCheck;
   }
   
	//3. 회원수정
	//4. 회원탈퇴
}
