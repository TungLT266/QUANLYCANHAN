package geso.traphaco.center.beans.donvidoluong.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.donvidoluong.IDonvidoluong;
import geso.traphaco.center.db.sql.*;
import geso.traphaco.erp.util.UtilitySyn;


public class Donvidoluong implements IDonvidoluong
{
	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String donvi;
	String diengiai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String msg;
	String congty_fk;
	
	dbutils db;
	
	public Donvidoluong(String[] param)
	{
		this.id = param[0];
		this.donvi = param[1];	
		this.diengiai = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];
		this.trangthai = param[7];	
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Donvidoluong()
	{
		this.id = "";
		this.donvi = "";
		this.diengiai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "";	
		this.msg = "";
		this.congty_fk = "";
		this.db = new dbutils();		
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDonvi()
	{
		return this.donvi;
	}

	public void setDonvi(String donvi)
	{
		this.donvi = donvi;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public String getCongty_fk() {
		return congty_fk;
	}

	public void setCongty_fk(String congty_fk) {
		this.congty_fk = congty_fk;
	}

	
	public void init(){
		String query =  "select donvi, diengiai, trangthai from donvidoluong where pk_seq = '"+ this.id +"'";
        ResultSet rs =  this.db.get(query);
        try{
        	rs.next();
        	this.donvi = rs.getString("donvi");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	
        }catch(Exception e){}
        
	}
	public boolean saveNewDvdl(){
		try{
			boolean kt = KiemTraTrungTen(this.donvi, 0);
			if(!kt)
			{
				this.msg = "Ngành hàng đã tồn tại, vui lòng kiểm tra lại!";
				return kt;
			}
			this.db.getConnection().setAutoCommit(false);
		    // Insert data set into table "Donvidoluong"
		    String query ="insert into donvidoluong(donvi,diengiai,trangthai,ngaytao,ngaysua,nguoitao,nguoisua, congty_fk) values(N'" + this.donvi + "',N'" + this.diengiai + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.congty_fk + "')";
		    if (!this.db.update(query)){
			this.db.update("rollback");
			this.msg = "Khong the luu vao table 'donvidoluong'";
			return false;			
		    }
		    //Upp date Lai Smart ID
				query = "select IDENT_CURRENT('donvidoluong') as id";
				ResultSet rs = this.db.get(query);
	 			rs.next();
	 			this.id = rs.getString("id");
	 			String sql_update_smartid="update donvidoluong set smartid='"+this.id+"' where pk_seq=" + this.id;
	 			try{
	 			if(!db.update(sql_update_smartid)){
	 				 db.update("rollback");
				    System.out.println("Khong The Thuc Hien Update SmartId vao bang  Don Vi Do Luong  Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
				     return false;
	 			}
	 			}catch(Exception er){
	 				er.printStackTrace();
	 			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "DONVIDOLUONG", "DONVIDOLUONG", "PK_SEQ", id, "0", false);
			
		}catch(Exception er){
			this.db.update("rollback");
			return false;
		}
		return true;
	}

	private boolean KiemTraTrungTen(String ten, int loai)
	{
		try
		{
			String query = "select COUNT(donvi) num from donvidoluong where donvi = N'"+ten+"'";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				int num = rs.getInt("num");
				if(loai == 0 && num > 0)
					return false;
				if(loai == 1 && num > 1)
					return false;
			}rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean UpdateDvdl(){
		try{
			boolean kt = KiemTraTrungTen(this.donvi, 0);
			if(!kt)
			{
				this.msg = "Ngành hàng đã tồn tại, vui lòng kiểm tra lại!";
				return kt;
			}
			this.db.getConnection().setAutoCommit(false);
			// Update table "Donvidoluong"
			String query = "update donvidoluong set donvi = N'" + this.donvi + "',  diengiai = N'" + this.diengiai + "', ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "' where pk_seq = '" + this.id + "'" ;
	
			if(!this.db.update(query)){
				this.db.update("rollback");
				this.msg = "Khong the cap nhat table 'Donvidoluong'";
				return false; 
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			//SYN QUA DMS
			UtilitySyn.SynData(db, "DONVIDOLUONG", "DONVIDOLUONG", "PK_SEQ", id, "1", false);
		}catch(Exception er){
			this.db.update("rollback");
			return false;
		}
		return true;
	}
		
	public void DBClose(){
		this.db.shutDown();

	}
	
}


