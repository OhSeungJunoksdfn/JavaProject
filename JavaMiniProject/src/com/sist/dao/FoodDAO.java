package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class FoodDAO {

	private Connection conn;
	private PreparedStatement ps;
	private static FoodDAO dao;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public FoodDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
	}
	//WEB => JDBC => DBCP
	//DBCP => 미리 연결하는 방식을 사용하는 방식으로 작동된다 => 재사용 (반환) => WEB에서 일반화
	//JDBC => 연결 /닫기 => 연결에 시간소모가 있다
	//2. Connection을 한개만 생성
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
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
	//1. 전체 목록 출력 => 인라인뷰
	public List<FoodVO> FoodListData(int page)
	{
		List<FoodVO> list = new ArrayList<FoodVO>();
		int rowSize=12;
		int start=(rowSize*page)-(rowSize-1);
		int end = page*rowSize;
		try
		{
			getConnection();
			String sql = "SELECT fno,name,poster,num FROM (SELECT fno,name,poster,rownum as num "
					+ "FROM (SELECT /*+INDEX_ASC(food_menupan fm_fno_pk)*/ fno,name,poster "
					+ "FROM food_menupan)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setPoster("http://menupan.com"+rs.getString(3));
				list.add(vo);
				
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally
		{
			disConnection();
		}
		
		return list;
	}
	public int foodTotalPage()
	{
		int total =0;
		try
		{
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/12.0) FROM food_menupan";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		}catch (Exception e) {
			// TODO: handle exception
		}finally
		{
			disConnection();
		}
		return total;
	}
	//2. 상세보기 => 조회수 증가 / WHERE
	
}
