package geso.traphaco.erp.beans.dutoanvattu.imp;

import geso.traphaco.erp.beans.dutoanvattu.IKho;

public class Kho implements IKho 
{
	String id;
	String makho;
	
	public Kho()
	{
		this.id = "";
		this.makho = "";
	}
	
	public Kho(String id, String makho)
	{
		this.id = id;
		this.makho = makho;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMakho() 
	{
		return this.makho;
	}

	public void setMakho(String makho) 
	{
		this.makho = makho;
	}

}
