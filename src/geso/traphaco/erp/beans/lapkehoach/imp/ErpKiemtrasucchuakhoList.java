package geso.traphaco.erp.beans.lapkehoach.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lapkehoach.IErpKiemtrasucchuakhoList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpKiemtrasucchuakhoList implements IErpKiemtrasucchuakhoList 
{
	String ctyId;
	String dvkdId;
	String userId;
	String trangthai; 

	String diengiai;
	String msg;
	String nam;
	String thang;
	String khoId;
	ResultSet khoRs;
	dbutils db;
	
	public ErpKiemtrasucchuakhoList()
	{
		this.ctyId = "";
		this.dvkdId = "";
		this.userId = "";
		this.khoId = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + (Integer.parseInt(this.getDateTime().substring(5, 7)) );
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;	
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;	
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		try
		{
			String query = 	"SELECT KHOTT.PK_SEQ AS ID, KHOTT.TEN AS KHO \n " +
							"FROM ERP_KHOTT KHOTT \n " +
							"WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.ctyId + " " +
							"AND KHOTT.PK_SEQ IN (100048, 100049)";
			System.out.println(query);
			this.khoRs = this.db.getScrol(query );
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception YC: " + e.getMessage() );
		}
	}

	
	
	public String getKhoId(){
		return this.khoId;
	}
	
	public void setKhoId(String khoId){
		this.khoId = khoId;
	}


	public ResultSet getSucchuakho_Ngay(String ngay, String khoId){
		String query = 	"SELECT SUCCHUA " +
						"FROM ERP_KHOTT " +
						"WHERE PK_SEQ = " + khoId ;
		
	
		//System.out.println(query);
		return this.db.get(query);
	}
	
	public ResultSet getKho(){
		String query = 	"SELECT KHOTT.PK_SEQ AS ID, KHOTT.TEN AS KHO \n " +
						"FROM ERP_KHOTT KHOTT \n " +
						"WHERE KHOTT.PK_SEQ IN (100048, 100049) AND TRANGTHAI = '1' AND CONGTY_FK = " + this.ctyId + " " ;
		if(this.khoId.length() > 0){
			query += " AND KHOTT.PK_SEQ = " + this.khoId;
		}
						
		return this.db.get(query);
	}
	public void DbClose() 
	{
		try 
		{
			if(this.khoRs != null) this.khoRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public ResultSet getKhoRs() 
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs) 
	{
		this.khoRs = khoRs;
	}

	public int getSongay(){
		int n  = 30;
		if(this.thang.equals("1") || this.thang.equals("3") || this.thang.equals("5") || this.thang.equals("7") || this.thang.equals("8") || this.thang.equals("10") || this.thang.equals("12")){
			n = 31;
		}
		
		if(this.thang.equals("4") || this.thang.equals("6") || this.thang.equals("9") || this.thang.equals("11")){
			n = 30;
		}
		
		if(this.thang.equals("2")){
			float m = Float.parseFloat(this.nam)/4;
			if(Long.parseLong(this.nam) / 4 == m){
				n = 29;
			}else{
				n = 28;
			}
			
		}
		
		return n;
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
