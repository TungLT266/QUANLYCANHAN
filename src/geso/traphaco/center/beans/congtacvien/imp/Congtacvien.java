package geso.traphaco.center.beans.congtacvien.imp;
import geso.traphaco.center.beans.congtacvien.ICongtacvien;
import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.servlets.reports.TinhHinhThucHienCTNPPSvl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Congtacvien implements ICongtacvien
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ma;
	String ten;
	String sodienthoai;
	String diachi;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	boolean isdelete;	
	String kenhbhid ="";
	String gioitinh;
	String chuyenkhoa;
	String chucvu;
	String ngaysinh;
	String lichkham;
	String sothich;
	
	ResultSet nppList;
	String nppId;
	ResultSet diabanRs;
	String diabanId;
	ResultSet tdvRs;
	String tdvId;
	ResultSet tinhthanhRs;
	String tinhthanhId;
	ResultSet quanhuyenRs;
	String quanhuyenId;
	ResultSet khachhangRs;
	ResultSet kenhbanhang;
	String khachhangId;
	dbutils db;
	String[] KhTen;
	

	String[] KhMa;
	public Congtacvien(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.diachi = param[3];
		this.trangthai = param[4];
		this.ngaytao = param[5];
		this.nguoitao = param[6];
		this.ngaysua = param[7];
		this.nguoisua = param[8];
		this.isdelete = true;
		this.msg = "";
		this.nppId = "";
		this.quanhuyenId="";
		this.tinhthanhId="";
		this.diabanId="";
		this.tdvId="";
	}
	
	public Congtacvien(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ma="";
		this.ten = "";
		this.sodienthoai = "";
		this.diachi = "";
		this.trangthai = "2";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua ="";
		this.nppId ="";
		this.msg = "";		
		this.ngaysinh = "";
		this.chucvu="";
		this.chuyenkhoa="";
		this.khachhangId="";
		this.gioitinh="1";
		this.lichkham="";
		this.sothich="";
		this.quanhuyenId="";
		this.tinhthanhId="";
		this.diabanId="";
		this.tdvId="";
		
		this.isdelete = true;
		
		if(id.length() > 0)
			this.init();
		else
			this.createRS();
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
	
	public String getTen()
	{
		return this.ten;
	}
	
	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	public String getSodt()
	{
		return this.sodienthoai;
	}
	
	public void setSodt(String sodienthoai)
	{
		this.sodienthoai = sodienthoai;
	}
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getNppId() 
	{		
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
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
	
	public void setNppList(ResultSet npplist)
	{
		this.nppList = npplist;
	}
	
	public ResultSet getNppList() 
	{
		return this.nppList;
	}
	
	public boolean getIsDelete() 
	{
		return this.isdelete;
	}

	public void setIsDelete(boolean isDelete) 
	{
		this.isdelete = isDelete;
	}
	
	public void createRS() 
	{
		createKbhRS();
		String query = "select pk_seq, diengiai from diaban where trangthai=1";
		this.diabanRs = db.get(query);
		
		query = "select pk_seq, ten from daidienkinhdoanh where trangthai=1";
		this.tdvRs = db.get(query);
		
		query = "select pk_seq, ten from tinhthanh where trangthai=1";
		this.tinhthanhRs = db.get(query);
		
		if(this.tinhthanhId.trim().length() > 0)
		{
			query = "select pk_seq, ten from quanhuyen where trangthai=1";
				query += " and tinhthanh_fk = '" + this.tinhthanhId + "' "; 
			this.quanhuyenRs = db.get(query);
		}
		
		query = "select pk_seq, ten from nhaphanphoi where isKHACHHANG = '0'  and trangthai=1";
		if(this.quanhuyenId.trim().length() > 0)
			query += " and quanhuyen_fk = '" + this.quanhuyenId + "' "; 
		this.nppList = db.get(query);
		query = "select b.PK_SEQ, b.Ma, b.ten from KHACHHANG b where trangthai=1";
		if(this.tdvId.trim().length() > 0)
		{
			query += "union select b.PK_SEQ, b.Ma, b.ten from ddkd_khachhang a inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ where trangthai=1";
			query += " and ddkd_fk = '" + this.tdvId + "' ";
			
		}
		System.out.println("Khachhang "+query);
		this.khachhangRs = db.get(query);
	}
	
	public boolean CreateDdkd() 
	{
		try
		{
			 this.db.getConnection().setAutoCommit(false);
			 
			 if(userId==null||userId=="")
			 {
				this.db.update("rollback");
				this.msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			 }
			
			 this.ngaytao = getDateTime();
			 this.nguoitao = this.userId;
			
		 String query = "insert into congtacvien(ma, ten,dienthoai,diachi, trangthai,nguoitao,nguoisua,ngaytao,ngaysua,gioitinh,chuyenkhoa,chucvu,ngaysinh,lichkham,sothich, diaban_fk, tdv_fk) " +
		  				" values('" + this.ma + "', N'" + this.ten +"','" + this.sodienthoai + "',N'"+ this.diachi + "', '1', '" + this.nguoitao + "','" + this.nguoitao + "','" + this.ngaytao + "','" + this.ngaytao + "',"+
		  				" '"+this.gioitinh+"',N'"+this.chuyenkhoa+"',N'"+this.chucvu+"','"+this.ngaysinh+"','"+this.lichkham+"',N'"+this.sothich+"', '" + this.diabanId +"', '" + this.tdvId + "')";		
		 if(!(this.db.update(query))){
				this.msg="khong the chuyen Chi Tieu"+ query;
				this.db.update("rollback");
				return false;
			}
		 this.msg = query;	
		 	query = "select IDENT_CURRENT('congtacvien') as ctvId";
		 	ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("ctvId");
			
			if(this.khachhangId.length()>0)
			{		
				query = "insert CONGTACVIEN_KHACHHANG(ctv_fk, kh_fk) select '" + this.id + "', pk_seq " +
						"from KHACHHANG where pk_seq in ( " + this.khachhangId + " ) ";
				System.out.println("---CHEN NPP: " + query );
				if(!(this.db.update(query)))
				{
					this.db.update("rollback");
					this.msg = "Lỗi: " + query;
					return false;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.msg="Khong The Thuc Hien Luu Dai Dien Kinh Doanh Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
			return false;
		}
		return true;
	}

	public boolean UpdateDdkd() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		if(userId==null||userId=="")
		 {
			this.db.update("rollback");
			this.msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			return false;
		 }
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update CONGTACVIEN set ten = N'" + this.ten + "', dienthoai ='" + this.sodienthoai + "', diachi = N'"+ this.diachi + "', trangthai = '" + this.trangthai + "', nguoisua = '" + this.userId + "', ngaysua = '" + this.ngaysua + "', " +
						   " ma='"+this.ma+"',gioitinh=N'"+this.gioitinh+"',chuyenkhoa=N'"+this.chuyenkhoa+"',chucvu=N'"+this.chucvu+"',ngaysinh='"+this.ngaysinh+"',lichkham='"+this.lichkham+"',sothich=N'"+this.sothich+"' ";
						   if(this.diabanId.length() > 0)
						   query+= " , diaban_fk = '"+ this.diabanId+"' ";
						   if(this.tdvId.length() > 0)
							   query+=", tdv_fk = '"+this.tdvId+"' ";
						   if(this.tinhthanhId.length() > 0)
						   	query+= ", tinhthanh_fk='"+this.tinhthanhId+"'";
						   if(this.quanhuyenId.length() > 0)
						   query += ", quanhuyen_fk='"+this.quanhuyenId+"' "; 
						   if(this.kenhbhid.length() > 0)
							   query += ", kbh_fk ='"+this.kenhbhid+"' "; 
			query += " where pk_seq = '" + this.id + "'" ;
			
			System.out.println("1. " + query);
			if(!this.db.update(query))
			{
				this.msg = "Lỗi: " + query;
				this.db.update("rollback");
				return false;
			}
			
			query="delete CONGTACVIEN_KHACHHANG where ctv_fk = '" + this.id + "'";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi: " + query;
				this.db.update("rollback");
				return false;
			}
			
			if(this.khachhangId.length()>0)
			{		
				query = "insert CONGTACVIEN_KHACHHANG(ctv_fk, kh_fk) select '" + this.id + "', pk_seq " +
						"from KHACHHANG where pk_seq in ( " + this.khachhangId + " ) ";
				System.out.println("---CHEN NPP: " + query );
				if(!(this.db.update(query)))
				{
					this.db.update("rollback");
					this.msg = "Lỗi: " + query;
					return false;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
	}	
	
	private void init() 
	{
		String query = "select a.ma, a.pk_seq as id, a.ten, a.dienthoai, a.diachi, a.trangthai, a.diaban_fk, a.tinhthanh_fk, a.tdv_fk, a.quanhuyen_fk, a.gioitinh, a.chuyenkhoa, a.chucvu, a.ngaysinh, a.lichkham, a.sothich,a.npp_fk,kbh_fk as kenhbanhang "+
					   " from CONGTACVIEN a  "+ 
					   " where  a.pk_seq ="+this.id ;
		
		System.out.println("1/ In ra nhân viên: " +query);
		ResultSet rs =  this.db.get(query);
        try
        {
            rs.next();     
        	this.id = rs.getString("id");
        	this.ma = rs.getString("ma");
        	this.ten = rs.getString("ten");
        	this.sodienthoai = rs.getString("dienthoai");
        	this.diachi = rs.getString("diachi");
        	this.trangthai = rs.getString("trangthai");
        	this.diabanId = rs.getString("diaban_fk");
        	this.gioitinh = rs.getString("gioitinh");
        	this.chuyenkhoa = rs.getString("chuyenkhoa");
        	this.chucvu = rs.getString("chucvu");
        	this.ngaysinh = rs.getString("ngaysinh");
        	this.lichkham = rs.getString("lichkham");
        	this.sothich = rs.getString("sothich");
        	this.tdvId=rs.getString("tdv_fk")==null?"":rs.getString("tdv_fk");
        	this.tinhthanhId=rs.getString("tinhthanh_fk")==null?"":rs.getString("tinhthanh_fk");
        	this.quanhuyenId=rs.getString("quanhuyen_fk")==null?"":rs.getString("quanhuyen_fk");
        	this.nppId=rs.getString("npp_fk")==null?"":rs.getString("npp_fk");		
        	this.kenhbhid = rs.getString("kenhbanhang") == null ?"":rs.getString("kenhbanhang") ;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        query = "select a.kh_fk,isnull(kh.ten,'') as ten,isnull(kh.ma,'') as mafast from CONGTACVIEN_KHACHHANG a inner join khachhang kh on kh.pk_seq = a.kh_fk "
        		+ " where a.CTV_FK = '" + this.id + "'";
        System.out.println("Congtacvien_khachhang "+query);
		rs = this.db.get(query);
		if (rs != null)
    	{
			int i = 0;
			try
			{
				this.KhMa = new String[10000];
				this.KhTen = new String[10000];
	    		while(rs.next())
	    		{
	    			this.KhMa[i] = rs.getString("mafast") ;
	    			this.KhTen[i] = rs.getString("ten") ;
	    			this.khachhangId += this.khachhangId +",";
	    			i++;
	    		}
	    		rs.close();
	    		
	    		if(this.khachhangId.trim().length() > 0)
	    		{
	    			this.khachhangId = this.khachhangId.substring(0, this.khachhangId.length() - 1);
	    			
	    		}
			}
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
    	}
		
       	createRS(); 
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public void DBClose() {
		try{

		if(nppList!=null){
		 nppList.close();
		
		}
		if(db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
	}

	
	public String getDiabanId() {
		
		return this.diabanId;
	}

	
	public void setDiabanId(String diabanId) {
		
		this.diabanId = diabanId;
	}

	
	public ResultSet getDiabanRs() {
		
		return this.diabanRs;
	}

	
	public void setDiabanRs(ResultSet diabanRs) {
		
		this.diabanRs = diabanRs;
	}

	
	public String getMa() {
		
		return this.ma;
	}

	
	public void setMa(String ma) {
		
		this.ma=ma;
	}

	
	public String getGioitinh() {
		
		return this.gioitinh;
	}

	
	public void setGioitinh(String gioitinh) {
		
		this.gioitinh=gioitinh;
	}

	
	public String getChuyenKhoa() {
		
		return this.chuyenkhoa;
	}

	
	public void setChuyenkhoa(String chuyenkhoa) {
		
		this.chuyenkhoa = chuyenkhoa;
	}

	
	public String getChucvu() {
		
		return this.chucvu;
	}

	
	public void setChucvu(String chucvu) {
		
		this.chucvu = chucvu;
	}

	
	public String getNgaysinh() {
		
		return this.ngaysinh;
	}

	
	public void setNgaysinh(String ngaysinh) {
		
		this.ngaysinh = ngaysinh;
	}

	
	public String getLichkham() {
		
		return this.lichkham;
	}

	
	public void setLichkham(String lichkham) {
		
		this.lichkham = lichkham;
	}

	
	public String getSothich() {
		
		return this.sothich;
	}

	
	public void setSothich(String sothich) {
		
		this.sothich=sothich;
	}

	
	public String getTDVId() {
		
		return this.tdvId;
	}

	
	public void setTDVId(String tdvId) {
		
		this.tdvId=tdvId;
	}

	
	public ResultSet getTDVRs() {
		
		return this.tdvRs;
	}

	
	public void setTDVRs(ResultSet tdvRs) {
		
		this.tdvRs = tdvRs;
	}

	
	public String getKhachhangId() {
		
		return this.khachhangId;
	}

	
	public void setKhachhangId(String khachhangId) {
		
		this.khachhangId=khachhangId;
	}

	
	public ResultSet getKhachhangRs() {
		
		return this.khachhangRs;
	}

	
	public void setKhachhangRs(ResultSet khachhangRs) {
		
		this.khachhangRs=khachhangRs;
	}

	
	public String getTinhthanhId() {
		
		return this.tinhthanhId;
	}
	public void createKbhRS()
	{
		this.kenhbanhang =  this.db.get("select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' order by diengiai");
		
	}
	
	public void setTinhthanhId(String tinhthanhId) {
		
		this.tinhthanhId=tinhthanhId;
	}

	
	public ResultSet getTinhthanhRs() {
		
		return this.tinhthanhRs;
	}

	
	public void setTinhthanhRs(ResultSet tinhthanhRs) {
		
		this.tinhthanhRs = tinhthanhRs;
	}

	
	public String getQuanhuyenId() {
		
		return this.quanhuyenId;
	}

	
	public void setQuanhuyenId(String quanhuyenId) {
		
		this.quanhuyenId= quanhuyenId;
	}

	
	public ResultSet getQuanhuyenRs() {
		
		return this.quanhuyenRs;
	}

	
	public void setQuanhuyenRs(ResultSet quanhuyenRs) {
		
		this.quanhuyenRs = quanhuyenRs;
	}

	
	public ResultSet getKenhbanhang() {
		
		return this.kenhbanhang;
	}

	
	public void setKenhbanhang(ResultSet kenhbanhang) {
		
		this.kenhbanhang = kenhbanhang;
	}

	
	public String getKbhId() {
		
		return this.kenhbhid;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kenhbhid = kbhId;
	}
	public String[] getKhTen() {
		return KhTen;
	}

	public void setKhTen(String[] khTen) {
		KhTen = khTen;
	}

	public String[] getKhMa() {
		return KhMa;
	}

	public void setKhMa(String[] khMa) {
		KhMa = khMa;
	}
}