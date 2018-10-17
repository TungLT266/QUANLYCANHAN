package geso.traphaco.center.beans.vung.imp;

import geso.traphaco.center.beans.vung.IVungmien;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Vungmien implements IVungmien
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String vungmien;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String bm;
	ResultSet bms;
	ResultSet qg;
	String maqg = "";
	
	dbutils db;
	
	public Vungmien(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.vungmien = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.msg = "";
		creatBm();
	}
	
	public Vungmien(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.vungmien = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.bm ="";
		this.msg = "";
		if(id.length() > 0)
		
			this.init();
		creatBm();
		creatqg();
		
	}
	public void creatqg()
	{
		String sql = "select * from quocgia";
       	qg = db.get(sql);
	}
	public void creatBm()
	{
		String sql = "select * from bm";
       	bms = db.get(sql);
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
	
	public String getVungmien() 
	{
		return this.vungmien;
	}

	public void setVungmien(String vungmien) 
	{
		this.vungmien = vungmien;
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

	public boolean CreateVm()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String command="";
		if(this.bm==null || this.bm.equals("")){
			 command = "insert into vung(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua,quocgia_fk) values(N'" + this.vungmien + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "',"+this.maqg+")"; 
		}else{
					 command = "insert into vung(ten, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua,bm_fk,quocgia_fk) values(N'" + this.vungmien + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "','"+ this.bm +"',"+this.maqg+")"; 
		}
		
		if (!this.db.update(command)){
			this.msg = command;		
			this.db.update("rollback");
			return false;
		}
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.db.update("rollback");
			this.msg="khong the cap nhat vung nay,loi tai dong lenh sau :" + er.toString();
			return false;
		}
		return true;
	}

	public boolean UpdateVm() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		String command;
		if(this.bm==null || this.bm.equals("")){
			 command ="update vung set ten = N'" + this.vungmien + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "',quocgia_fk ='"+this.maqg+"' where pk_seq = '" + this.id + "'";
		}else{
		 command ="update vung set ten = N'" + this.vungmien + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' ,bm_fk = '"+ this.bm +"', quocgia_fk ='"+this.maqg+"' where pk_seq = '" + this.id + "'";
		}
		if (!this.db.update(command)){
			this.msg = "update vung set ten = N'" + this.vungmien + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";			
			this.db.update("rollback");
			return false;
		}

		return true; 
	}

	private void init()
	{	
		
		String query = "select a.pk_seq as id, a.ten, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,a.bm_fk,a.quocgia_fk"; 
		query = query + " from vung a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		query = query + " and a.pk_seq='"+ this.id + "'";
		System.out.println("cau lenh "+query);
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.vungmien = rs.getString("ten");
        	this.diengiai = rs.getString("diengiai");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.bm = rs.getString("bm_fk");
        	this.maqg = rs.getString("quocgia_fk");
       	}catch(Exception e){}
       	
       
       	
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
	
	public void closeDB(){
		try {
			if(bms != null) {
				bms.close();
			}
		} catch(Exception e) {}
		
		if (this.db != null)
			this.db.shutDown();
	}

	public void setBm(String bm) {
		
		this.bm = bm;
	}

	public String getBm() {
		
		return this.bm;
	}

	
	public void setBms(ResultSet bms) {
		
		this.bms = bms;
	}

	public ResultSet getBms() {
		return this.bms;
	}

	@Override
	public void setQuocgia(ResultSet qg) {
	this.qg = qg;
		
	}

	@Override
	public ResultSet getQuocgia() {
		// TODO Auto-generated method stub
		return this.qg;
	}

	@Override
	public void setMaQuocgia(String maqg) {
		this.maqg = maqg;
		
	}

	@Override
	public String getMaQuocGia() {
		// TODO Auto-generated method stub
		return this.maqg;
	}
	
	
}

