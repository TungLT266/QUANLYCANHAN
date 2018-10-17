package geso.traphaco.erp.beans.congtytrahang.imp;

import geso.traphaco.erp.beans.congtytrahang.ITiente;

public class Tiente implements ITiente
{
	String id;
	String ma;
	String tygia;
	
	public Tiente()
	{
		this.id = "";
		this.ma = "";
		this.tygia = "";
	}
	
	public Tiente(String id, String ma, String tygia)
	{
		this.id = id;
		this.ma = ma;
		this.tygia = tygia;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	public String getTygiaquydoi() 
	{
		return this.tygia;
	}

	public void setTygiaquydoi(String tygiaqd) 
	{
		this.tygia = tygiaqd;
	}

}
