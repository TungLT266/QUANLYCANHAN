package geso.traphaco.erp.beans.donmuahangtrongnuoc.imp;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi;

public class Donvi implements IDonvi
{
	String id;
	String donvi;
	
	public Donvi()
	{
		this.id = "";
		this.donvi = "";
	}
	
	public Donvi(String id, String donvi)
	{
		this.id = id;
		this.donvi = donvi;
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
	
	
}
