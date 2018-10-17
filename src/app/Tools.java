package app;

import geso.traphaco.center.db.sql.dbutils;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tools extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel pMain, phienthi;
	private JLabel lblTB;
	private JButton btnTooltips, btnKhoBT, btnKhoKygui;
	
	public Tools(){
		setTitle("Tools");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(450, 110);
		setResizable(false);
		setLocationRelativeTo(null); 
		pMain = new JPanel(new GridLayout(2, 1));
		this.add(pMain);
		PanelHienthi();		
	}
	
	void PanelHienthi(){
		phienthi = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		phienthi.setBorder(BorderFactory.createLineBorder(Color.black));
		pMain.add(phienthi);
		btnKhoBT = new JButton("Chạy kho bt");
		btnKhoBT.addActionListener(this);
		phienthi.add(btnKhoBT);
		btnKhoKygui = new JButton("Chạy kho ký gửi");
		btnKhoKygui.addActionListener(this);
		phienthi.add(btnKhoKygui);
		btnTooltips = new JButton("Tooltips");
		btnTooltips.addActionListener(this);
		phienthi.add(btnTooltips);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		Object o = e.getSource();
		if (o.equals(btnTooltips))
		{
			btnTooltips.setEnabled(false);
	        final CapnhatTooltip x= new CapnhatTooltip();
	        x.addWindowListener(new WindowAdapter(){
		        @Override
		        public void windowClosing(WindowEvent e){
			        x.dispose();
			        btnTooltips.setEnabled(true);
		        }
	        });
		}
		else if (o.equals(btnKhoBT))
		{
			btnKhoBT.setEnabled(false);
	        final Capnhat_khobt x= new Capnhat_khobt();
	        x.addWindowListener(new WindowAdapter(){
		        @Override
		        public void windowClosing(WindowEvent e){
			        x.dispose();
			        btnKhoBT.setEnabled(true);
		        }
	        });
		}
		else if (o.equals(btnKhoKygui))
		{
			btnKhoKygui.setEnabled(false);
	        final Capnhat_khokygui x= new Capnhat_khokygui();
	        x.addWindowListener(new WindowAdapter(){
		        @Override
		        public void windowClosing(WindowEvent e){
			        x.dispose();
			        btnKhoKygui.setEnabled(true);
		        }
	        });
		}
	}
	
	public static void main(String[] args)
	{
		new Tools().setVisible(true);
	}
}
