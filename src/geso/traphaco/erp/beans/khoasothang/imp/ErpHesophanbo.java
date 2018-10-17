package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khoasothang.IErpHesophanbo;

public class ErpHesophanbo implements IErpHesophanbo
{
	String userId;
	String thang;
	String nam;
	
	ResultSet giavonRs;
	ResultSet giavonCTRs;
	
	String msg;
	
	public ErpHesophanbo()
	{
		this.thang = Integer.toString( Integer.parseInt( getDateTime().split("-")[1] ) );
		this.nam = getDateTime().split("-")[0];
		this.msg = "";
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getThang()
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public String getNam()
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
	public void createRs() 
	{
		
	}

	public ResultSet getGiavonRs()
	{
		return this.giavonRs;
	}
	
	public ResultSet getGiavonCTRs() 
	{
		return this.giavonCTRs;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DbClose() 
	{
	}

	String congtyId;
	String nppId;
	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	public String getNppId() {

		return this.nppId;
	}

	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}


	
}
