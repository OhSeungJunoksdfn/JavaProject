package com.sist.main;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.table.*;

import com.sist.commons.ImageChange;
import com.sist.dao.*;
import com.sist.vo.*;
import java.util.List;

public class Zipcode extends JFrame 
implements ActionListener
{
	JLabel sidoLa,gugunLa,dongLa,searchLa;
	JTextField sidoTf,gugunTf,dongTf;
	JTable table;
	DefaultTableModel model;
	TableColumn column;
	JButton b1,b2;
	
	public Zipcode() {
		String[] col = {"우편번호", "주소"};
		String[][] row = new String[0][2];
		model = new DefaultTableModel(row,col)
    	{

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    	};
    	table= new JTable(model);
    	JScrollPane js = new JScrollPane(table);
    	for(int i=0;i<col.length;i++)
    	{
    		column=table.getColumnModel().getColumn(i);//오류
    		if(i==0)
    		{
    			column.setPreferredWidth(70);
    		}

    		else if(i==1)
    		{
    			column.setPreferredWidth(240);
    		}
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.pink);
		
    	setLayout(null);
    	
    	sidoLa = new JLabel("시/도");
    	gugunLa = new JLabel("구/군");
    	sidoTf = new JTextField();
		gugunTf = new JTextField();
		sidoLa.setBounds(20,20,150,20);
		gugunLa.setBounds(180,20,150,20);
		sidoTf.setBounds(20,45,150,50);
		gugunTf.setBounds(180,45,150,50);
		add(sidoLa);add(gugunLa);add(sidoTf);add(gugunTf);
		
		dongLa = new JLabel("동");
		dongTf = new JTextField();
		dongLa.setBounds(20,100,310,20);
		dongTf.setBounds(20,125,310,50);
		add(dongLa);add(dongTf);
		
		searchLa = new JLabel("검색 결과");
		searchLa.setBounds(20,180,310,20);
		add(searchLa);
		
		js.setBounds(20,205,310,300);
		add(js);
		
		
		b1 = new JButton("검색");
		b2 = new JButton("취소");
		b1.setBounds(165,525,80,40);
		b2.setBounds(250,525,80,40);
		add(b1);add(b2);
		
		setSize(350,600);
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	
    public void print()
	{
		// 테이블 데이터 지우기 
		for(int i=model.getRowCount()-1;i>=0;i--)
		{
			model.removeRow(i);
		}
		
		String sido= sidoTf.getText();
		if(sido.trim().length()<1)
		{
			JOptionPane.showMessageDialog(this, "시/도를 입력해주세요");
			sidoTf.requestFocus();
			return;
		}
		String gugun= gugunTf.getText();
		if(gugun.trim().length()<1)
		{
			JOptionPane.showMessageDialog(this, "구/군을 입력해주세요");
			gugunTf.requestFocus();
			return;
		}
		String dong= dongTf.getText();
		if(dong.trim().length()<1)
		{
			JOptionPane.showMessageDialog(this, "동을 입력해주세요");
			dongTf.requestFocus();
			return;
		}
		
		ZipcodeVO vo = new ZipcodeVO();
		vo.setSido(sido);
		vo.setGugun(gugun);
		vo.setDong(dong);
		
		ZipcodeDAO dao = ZipcodeDAO.newInstance();
		List<ZipcodeVO> list = dao.zipcodeFindData(vo);
		
		for(ZipcodeVO db : list)
		{
			String[] data={
					db.getBunji(),
					db.getSido()+" "+db.getGugun()+ " "+db.getDong()+" "+db.getBunji()
				};
				model.addRow(data);
		}
		// 데이터 받기 
	}
	
	public static void main(String[] args) {
		 new Zipcode();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2){
			setVisible(false);
		}
		else if(e.getSource()==b1)
		{
			print();
		}
	}

}
