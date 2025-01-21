package com.sist.dao;
import java.util.*;
import java.util.List;
import java.sql.*;
import java.awt.*;
import com.sist.vo.*;

public class ZipcodeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static ZipcodeDAO dao;
	private final String URL = "jdbc:oracle:thin:@211.238.142.124:1521:XE";
	
	public ZipcodeDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static ZipcodeDAO newInstance() {
		if(dao ==null)
			dao=new ZipcodeDAO();
		return dao;
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL,"hr_1","happy");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void disConnection() {
		try {
			if(conn!=null) conn.close();
			if(ps!=null) ps.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/*
ZIPCODE	VARCHAR2(7 BYTE)
SIDO	VARCHAR2(30 BYTE)
GUGUN	VARCHAR2(50 BYTE)
DONG	VARCHAR2(100 BYTE)
BUNJI	VARCHAR2(100 BYTE)
	 */
	public List<ZipcodeVO> zipcodeFindData(ZipcodeVO vo) {
		List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		try {
			getConnection();
			String sql= "SELECT zipcode,sido,gugun,dong,NVL(bunji,'') "
					+ "FROM zipcode "
					+ "WHERE REGEXP_LIKE(sido,?) "
					+ "AND REGEXP_LIKE(gugun,?) "
					+ "AND REGEXP_LIKE(dong,?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getSido());
			ps.setString(2,vo.getGugun());
			ps.setString(3, vo.getDong());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ZipcodeVO zvo = new ZipcodeVO();
				zvo.setZipcode(rs.getString(1));
				zvo.setSido(rs.getString(2));
				zvo.setGugun(rs.getString(3));
				zvo.setDong(rs.getString(4));
				zvo.setBunji(rs.getString(5));
				list.add(zvo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			disConnection();
		}
		
		return list;
	}
}
