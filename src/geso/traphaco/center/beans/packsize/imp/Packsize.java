package geso.traphaco.center.beans.packsize.imp;
import geso.traphaco.center.beans.packsize.IPacksize;
import geso.traphaco.center.db.sql.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Packsize implements IPacksize
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
	
	dbutils db;
	
	public Packsize(String[] param)
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
	
	public Packsize(String id)
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
		this.msg = "";
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
		
		String command = "insert into packsize(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua) values(N'" + this.kenhbanhang + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "')"; 
		
		if (!db.update(command)){
			this.msg = "Khong the tao moi kenh ban hang: " + command;		
			this.db.update("rollback");
			return false;
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
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String command ="update packsize set ten = N'" + this.kenhbanhang + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
		
		if (!this.db.update(command)){
			this.msg = "Khong the cap nhat: " + command;
			this.db.update("rollback");
			return false;
		}

		return true; 
	}

	private void init()
	{	
		String query = "select a.pk_seq as id, a.ten as kbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from packsize a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq= '"+ this.id + "'"; 
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
        	  	
       	}catch(Exception e){}
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
}
