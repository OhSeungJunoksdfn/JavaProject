package com.sist.main;
//상세 보기 
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;

import javax.swing.*;

import com.sist.commons.ImageChange;
import com.sist.dao.*;
import com.sist.vo.*;

public class DetailPanel extends JPanel
implements ActionListener
{
	JLabel product_poster;
    JLabel categoryLa,product_nameLa,priceLa,saleLa,deliverLa,hitLa;
    JLabel category,product_name,price,sale,deliver,hit;
    JLabel themeLa;
    JLabel[] images=new JLabel[6];
    JTextPane themeTa,contentTa;
    JButton b1,b2,b3;
    int mode=0;
    ControlPanel cp;
    String[] link={"","HOME","PROD","FIND"};
	
    public DetailPanel(ControlPanel cp)
    {
   	 this.cp=cp;
   	 setLayout(null);
   	 
   	product_poster=new JLabel("");
  	 product_poster.setBounds(20, 20, 400, 500);
  	 add(product_poster);
  	 
  	 categoryLa=new JLabel("카테고리");
  	 category=new JLabel();
  	 categoryLa.setBounds(330, 250, 80, 30);
  	 category.setBounds(415, 250, 250, 30);
  	 add(categoryLa);add(category);
  	 
  	 product_nameLa=new JLabel("제품명");
  	 product_name=new JLabel();
    product_nameLa.setBounds(330, 285, 80, 30);
    product_name.setBounds(415, 285, 250, 30);
  	 add(product_nameLa);add(product_name);
  	 
  	 saleLa=new JLabel("가격");
  	 sale=new JLabel();
  	 saleLa.setBounds(330, 320, 80, 30);
  	 sale.setBounds(415, 320, 250, 30);
  	 add(saleLa);add(sale);
  	 
  	 priceLa=new JLabel("할인가격");
  	 price=new JLabel();
  	 priceLa.setBounds(330, 350, 80, 30);
  	 price.setBounds(415, 350, 250, 30);
  	 add(priceLa);add(price);
  	
  	 
  	 deliverLa=new JLabel("배송");
  	 deliver=new JLabel();
  	 deliverLa.setBounds(330, 385, 80, 30);
  	 deliver.setBounds(415, 385, 250, 30);
  	 add(deliverLa);add(deliver);
  	   	 
  	 
  	 hitLa=new JLabel("평점");
  	 hit=new JLabel();
  	 hitLa.setBounds(330, 420, 80, 30);
    hit.setBounds(415, 420, 250, 30);
  	 add(hitLa);add(hit);
  	 
  	 b1=new JButton("찜하기");
  	 b2=new JButton("예약하기");
  	 b3=new JButton("목록");
  	 
  	 
  	 
  	 JPanel p=new JPanel();
  	 p.add(b1);p.add(b2);p.add(b3);
  	 p.setBounds(330, 525, 435, 35);
  	 add(p);
  	 
  	 b3.addActionListener(this);
  	 
    }
    public void detailPrint(int mode,ProductVO vo)
    {
   	 this.mode=mode;
   	 try
   	 {
   		 URL url=new URL(vo.getProduct_poster());
   		 Image img=
   			ImageChange.getImage(new ImageIcon(url), 300, 500);
   		 product_poster.setIcon(new ImageIcon(img));
         //category,product_name,price,sale,deliver,hit
    		category.setText(vo.getCategory());
    		product_name.setText(vo.getProduct_name());
    		price.setText(vo.getPrice());
    		sale.setText(vo.getSale());
    		deliver.setText(vo.getDeliver());
    		hit.setText(String.valueOf(vo.getHit()));
    	 }catch(Exception ex) {}
   	 
    }
   	 
   	 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b3)
		{
			cp.card.show(cp, link[mode]);
			// history.back()
		}
	}
}

