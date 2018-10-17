package geso.traphaco.erp.beans.khoanmucchietkhau.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.khoanmucchietkhau.IKhoanmucCK;

public class KhoanmucCK implements IKhoanmucCK 
{
	String kenhbhId;
	String kenhbhTen;
	
	String taikhoanId;
	String taikhoanTen;
	String taikhoanSH;
	
	
	public KhoanmucCK()
	{
	  this.kenhbhId = "";
	  this.kenhbhTen = "";
	  
	  this.taikhoanId = "";
	  this.taikhoanTen = "";
	  this.taikhoanSH = "";
	}



	public String getKenhbhId() 
	{
		return this.kenhbhId;
	}



	public void setKenhbhId(String kenhbhId) 
	{
		this.kenhbhId = kenhbhId;
	}


	public String getKenhbhTen() 
	{
		return this.kenhbhTen;
	}

	public void setKenhbhTen(String kenhbhTen) 
	{
		this.kenhbhTen = kenhbhTen;
	}

	public String getTaikhoanId() 
	{
		return this.taikhoanId;
	}

	public void setTaikhoanId(String taikhoanId) 
	{
		this.taikhoanId = taikhoanId;
	}


	public String getTaikhoanTen() 
	{
		return this.taikhoanTen;
	}

	public void setTaikhoanTen(String taikhoanTen) 
	{
		this.taikhoanTen = taikhoanSH;
	}
	
	public String getTaikhoanSH() 
	{
		return this.taikhoanSH;
	}

	public void setTaikhoanSH(String taikhoanSH) 
	{
		this.taikhoanSH = taikhoanSH;
	}
	

}
