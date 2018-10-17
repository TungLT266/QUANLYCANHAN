package geso.traphaco.center.beans.hangcuahang.imp;

import geso.traphaco.center.beans.hangcuahang.IHangcuahang;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hangcuahang implements IHangcuahang
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String hangcuahang;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String doanhthu_tu;
	String doanhthu_den;
	
	dbutils db;
	
	public Hangcuahang(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.hangcuahang = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.msg = "";
		this.doanhthu_tu = "";
		this.doanhthu_den = "";
	}
	
	public Hangcuahang(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.hangcuahang = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.doanhthu_tu = "";
		this.doanhthu_den = "";
		if(id.length() > 0)
			this.init();
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
	
	public String getHangcuahang() 
	{
		return this.hangcuahang;
	}

	public void setHangcuahang(String hangcuahang) 
	{
		this.hangcuahang = hangcuahang;
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

	public boolean CreateHch()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			String command = "insert into hangcuahang(diengiai,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,hang) values(N'" + this.diengiai + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "',N'" + this.hangcuahang + "')"; 
		
			if (!db.update(command)){
				//this.msg = "insert into hangcuahang values(N'" + this.diengiai + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "','" + this.hangcuahang + "')";
				this.msg="Ban khong the luu duoc";
				db.update("rollback");
				return false;
			}
			/*
			 * thuc hien cap nhat lai smartid
			 * 
			 */
			String query_getid = "select IDENT_CURRENT('hangcuahang') as id";
			ResultSet rs_getpk_seq = this.db.get(query_getid);
			rs_getpk_seq.next();
			this.id = rs_getpk_seq.getString("id");
			String sql_update_smartid="update hangcuahang set smartid='"+this.id+"' where pk_seq=" + this.id;
			try{
			if(!db.update(sql_update_smartid)){
				db.update("rollback");
					System.out.println("Khong The Thuc Hien Luu Bang Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
				return false;
			}
			}catch(Exception er){
				db.update("rollback");
				return false;
				
			}
			//Them moi HANGCUAHANG_DOANHTHU
			command = "insert into HANGCUAHANG_DOANHTHU " 
					+ "select '"+this.id+"','"+this.doanhthu_tu+"','"+this.doanhthu_den+"' "
					+ "";
			if(!db.update(command))
			{
				this.msg="Ban khong the luu duoc HANGCUAHANG_DOANHTHU";
				db.update("rollback");
				return false;
			}
			

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		
		}catch(Exception er){
			this.msg="Khong The Cap Nhat Hang Cua Hang Nay ";
			this.db.update("rollback");
			return false;
		}
		return true;
	}

	public boolean UpdateHch() 
	{
		
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String command ="update hangcuahang set hang = N'" + this.hangcuahang + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
			
			if (!db.update(command)){
				//this.msg = "update hangcuahang set hang = '" + this.hangcuahang + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
				this.msg="Ban khong the luu duoc, can kiem tra lai du lieu";
				db.update("rollback");
				return false;
			}
			
			
			command = "delete from HANGCUAHANG_DOANHTHU where HCH_FK='" + this.id + "' ";
			db.update(command);
			command = "insert into HANGCUAHANG_DOANHTHU select '"+this.id+"','"+this.doanhthu_tu+"','"+this.doanhthu_den+"' ";
			//System.out.println("Day la ID: " + this.id);
			if (!db.update(command))
			{
				this.msg="Ban khong the luu duoc HANGCUAHANG_DOANHTHU";
				db.update("rollback");
				return false;
			}
			

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);

			
		} catch (SQLException e) {
			e.printStackTrace();
			this.msg="Khong The Cap Nhat Hang Cua Hang Nay ";
			this.db.update("rollback");
			return false;
			
		}
	
		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id, a.hang, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"; 
		query = query + " from hangcuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		query = query + " and a.pk_seq='"+ this.id + "'";
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.hangcuahang = rs.getString("hang");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	  	
       	}catch(Exception e){}
        query = "select * from HANGCUAHANG_DOANHTHU where HCH_FK='"+this.id+"' ";
        rs = this.db.get(query);
        try{
        	rs.next();
        	this.doanhthu_tu=rs.getString("DT_Tu");
        	this.doanhthu_den=rs.getString("DT_Den");
        }catch(Exception e){}
        
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
		
	public void closeDB(){
		if(this.db != null)
			this.db.shutDown();
	}

	@Override
	public void setDoanhThu_Tu(String doanhthu_tu) {
		// TODO Auto-generated method stub
		this.doanhthu_tu = doanhthu_tu;
	}

	@Override
	public void setDoanhThu_Den(String doanhthu_den) {
		// TODO Auto-generated method stub
		this.doanhthu_den = doanhthu_den;
	}

	@Override
	public String getDoanhThu_Tu() {
		// TODO Auto-generated method stub
		return this.doanhthu_tu;
	}

	@Override
	public String getDoanhThu_Den() {
		// TODO Auto-generated method stub
		return this.doanhthu_den;
	}

	@Override
	public boolean KiemTraHMDoanhThuTrung() {
		// TODO Auto-generated method stub
		boolean r = false;
		String query = "select * from HANGCUAHANG_DOANHTHU " 
				+ "where DT_Tu='" + this.doanhthu_tu + "' and DT_Den='" + this.doanhthu_den + "' "; 
		
		if(this.id.length()>0)
			query+=" and hch_fk!='"+this.id+"' ";
		
       System.out.println("truy van:"+query);
		try {
			if(this.db.get(query).next())
			{
				r = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
}
