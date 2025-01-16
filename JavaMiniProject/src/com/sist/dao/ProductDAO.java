package com.sist.dao;

import java.sql.*;
import java.util.*;
import java.util.List;
import com.sist.vo.*;


public class ProductDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static ProductDAO dao;
	private final String URL = "jdbc:oracle:thin:@211.238.142.124:1521:XE";
	
	public ProductDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
	}
	//WEB => JDBC => DBCP
	//DBCP => 미리 연결하는 방식을 사용하는 방식으로 작동된다 => 재사용 (반환) => WEB에서 일반화
	//JDBC => 연결 /닫기 => 연결에 시간소모가 있다
	//2. Connection을 한개만 생성
	public static ProductDAO newInstance()
	{
		if(dao==null)
			dao=new ProductDAO();
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
	public List<ProductVO> ProductListData(int page)
	{
		List<ProductVO> list = new ArrayList<ProductVO>();
		int rowSize=12;
		int start=(rowSize*page)-(rowSize-1);
		int end = page*rowSize;
		try
		{
			getConnection();
			String sql = "SELECT product_no,product_name,product_poster,num FROM (SELECT product_no,product_name,product_poster,rownum as num "
					+ "FROM (SELECT /*+INDEX_ASC(product pk_product)*/ product_no,product_name,product_poster "
					+ "FROM product)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ProductVO vo = new ProductVO();
				vo.setProduct_no(rs.getInt(1));
				vo.setProduct_name(rs.getString(2));
				vo.setProduct_poster(rs.getString(3));
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
	public int productTotalPage()
	{
		int total =0;
		try
		{
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/12.0) FROM product";
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
}
