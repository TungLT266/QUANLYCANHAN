package geso.traphaco.center.beans.kenhbanhang.imp;

import geso.traphaco.center.beans.kenhbanhang.IKenhbanhang;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Kenhbanhang implements IKenhbanhang
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String kenhbanhang;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String htbhId;
	String nkId;
	ResultSet htbhRs;
	ResultSet nkRs;
	
	dbutils db;
	
	public Kenhbanhang(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.kenhbanhang = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Kenhbanhang(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.kenhbanhang = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nkId = "";
		this.htbhId ="";
		this.msg = "";
		this.db = new dbutils();
		if(id.length() > 0)
			this.init();
		createRs();
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
	
	public String getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(String kenhbanhang) 
	{
		this.kenhbanhang = kenhbanhang;
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
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

	public boolean CreateKbh()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		String command = "insert into kenhbanhang(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua) values(N'" + this.kenhbanhang + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "')"; 
		
		if (!db.update(command)){
			this.msg = "Khong the tao moi kenh ban hang: " + command;		
			this.db.update("rollback");
			return false;
		}
		/*
		 * thuc hien cap nhat lai smartid
		 * 
		 */
		String query_getid = "select IDENT_CURRENT('kenhbanhang') as id";
		ResultSet rs_getpk_seq = this.db.get(query_getid);
		rs_getpk_seq.next();
		this.id = rs_getpk_seq.getString("id");
		
		String sql_update_smartid="update kenhbanhang set smartid='"+this.id+"' where pk_seq=" + this.id;
		try{
		if(!db.update(sql_update_smartid)){
			 db.update("rollback");
			  System.out.println("Khong Cap Nhat Lai Duoc SmartId Cho Bang Kenh Ban Hang,Vui Long Lien He Voi Admin De Sua Loi Nay");
			return false;
		}
		}catch(Exception er){
			
		}
		
		if(this.nkId != null)
		{
			command = "insert into nhomkenh_kenhbanhang(kbh_fk, nk_fk) select '" + this.id + "', pk_seq from nhomkenh where pk_seq in (" + this.nkId + ")"; 	
			if(!db.update(command)){
				this.msg="Vui lòng chọn nhóm kênh.";
				db.getConnection().rollback();
				return false;
			}
		}
		
		if(this.htbhId != null)
		{
			command = "insert into hethongbanhang_kenhbanhang values ('" + this.htbhId + "', '" + this.id + "')"; 	
			if(!db.update(command)){
				this.msg="Bạn vui lòng chọn hệ thống bán hàng. ";
				db.getConnection().rollback();
				return false;
			}
		}
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.msg="Khong The Them Kenh Ban Hang Nay , Loi Dong Lenh Sau :" + er.toString();
			this.db.update("rollback");
			return false;
		}
		return true;
	}

	public boolean UpdateKbh() 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			String command ="update kenhbanhang set ten = N'" + this.kenhbanhang + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
			
			if (!this.db.update(command)){
				this.msg = "Khong the cap nhat: " + command;
				this.db.update("rollback");
				return false;
			}
			
			command = "delete nhomkenh_kenhbanhang where kbh_fk = '" + this.id + "'";
			if(!db.update(command)){
				this.msg="Khong the cap nhat nhomkenh_kenhbanhang .vui long thuc hien lai,hoac lien he voi admin de sua loi nay . Loi insert: "+command;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nkId != null)
			{
				command = "insert into nhomkenh_kenhbanhang(kbh_fk, nk_fk) select '" + this.id + "', pk_seq from nhomkenh where pk_seq in (" + this.nkId + ")"; 	
				if(!db.update(command)){
					this.msg="Vui lòng chọn nhóm kênh.";
					db.getConnection().rollback();
					return false;
				}
			}
			
			command = "delete hethongbanhang_kenhbanhang where kbh_fk = '" + this.id + "'";
			if(!db.update(command)){
				this.msg="Khong the cap nhat hethongbanhang_kenhbanhang .vui long thuc hien lai,hoac lien he voi admin de sua loi nay . Loi insert: "+command;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.htbhId != null)
			{
				command = "insert into hethongbanhang_kenhbanhang values ('" + this.htbhId + "', '" + this.id + "')"; 	
				if(!db.update(command)){
					this.msg="Vui lòng chọn hệ thống bán hàng.";
					db.getConnection().rollback();
					return false;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.msg="Khong The Them Kenh Ban Hang Nay , Loi Dong Lenh Sau :" + er.toString();
			this.db.update("rollback");
			return false;
		}
		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id, a.ten as kbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from kenhbanhang a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq= '"+ this.id + "'"; 
		System.out.println("thong tin kenh: " + query);
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.kenhbanhang = rs.getString("kbhTen");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.nkId = rs.getString("nkId")==null?"": rs.getString("nkId");
        	this.htbhId = rs.getString("htbhId");
        	  	
       	}catch(Exception e){}
	}
	
	private void createRs()
	{
		String query = "select a.pk_seq, a.ten from hethongbanhang a "; 
        this.htbhRs =  this.db.get(query);
        
        query = "select a.pk_seq, a.ten from nhomkenh a"; 
        this.nkRs =  this.db.get(query);
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}

	@Override
	public String getHtbhId() {
		// TODO Auto-generated method stub
		return this.htbhId;
	}

	@Override
	public void setHtbhId(String htbhId) {
		// TODO Auto-generated method stub
		this.htbhId = htbhId;
	}

	@Override
	public ResultSet getHtbhRs() {
		// TODO Auto-generated method stub
		return this.htbhRs;
	}

	@Override
	public void setHtbhRs(ResultSet htbhRs) {
		// TODO Auto-generated method stub
		this.htbhRs = htbhRs;
	}

	@Override
	public String getNkId() {
		// TODO Auto-generated method stub
		return this.nkId;
	}

	@Override
	public void setNkId(String nkId) {
		// TODO Auto-generated method stub
		this.nkId = nkId;
	}

	@Override
	public ResultSet getNkRs() {
		// TODO Auto-generated method stub
		return this.nkRs;
	}

	@Override
	public void setNkRs(ResultSet nkRs) {
		// TODO Auto-generated method stub
		this.nkRs = nkRs;
	}
}
