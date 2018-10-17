package geso.traphaco.center.beans.mucchietkhau.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.mucchietkhau.IChietkhau;
import geso.traphaco.center.db.sql.dbutils;

public class Chietkhau implements IChietkhau
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String loai;
	String diengiai;
	
	String phantram_doanhthu;
	String gioihan;
	String tongsotien;
	String hanmucno;
	String songayno;
	String sotien;
	
	String msg;
	
	ResultSet nppRs;
	String nppIds;
	
	dbutils db;
	
	public Chietkhau(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.loai = param[1];
		this.diengiai = param[2];
		this.msg = "";
		this.nppIds = "";
		
		this.phantram_doanhthu = "";
		this.gioihan = "";
		this.tongsotien = "";
		this.hanmucno = "";
		this.songayno = "";
		this.sotien = "";
		
		this.db = new dbutils();
	}
	
	public Chietkhau(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.loai = "";
		this.diengiai = "";
		
		this.phantram_doanhthu = "";
		this.gioihan = "";
		this.tongsotien = "";
		this.hanmucno = "";
		this.songayno = "";
		this.sotien = "";
	
		this.msg = "";
		this.nppIds = "";
		this.diengiai = "";
		this.db = new dbutils();

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
	
	public String getSotien() 
	{
		return this.sotien;
	}

	public void setSotien(String sotien) 
	{
		this.sotien = sotien;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public boolean CreateMck()
	{
		
		return true;
	}

	public void init()
	{	
		NumberFormat formater = new DecimalFormat("#,###,###");
		String query =  "select  diengiai, loai, phantram_doanhthu, isnull(gioihan, 0) as gioihan, " +
						"	isnull(tongsotien, 0) as tongsotien, hanmucno, songayno, isnull(sotien, 0) as sotien " +
						"from CONGNO where pk_seq = '" + this.id + "'"; 
        ResultSet rs =  this.db.get(query);
        try
        {
            rs.next();        	
        	this.diengiai = rs.getString("diengiai");
        	this.loai = rs.getString("loai");
        	this.phantram_doanhthu = rs.getString("phantram_doanhthu");
        	this.gioihan = rs.getString("gioihan");
        	this.tongsotien = formater.format(rs.getDouble("tongsotien"));
        	this.hanmucno = rs.getString("hanmucno");
        	this.songayno = rs.getString("songayno");
        	this.sotien = formater.format(rs.getDouble("sotien"));
       	}
        catch (Exception e)
        {
        	e.printStackTrace();
        }
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

	public ResultSet getNppRs() 
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getNppIds() 
	{
		return this.nppIds;
	}

	public void setNppIds(String nppIds) 
	{
		this.nppIds = nppIds;
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public String getLoai() {
		
		return this.loai;
	}

	
	public void setLoai(String loai) {
		
		this.loai = loai;
	}

	
	public String getPhantramDS() {
		
		return this.phantram_doanhthu;
	}

	
	public void setPhantramDS(String phantramDS) {
		
		this.phantram_doanhthu = phantramDS;
	}

	
	public String getGioihan() {
		
		return this.gioihan;
	}

	
	public void setGioihan(String gioihan) {
		
		this.gioihan = gioihan;
	}

	
	public String getTongsotien() {
		
		return this.tongsotien;
	}

	
	public void setTongsotien(String tongsotien) {
		
		this.tongsotien = tongsotien;
	}

	
	public String getHanmucno() {
		
		return this.hanmucno;
	}

	
	public void setHanmucno(String hanmucno) {
		
		this.hanmucno = hanmucno;
	}

	
	public String getSongayno() {
		
		return this.songayno;
	}

	
	public void setSongayno(String songayno) {
		
		this.songayno = songayno;
	}

	
	public boolean Create() {
		
		return false;
	}

	
	public boolean Update() 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String command ="update CONGNO set diengiai = N'" + this.diengiai + "', phantram_doanhthu = '" + this.phantram_doanhthu + "', " +
							"	gioihan = '" + this.gioihan + "', tongsotien = '" + this.tongsotien + "', hanmucno = '" + this.hanmucno + "', " +
							"	songayno = '" + this.songayno + "', sotien = '" + this.sotien + "', nguoisua = '" + this.userId + "', ngaysua = '" + this.getDateTime() + "'  " +
							"where pk_seq = '" + this.id + "'";
			System.out.println("---CAP NHAT: " + command);
			
			if (!this.db.update(command))
			{
				this.msg = "Khong the cap nhat cong no: " + command;
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			return false;
		}

		return true; 
	}

	
	public String getMsg() {

		return this.msg;
	}

	public void setMsg(String msg) {
		
		this.msg = msg;
	}


	public void createRs()
	{
		
	}

	
	

}
