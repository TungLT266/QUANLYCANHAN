package geso.traphaco.erp.beans.stockintransit.imp;

import geso.traphaco.erp.beans.stockintransit.IDataChart;

public class DataChart implements IDataChart 
{

	String nam;
	String thang;
	String data;
	
	public DataChart()
	{
		this.nam = "";
		this.thang = "";
		this.data = "0";
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

	public String getData() 
	{
		return this.data;
	}

	public void setData(String data) 
	{
		this.data = data;
	}

}
