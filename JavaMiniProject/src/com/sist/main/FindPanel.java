package com.sist.main;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;//Image , Layout
import java.awt.event.*;
import java.net.URL;


import com.sist.vo.*;
import com.sist.commons.ImageChange;
import com.sist.dao.*;
import java.util.List;

public class FindPanel extends JPanel
implements ActionListener,MouseListener
{
	ControlPanel cp; // 상세보기 
    JTable table; // 모양관리 
    DefaultTableModel model; // 데이터 관리
    // MVC구조 
    JTextField tf; 
    JButton b;// 검색 
    TableColumn column;
    ProductDAO dao=ProductDAO.newInstance();
    
    public FindPanel(ControlPanel cp)
    {
    	this.cp=cp;
    	String[] col={
        		"번호","","제품명","가격","할인","조회수"
    	};
    	Object[][] row=new Object[0][6];
   	    model=new DefaultTableModel(row,col)
   	 {
            // 클릭시 => 편집 방지 
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    
			@Override
			public Class<?> getColumnClass(int columnIndex) {
			    if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
			        return getValueAt(0, columnIndex).getClass();
			    }
			    return Object.class; // 기본적으로 Object 클래스를 반환
			}
    		 // 오버라이딩 사용 => 라이브러리 변경 
			 // 상속없이 오버라이딩이 가능 => 익명의 클래스 (내부 클래스의 일종)
    		 
    	 };
    	 table=new JTable(model);
    	 table.setRowHeight(40);
    	 table.getTableHeader().setReorderingAllowed(false);
    	 JScrollPane js1=new JScrollPane(table);
    	 
    	 for(int i=0;i<col.length;i++)
     	 {
     		column=table.getColumnModel().getColumn(i);
     		if(i==0)
     			column.setPreferredWidth(60);
     		else if(i==1)
     			column.setPreferredWidth(80);
     		else if(i==2)
     			column.setPreferredWidth(350);
     		else if(i==3)
     			column.setPreferredWidth(180);
     		else if(i==4)
     			column.setPreferredWidth(160);
     		else if(i==5)
     			column.setPreferredWidth(80);
 }
    	 
    	 b=new JButton("검색");
    	 tf=new JTextField();
    	 
    	 setLayout(null);
    	 
    	 tf.setBounds(20, 20, 200, 30);
    	 b.setBounds(225, 20, 80, 30);
    	 
    	 js1.setBounds(20, 60, 800, 500);
    	 
    	 add(tf); add(b); 
    	 add(js1);
    	 
    	 b.addActionListener(this);
    	 tf.addActionListener(this);
    	 table.addMouseListener(this);
    	 
     }
    @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b || e.getSource()==tf)
		{
			// 검색어 읽기
			String address=tf.getText();
			if(address.trim().length()<1)
			{
				tf.requestFocus();
				return;
			}
			print(address);
		}
	}
    public void print(String address)
	{
		// 테이블 데이터 지우기 
		for(int i=model.getRowCount()-1;i>=0;i--)
		{
			model.removeRow(i);
		}
		
		// 데이터 받기 
		List<ProductVO> list=dao.ProductFindData(address);
		for(ProductVO vo:list)
		{
		  try
		  {
			URL url=new URL(vo.getProduct_poster());
			Image image=ImageChange.getImage(
					new ImageIcon(url), 30, 30);
			String name="<html><body><font color=blue>"
					    +vo.getProduct_name()+"</font></body></html>";
			Object[] data={
				vo.getProduct_no(),
				new ImageIcon(image),
				name,
				vo.getSale(),
				vo.getPrice(),
				vo.getHit()
			};
			model.addRow(data);
		  }catch(Exception ex){}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table)
		{
			if(e.getClickCount()==2)
			{
				int row=table.getSelectedRow();
				String fno=
					model.getValueAt(row, 0).toString();
				ProductVO vo=dao.DetailData(Integer.parseInt(fno));
				cp.dp.detailPrint(3, vo);
				cp.card.show(cp, "DETAIL");
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