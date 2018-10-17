package geso.traphaco.center.beans.khuvuc.imp;

import geso.traphaco.center.beans.khuvuc.IKhuvuc;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Khuvuc implements IKhuvuc
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String vungmien;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String asm;
	String tenqg = "";
	ResultSet asms;
	
	ResultSet vungmienlist;
	String vmId;
	
	dbutils db;
	
	public Khuvuc(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.vungmien = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.vmId = param[8];
		this.diengiai = param[9];
		this.tenqg = param[10];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Khuvuc(String id)
	{
		this.id = id;
		this.ten = "";
		this.vungmien = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.vmId = "";
		this.asm ="";
		this.msg = "";
		this.db = new dbutils();
		if(id.length() > 0)
			this.init();
		else
			this.createVmRS();
		creasms();
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
		if (this.trangthai.equals("1")){
			return "Hoat dong";
		}
		return "Ngung hoat dong";
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
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public String getVmId() 
	{
		return this.vmId;
	}

	public void setVmId(String vmId)
	{
		this.vmId = vmId;
	}

	public ResultSet getVungMienlist() 
	{
		return this.vungmienlist;
	}

	public void setVungmienlist(ResultSet vungmienlist) 
	{
		this.vungmienlist = vungmienlist;
	}

	public void createVmRS() 
	{
		ResultSet vmRS = this.db.get("select pk_seq as vmId, ten as vmTen from vung where trangthai='1' order by ten");

		this.vungmienlist = vmRS;
	}
	
	public boolean CreateKv() 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String command="";
		if(this.asm==null || this.asm.equals("")){
		 command = "insert into khuvuc(ten, ngaytao, ngaysua, nguoitao, nguoisua, vung_fk, trangthai, diengiai)";
		 command = command + " values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao + "','" + this.nguoitao + "','" + this.vmId + "','1', N'" + this.diengiai + "')"; 
		}else{
			command = "insert into khuvuc(ten, ngaytao, ngaysua, nguoitao, nguoisua, vung_fk, trangthai, diengiai,asm_fk)";
			command = command + " values(N'" + this.ten + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao + "','" + this.nguoitao + "','" + this.vmId + "','1', N'" + this.diengiai + "','"+ this.asm +"')";
		}
		if (!db.update(command)){
			this.msg = command;
			db.update("rollback");
			return false;
		}
		

		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.db.update("rollback");
			this.msg="Khong the cap nhat lai bang nay,xay ra loi trong dong lenh sau "+ er.toString();
			return false;
		}
		return true;
	}

	public boolean UpdateKv() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		String command="";
		if(this.asm==null||this.asm.equals("")){
		 command ="update khuvuc set ten = N'" + this.ten + "', vung_fk='"+ this.vmId + "', diengiai=N'"+ this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId +  "' where pk_seq = '" + this.id + "'";
		}else{
			 command ="update khuvuc set ten = N'" + this.ten + "', vung_fk='"+ this.vmId + "', diengiai=N'"+ this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId +  "',asm_fk ='"+ this.asm +"' where pk_seq = '" + this.id + "'";
		}
		if (!db.update(command)){
			this.msg = command;			
			db.update("rollback");
			return false;
		}

		return true; 
	}
	
	private void init()
	{	
		String query = "select a.pk_seq as id, a.ten as kvTen, a.trangthai as trangthai, b.pk_seq as vmId, b.ten as vmTen, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, a.diengiai,a.asm_fk";
		query = query + " from khuvuc a, vung b, nhanvien c, nhanvien d";
		query = query + " where a.vung_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq and a.pk_seq = '" + this.id + "'";
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.ten = rs.getString("kvTen");
        	this.vungmien = rs.getString("vmTen");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.vmId = rs.getString("vmId");
        	this.diengiai = rs.getString("diengiai");
        	this.asm = rs.getString("asm_fk");
        	     	
       	}catch(Exception e){}
       	createVmRS(); 
       	if(this.vmId.length() > 0)
    	{
    		String sql = "Select tenqg from quocgia,vung where quocgia.pk_seq = vung.quocgia_fk and vung.pk_seq = "+this.vmId;
    		System.out.println("truy van "+sql);
    		ResultSet rs1 = db.get(sql);
    		try {
    			while (rs1.next())
    			{
    				setTenqg(rs1.getString("tenqg"));
    			}
    		} catch (SQLException e)
    		{
			
    			e.printStackTrace();
    		}
    	}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public void setAsm(String asm) {
		
		this.asm = asm;
	}

	
	public String getAsm() {
		
		return this.asm;
	}

	
	public void setAsms(ResultSet asms) {
		
		this.asms = asms;
	}

	
	public ResultSet getAsms() {
		
		return this.asms;
	}
	
    void creasms()
    {
    	String sql = "select * from asm ";
    	asms = db.get(sql);
    }
	
	public void closeDB(){
		try {
			if(asms!=null) {
				asms.close();
			}
			if(vungmienlist != null) {
				vungmienlist.close();
			}
		} catch(Exception e) {}
		if (this.db != null)
			this.db.shutDown();
	}

	@Override
	public String getTenqg() {
		// TODO Auto-generated method stub
		return this.tenqg;
	}

	@Override
	public void setTenqg(String tenqg) {
	this.tenqg = tenqg;
		
	}
	
	
}
