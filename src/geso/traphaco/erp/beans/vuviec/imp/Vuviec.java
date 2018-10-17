package geso.traphaco.erp.beans.vuviec.imp;

import geso.traphaco.erp.beans.vuviec.IVuviec;
import geso.traphaco.distributor.db.sql.dbutils;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Vuviec implements IVuviec
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ma;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String congty;
	
	dbutils db;
	
	public Vuviec(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.ma = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.congty =  param[8];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Vuviec(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.congty = "";
		this.db = new dbutils();
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
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
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
		
		String command = "insert into VUVIEC(ma, ten, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, congty_fk) values(N'" + this.ma + "',N'" + this.diengiai + "','0','" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "', "+this.congty+")"; 
		
		if (!db.update(command)){
			this.msg = "Không thể tạo mới danh mục phế liệu này: " + command;		
			this.db.update("rollback");
			return false;
		}
		
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.msg="Không thể thêm Vụ việc này , Lỗi dòng lệnh sau :" + er.toString();
			this.db.update("rollback");
			return false;
		}
		return true;
	}

	public boolean UpdateKbh() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String command ="update VUVIEC set ma = N'" + this.ma + "', ten = N'" + this.diengiai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', congty_fk = "+this.congty+" where pk_seq = '" + this.id + "'";
		
		if (!this.db.update(command)){
			this.msg = "Khong the cap nhat: " + command;
			this.db.update("rollback");
			return false;
		}

		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id, a.ten as vvTen, a.ma, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"
				     + " from VUVIEC a, nhanvien b, nhanvien c "
		             + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq= '"+ this.id + "'"; 
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.ma = rs.getString("ma");
        	this.diengiai = rs.getString("vvTen");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	  	
       	}catch (java.sql.SQLException e){}
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

	
	public String getCongty() {
		
		return this.congty;
	}

	
	public void setCongty(String congty) {
		
		this.congty = congty;
	}
}
