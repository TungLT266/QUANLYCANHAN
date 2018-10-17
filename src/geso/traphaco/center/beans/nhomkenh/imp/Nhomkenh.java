package geso.traphaco.center.beans.nhomkenh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.nhomkenh.INhomkenh;
import geso.traphaco.center.db.sql.dbutils;

public class Nhomkenh implements INhomkenh
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String nhomkenh;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String kbhId;
	ResultSet kbhRs; 
	dbutils db;
	
	public Nhomkenh(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.nhomkenh = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Nhomkenh(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.nhomkenh = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.kbhId = "";
		this.msg = "";
		this.db = new dbutils();
		if(id.length() > 0)
		{
			this.init();
			createKbhRs();
		}
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
	
	public String getNhomkenh() 
	{
		return this.nhomkenh;
	}

	public void setNhomkenh(String nhomkenh) 
	{
		this.nhomkenh = nhomkenh;
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

	public boolean CreateNk()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		String command = "insert into nhomkenh(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua) values(N'" + this.nhomkenh + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "')"; 
		
		if (!db.update(command)){
			this.msg = "Khong the tao moi kenh ban hang: " + command;		
			this.db.update("rollback");
			return false;
		}
		/*
		 * thuc hien cap nhat lai smartid
		 * 
		 */
		command = "select IDENT_CURRENT('nhomkenh')as id";
		ResultSet rs_getpk_seq = this.db.get(command);
		rs_getpk_seq.next();
		this.id = rs_getpk_seq.getString("id");
		
		if(this.kbhId.length()>0)
		{
				command="insert into nhomkenh_kenhbanhang(kbh_fk, nk_fk)" +
						" select pk_seq,'"+this.id+"'  " +
						" from kenhbanhang where pk_seq in ("+this.kbhId+")  ";
				if(!this.db.update(command))
				{
					this.msg="Lỗi hệ thống "+command;
					this.db.update("rollback");
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

	public boolean UpdateNk() 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String command ="update nhomkenh set ten = N'" + this.nhomkenh + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
		
		if (!this.db.update(command)){
			this.msg = "Khong the cap nhat: " + command;
			this.db.update("rollback");
			return false;
		}
		
		command="delete nhomkenh_kenhbanhang where nk_fk = '" + this.id +"'";
		System.out.println("xoa hethongbanhang_kenhbanhang:" + command);
		if(!this.db.update(command))
		{
			this.msg="Không thể cập nhật nhomkenh_kenhbanhang "+command;
			this.db.update("rollback");
			return false;
		}
		if(this.kbhId.length()>0)
		{
				command="insert into nhomkenh_kenhbanhang(kbh_fk, nk_fk)" +
						" select pk_seq,'"+this.id+"'  " +
						" from kenhbanhang where pk_seq in ("+this.kbhId+")  ";
				if(!this.db.update(command))
				{
					this.msg="Lỗi hệ thống "+command;
					this.db.update("rollback");
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
		String query = "select a.pk_seq as id, a.ten as nkTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from nhomkenh a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq= '"+ this.id + "'"; 
        ResultSet rs =  this.db.get(query);
        System.out.println("lay thong tin :" + query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.nhomkenh = rs.getString("nkTen");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	  	
       	}catch(Exception e){}
        
        if(this.id!=null && this.id.length()>0)
  		{
  			query="select nk_fk, kbh_fk from nhomkenh_kenhbanhang where  nk_fk='"+this.id+"'";
			rs=this.db.get(query);
		
			try
			{
  		      	while(rs.next())
  		      	{
  		      		this.kbhId+=rs.getString("kbh_fk")+",";
  		      	}
  		      	if(rs!=null)rs.close();
  		      	if(this.kbhId.length()>0)
  				{
  		      		this.kbhId=this.kbhId.substring(0, this.kbhId.length()-1);
  				}
  		      	System.out.println("kbhIds " + this.kbhId);
  		      
  	      	} catch (SQLException e)
  	      	{
  	      		e.printStackTrace();
  	      	}
  		}
        
        createKbhRs();
	}
	
	public void createKbhRs()
	{
		String query="select pk_seq,ten,diengiai from kenhbanhang order by ten";
		this.kbhRs=this.db.get(query);
		System.out.println("kenh ban hang " + query);
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
	public String getKbhId() {
		// TODO Auto-generated method stub
		return this.kbhId;
	}

	@Override
	public void setKbhId(String kbhId) {
		// TODO Auto-generated method stub
		this.kbhId = kbhId;
	}

	@Override
	public ResultSet getKbhRs() {
		// TODO Auto-generated method stub
		return this.kbhRs;
	}

	@Override
	public void setKbhRs(ResultSet kbhRs) {
		// TODO Auto-generated method stub
		this.kbhRs = kbhRs;
	}
}
