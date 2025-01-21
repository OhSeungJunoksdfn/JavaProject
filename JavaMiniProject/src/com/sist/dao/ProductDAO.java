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
		}catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}

	
	
	public List<ProductVO> GenreData(int page, String genre) {
	    List<ProductVO> list = new ArrayList<ProductVO>();
	    try {
	        getConnection();
	        String sql;
	        if ("기타".equals(genre)) {
	            // 기타 카테고리: 특정 카테고리를 제외한 나머지
	            sql = "SELECT product_no, product_name, product_poster, num "
	                + "FROM (SELECT product_no, product_name, product_poster, rownum AS num "
	                + "FROM (SELECT /*+INDEX_ASC(product pk_product)*/ product_no, product_name, product_poster "
	                + "FROM product WHERE category NOT IN ('스킨케어', '클렌징', '메이크업', '헤어케어', '바디케어', "
	                + "'여성', '맨즈케어', '선케어', '뷰티소품'))) "
	                + "WHERE num BETWEEN ? AND ?";
	        } else {
	            // 일반 카테고리: 해당 카테고리 데이터만 선택
	            sql = "SELECT product_no, product_name, product_poster, num "
	                + "FROM (SELECT product_no, product_name, product_poster, rownum AS num "
	                + "FROM (SELECT /*+INDEX_ASC(product pk_product)*/ product_no, product_name, product_poster "
	                + "FROM product WHERE category LIKE '%'||?||'%')) "
	                + "WHERE num BETWEEN ? AND ?";
	        }
	        ps = conn.prepareStatement(sql);
	        if (!"기타".equals(genre)) {
	            ps.setString(1, genre);
	        }
	        int rowSize = 12;
	        int start = (rowSize * page) - (rowSize - 1);
	        int end = rowSize * page;
	        ps.setInt("기타".equals(genre) ? 1 : 2, start);
	        ps.setInt("기타".equals(genre) ? 2 : 3, end);

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            ProductVO vo = new ProductVO();
	            vo.setProduct_no(rs.getInt(1));
	            vo.setProduct_name(rs.getString(2));
	            vo.setProduct_poster(rs.getString(3));
	            list.add(vo);
	        }
	        rs.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        disConnection();
	    }
	    return list;
	}
	public int GenreTotalPage(String genre) {
	    int total = 0;
	    try {
	        getConnection();
	        String sql;
	        if ("기타".equals(genre)) {
	            sql = "SELECT CEIL(COUNT(*)/12.0) "
	                + "FROM product "
	                + "WHERE category NOT IN ('스킨케어', '클렌징', '메이크업', '헤어케어', '바디케어', "
	                + "'여성', '맨즈케어', '선케어', '뷰티소품')";
	        } else {
	            sql = "SELECT CEIL(COUNT(*)/12.0) "
	                + "FROM product "
	                + "WHERE category LIKE '%'||?||'%'";
	        }
	        ps = conn.prepareStatement(sql);
	        if (!"기타".equals(genre)) {
	            ps.setString(1, genre);
	        }
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        total = rs.getInt(1);
	        rs.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        disConnection();
	    }
	    return total;
	}
	   
	   
	   // 4.검색 => 주소
	public List<ProductVO> ProductFindData(String name)
	{
		List<ProductVO> list=
			   new ArrayList<ProductVO>();
		try
		{
		   getConnection();
		  
		   String sql="SELECT product_no,product_poster,product_name,price,sale,hit,rownum  "
				     +"FROM product "
				     +"WHERE REGEXP_LIKE(PRODUCT_NAME,?) AND rownum<=10 "
				     +"ORDER BY product_no ASC";
		   ps=conn.prepareStatement(sql);
		   ps.setString(1, name);
		   ResultSet rs=ps.executeQuery();
		   while(rs.next())
		   {
			   ProductVO vo=new ProductVO();
			   vo.setProduct_no(rs.getInt(1));
			   vo.setProduct_poster(rs.getString(2));
			   vo.setProduct_name(rs.getString(3));
			   vo.setPrice(rs.getString(4));
			   vo.setSale(rs.getString(5));
			   vo.setHit(rs.getInt(6));
			   list.add(vo);
		   }
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
	   return list;
	}

	   /*
	    * PRODUCT_NO	NUMBER
	   PRODUCT_NAME	VARCHAR2(300 BYTE)
	   PRODUCT_POSTER	VARCHAR2(300 BYTE)
	   PRICE	VARCHAR2(50 BYTE)
	   SALE	VARCHAR2(200 BYTE)
	   DELIVER	VARCHAR2(200 BYTE)
	   HIT	NUMBER
	   CATEGORY	VARCHAR2(30 BYTE)
	    */
	   

	public ProductVO DetailData(int product_no)
	{
		ProductVO vo=new ProductVO();
		try
		{
			getConnection();
			String sql="SELECT product_name,product_poster,price,sale,deliver,hit,category "
					+"FROM product "
					+"WHERE product_no="+product_no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setProduct_name(rs.getString(1));
			vo.setProduct_poster(rs.getString(2));
			vo.setPrice(rs.getString(3));
			vo.setSale(rs.getString(4));
			vo.setDeliver(rs.getNString(5));
			vo.setHit(rs.getInt(6));
			vo.setCategory(rs.getString(7));
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
	


	
}