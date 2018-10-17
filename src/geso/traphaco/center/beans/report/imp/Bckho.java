package geso.traphaco.center.beans.report.imp;

import geso.traphaco.center.beans.report.IBckho;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bckho extends Phan_Trang implements IBckho, Serializable
{
	private static final long serialVersionUID = 1L;
	
	String userId;
	String userTen;
	
	String nhomkenhId;
	ResultSet nhomkenhRs;
	
	String loaikhoId;
	ResultSet loaikhoRs;
	
	String khoId;
	ResultSet khoRs;
	
	String doituongId;
	ResultSet doituongRs;
	
	String laytatca;
	String laysolo;
	
	String msg;
	
	dbutils db;
	
	public Bckho()
	{
		this.userId = "";
		
		this.nhomkenhId = "";
		this.loaikhoId = "";
		
		this.doituongId = "";
		this.loaikhoId = "";
		
		this.laytatca = "0";
		this.laysolo = "";
		
		this.msg = "";
		this.khoId = "";
		
		db = new dbutils();
	}

	
	public String getNhomkenhId() {
		
		return this.nhomkenhId;
	}

	
	public void setNhomkenhId(String nhomkenhId) {
		
		this.nhomkenhId = nhomkenhId;
	}

	
	public ResultSet getNhomkenhRs() {
		
		return this.nhomkenhRs;
	}

	
	public void setNhomkenhRs(ResultSet nhomkenhRs) {
		
		this.nhomkenhRs = nhomkenhRs;
	}

	
	public String getKhoId() {
		
		return this.khoId;
	}

	
	public void setKhoId(String khoId) {
		
		this.khoId = khoId;
	}

	
	public ResultSet getKhoRs() {
		
		return this.khoRs;
	}

	
	public void setKhoRs(ResultSet khoRs) {
		
		this.khoRs = khoRs;
	}

	
	public void setLoaikhoRs(ResultSet loaikhoRs) {
		
		this.loaikhoRs = loaikhoRs;
	}

	
	public ResultSet getLoaikhoRs() {
		
		return this.loaikhoRs;
	}

	
	public String getLoaikho() {
		
		return this.loaikhoId;
	}

	
	public void setLoaikho(String loaikho) {
		
		this.loaikhoId = loaikho;
	}

	
	public String getDoituongId() {
		
		return this.doituongId;
	}

	
	public void setDoituongId(String doituongId) {
		
		this.doituongId = doituongId;
	}

	
	public ResultSet getDoituongRs() {
		
		return this.doituongRs;
	}

	
	public void setDoituongRs(ResultSet doituongRs) {
		
		this.doituongRs = doituongRs;
	}

	
	public String getLaytatca() {
		
		return this.laytatca;
	}

	
	public void setLaytatca(String laytatca) {
		
		this.laytatca = laytatca;
	}

	
	public String getLaysolo() {
		
		return this.laysolo;
	}

	
	public void setLaysolo(String laysolo) {
		
		this.laysolo = laysolo;
	}

	public String getUserId() {

		return this.userId;
	}


	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getUserTen() {
		
		return this.userTen;
	}


	public void setUserTen(String userTen) {
		
		this.userTen = userTen;
	}



	public String getMsg() {

		return this.msg;
	}



	public void setMsg(String msg) {

		this.msg = msg;
	}
	
	public void createRs() 
	{
		try
		{
			Utility util = new Utility();
			this.doituongId = "";
			
			String query =   " select 1  as pk_seq, N'Kho hàng bán' AS DIENGIAI  UNION ALL "+
							 " select 2  as pk_seq, N'Kho hàng ký gửi tại NCC' AS DIENGIAI  UNION ALL "+
							 " select 3  as pk_seq, N'Kho hàng hỏng(kho biệt trữ)' AS DIENGIAI  UNION ALL "+
							 " select 4  as pk_seq, N'Kho vật tư' AS DIENGIAI  UNION ALL "+
							 " select 5  as pk_seq, N'Kho dữ trữ khách hàng' AS DIENGIAI  UNION ALL "+
							 " select 6  as pk_seq, N'Kho trình duyệt viên' AS DIENGIAI  UNION ALL "+
							 " select 7  as pk_seq, N'Kho của nhà cung cấp' AS DIENGIAI  UNION ALL "+
							 " select 8  as pk_seq, N'Kho khách hàng ký gửi' AS DIENGIAI  UNION ALL "+
							 " select 9  as pk_seq, N'Kho khách hàng ký gửi tại công ty' AS DIENGIAI  UNION ALL "+
							 " select 10  as pk_seq, N'Kho hàng thu hồi(Đổi)' AS DIENGIAI  ";
			
			this.loaikhoRs = this.db.get(query);
			
			this.nhomkenhRs = db.get("select pk_seq,ten,diengiai from NHOMKENH");
			
			if(this.loaikhoId.equals("8"))
			{
				String nppId = util.getIdNhapp(this.userId);
				
				query = " select pk_seq, ma + ', ' + ten as ten from KHACHHANG where trangthai = '1' and npp_fk = '" + nppId + "' ";
				this.doituongRs = db.get(query);
			}
			
			query = "select * from kho where trangthai = 1 and pk_seq in " + util.quyen_kho( this.userId );
			if(this.loaikhoId.trim().length() > 0)
			{
				query += " and loai = "+this.loaikhoId;
			}
			this.khoRs = db.get(query);
	
			System.out.println("toiday");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void DBclose() 
	{
		try
		{
			this.db.shutDown();
			
			if(this.nhomkenhRs != null)
			{
				this.nhomkenhRs.close();
				this.nhomkenhRs = null;
			}
			
			if(this.doituongRs != null)
			{
				this.doituongRs.close();
				this.doituongRs = null;
			}
		}
		catch(Exception e){}
	}


	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
}
