package com.sist.dao;
//로그인 /회원가입 / 회원 탈퇴 / 회원수정 => 오라클 연결
import java.sql.*;
import com.sist.vo.*;
public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static MemberDAO dao;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
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
			conn = DriverManager.getConnection(URL,"hr","happy");
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
	//2. 회원가입
	//3. 회원수정
	//4. 회원탈퇴
}
