package geso.traphaco.center.beans.huynhaphangtt.imp;

import geso.traphaco.center.beans.huynhaphangtt.IHuynhaphang;
import geso.traphaco.center.beans.huynhaphangtt.ISanpham;
import geso.traphaco.center.beans.huynhaphangtt.imp.Sanpham;

import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.util.*;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;


public class Huynhaphang implements IHuynhaphang 
{	
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id; //ma don hang
	String ngaygiaodich;
	
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String nppId;
	String nppTen;
	String SoChungtu;
	String NgayChungtu;
	String NhaphangId;
	String Khoid;
	String KBhid;
	

	List<ISanpham> splist;
	dbutils db;

	public Huynhaphang(String id)
	{
		this.NhaphangId="";
		this.id = id;
		this.ngaygiaodich = "";
		this.nppTen = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.msg = "";
		this.SoChungtu="";
		this.NgayChungtu="";
		db = new dbutils();	
		this.splist=new  ArrayList<ISanpham>();
		
	}

	public String getUserId() 
	{		
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;		
	}
	


	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;		
	}
	
	
	
	public String getNgaygiaodich() 
	{	
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String ngaygiaodich) 
	{
		this.ngaygiaodich = ngaygiaodich;		
	}
	

	public String getTrangthai()
	{	
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;		
	}
	
	public String getNgaytao()
	{	
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;		
	}
	
	public String getNguoitao() 
	{		
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;		
	}

	public String getNgaysua() 
	{		
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;	
	}

	public String getNguoisua() 
	{		
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;	
	}
	

	public String getMessage() 
	{	
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;		
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	

	
	
	public List<ISanpham> getSpList()
	{	
		return this.splist;
	}
	
	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}



	
	
	public void init() 
	{
		try{
			this.splist.clear();
			
		String sql=     " select sp.pk_seq as spid,nhsp.giagross,nhsp.gianet, nh.pk_seq as  " +
						" nhaphangid,nh.kho_fk,nh.kbh_fk , nhsp.DONVITINH, "+
						" nh.SOCHUNGTU,nh.NGAYCHOT,NHAPHANG.NGAYCHUNGTU ,sp.ma,sp.ten,nhsp.soluong,nhsp.solo,nhsp.ngayhethan ,nhsp.ISXUATKHO "+
						" from HUYNHAPHANG nh "+
						" inner join NHAPHANG on NHAPHANG.PK_SEQ=nh.NHAPHANG_FK "+
						" inner join HUYNHAPHANG_SP nhsp on nh.pk_seq=nhsp.HUYNHAPHANG_FK "+  
						" inner join sanpham sp on sp.PK_SEQ=nhsp.SANPHAM_FK       "+
						" where nh.PK_SEQ="+this.id;
 
		ResultSet rs=db.get(sql);
		while(rs.next()){
				this.NgayChungtu=rs.getString("ngaychungtu");
				this.SoChungtu=rs.getString("SOCHUNGTU");
				this.NhaphangId=rs.getString("NhaphangId");
				this.Khoid=rs.getString("kho_fk");
				this.KBhid=rs.getString("kbh_fk");
				ISanpham sp=new Sanpham();
				sp.setId(rs.getString("spid"));
 
				sp.setMasanpham(rs.getString("ma"));
				sp.setTensanpham(rs.getString("ten"));
				sp.setSoLo(rs.getString("solo"));
				sp.setNgayhethan(rs.getString("ngayhethan"));
				sp.setDonvitinh(rs.getString("donvitinh"));
				sp.setSoluong(rs.getString("soluong"));
				sp.setISXUATKHO(rs.getString("ISXUATKHO"));
				this.splist.add(sp);
			
		}
		}catch(Exception er){
			
			er.printStackTrace();
			
		}
		
		
		
	}
		


	public void DBclose()
	{
		try 
		{
			
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}
	
	@Override
	public String getSochungtu() {
		// TODO Auto-generated method stub
		return this.SoChungtu;
	}

	@Override
	public void setSochungtu(String sochungtu) {
		// TODO Auto-generated method stub
		this.SoChungtu=sochungtu;
		
	}

	@Override
	public String getNgaychungtu() {
		// TODO Auto-generated method stub
		return this.NgayChungtu;
	}

	@Override
	public void setNgaychungtu(String ngaychungtu) {
		// TODO Auto-generated method stub
		this.NgayChungtu=ngaychungtu;
	}

	@Override
	public String getNhaphangId() {
		// TODO Auto-generated method stub
		return this.NhaphangId;
	}

	@Override
	public void setNhaphangId(String NhaphangId) {
		// TODO Auto-generated method stub
		this.NhaphangId=NhaphangId;
		
	}

	@Override
	public String getKhoId() {
		// TODO Auto-generated method stub
		return this.Khoid;
	}

	@Override
	public void setkhoId(String _KhoId) {
		// TODO Auto-generated method stub
		this.Khoid=_KhoId;
		
	}

	@Override
	public String getKBHId() {
		// TODO Auto-generated method stub
		return this.KBhid;
	}

	@Override
	public void setKBHId(String _KbhId) {
		// TODO Auto-generated method stub
		this.KBhid=_KbhId;
		
	}
	
}
