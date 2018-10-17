package geso.traphaco.center.beans.nhanhang.imp;

import geso.traphaco.center.beans.nhanhang.INhanhang;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;


public class Nhanhang implements INhanhang
{
	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String nhanhang;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String dvkdId;
	String dvkd;
	ResultSet dvkdlist;
	String msg;
	dbutils db;

	public Nhanhang(String[] param)
	{
		this.id = param[0];
		this.nhanhang = param[1];	
		this.ngaytao = param[2];
		this.ngaysua = param[3];
		this.nguoitao = param[4];
		this.nguoisua = param[5];
		this.trangthai = param[6];	
		this.dvkd = param[7];
		this.dvkdId = param[8];
		this.msg = "";
		this.db = new dbutils();
		createDvkdList();
	}
	
	public Nhanhang()
	{

		this.id = "";
		this.nhanhang = "";	
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "2";	
		this.dvkd = "";
		this.dvkdId = "";
		this.msg = "";
		this.db = new dbutils();
		createDvkdList();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNhanhang()
	{
		return this.nhanhang;
	}

	public void setNhanhang(String nhanhang)
	{
		this.nhanhang = nhanhang;
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

	public String getDvkdId()
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	
	public String getDvkd()
	{
		return this.dvkd;
	}

	public void setDvkd(String dvkd)
	{
		this.dvkd = dvkd;
	}

	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void setDvkdList(ResultSet dvkdlist)
	{
		this.dvkdlist = dvkdlist;
	}

	public ResultSet getDvkdList()
	{ 		
		return this.dvkdlist;
		
	}
	
	public boolean saveNewNhanhang(){
		try{
		// Insert data set into table "Nhanhang"
			this.db.getConnection().setAutoCommit(false);
		 	String query ="insert into nhanhang(ten,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,dvkd_fk) "+ 
		 	" values(N'" + this.nhanhang + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.dvkdId + "')";
		
		 	if (!db.update(query)){
		 		db.update("rollback");
		 		this.msg = "Khong the luu vao table 'nhanhang'" + this.nhanhang + ", " + this.trangthai + ", " + this.ngaytao + ", " + this.ngaysua + ", " + this.nguoitao + ", " + this.nguoisua +"";
		 		return false;			
		 	}
		 	/*
		 	 /*
			 * thuc hien cap nhat lai smartid
			 * 
			 */
			String query_getid = "select IDENT_CURRENT('nhanhang') as id";
			ResultSet rs_getpk_seq = this.db.get(query_getid);
			rs_getpk_seq.next();
			this.id = rs_getpk_seq.getString("id");
			
				String sql_update_smartid="update nhanhang set smartid='"+this.id+"' where pk_seq=" + this.id;
				try{
					if(!db.update(sql_update_smartid)){
						db.update("rollback");
						System.out.println("khong cap nhat smartid cho bang nhanhang ");
						return false;
					}
				}catch(Exception er){
				
				}
			
		 	
		 	this.db.getConnection().commit();
		 	this.db.getConnection().setAutoCommit(true);
		
		}catch(Exception er){
			this.msg="Khong the Luu Vao Bang Nhan Hang,Vui Long Lien He Voi Admin De Sua Loi Nay";
			this.db.update("rollback");
			
		}
		return true;
	}
	
	public boolean UpdateNhanhang(){
		// Update table "Nhanhang"
		String query = "update nhanhang set ten = N'" + this.nhanhang + "',  ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "' , dvkd_fk = '" + this.dvkdId + "' where pk_seq = '" + this.id + "'" ;

		if(!db.update(query)){
			db.update("rollback");
			this.msg = "Khong the cap nhat table 'Nhanhang'";
			return false; 
		}
		return true;
	}

	private void createDvkdList(){//chi cho load don vi kinh doanh nao co checked=1
		//this.dvkdlist =  db.get("select distinct pk_seq, donvikinhdoanh from donvikinhdoanh order by donvikinhdoanh");
		this.dvkdlist =  db.get("select distinct a.pk_seq, a.donvikinhdoanh from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}
	
	public boolean AllowtoChangeDvkd(){
		ResultSet rs = db.get("select count(pk_seq) as num from sanpham where nhanhang_fk='" + this.id + "' and dvkd_fk = '" + this.dvkdId + "'");
		try{
			rs.next();
			if (rs.getString("num").equals("0"))
				return true;
			else 
				return false;
		}catch(Exception e){
			return false;
		}		
	
	}
	public void DBClose(){
		try{
			if(this.dvkdlist != null) this.dvkdlist.close();
			this.db.shutDown();
		}catch(Exception e){}
	}
		
}


