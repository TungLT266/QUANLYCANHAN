package geso.traphaco.erp.beans.vayvon.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import geso.traphaco.erp.beans.vayvon.IHoadon;

public class Hoadon implements IHoadon
{
	String id;
	String nccId;
	String nccTen;
	String sotienVND;
	String sotienNT;
	
	String thanhtoan;
	String conlai;
	String tygia;
	String checked;
	String tienteId;
	
	public Hoadon()
	{
		this.id = "";

		this.nccId = "";
		this.nccTen= "";
		this.sotienVND = "";
		this.sotienNT = "";
		this.thanhtoan = "";
		this.conlai = "";
		this.checked = "";
		this.tygia = "";
		this.tienteId = "";

		
	}
	
	public Hoadon(String id, String nccId, String nccTen, String sotienVND, String sotienNT, String thanhtoan,String conlai, String ttId,String tygia)
	{
		this.id = id;
		this.nccId = nccId;
		this.nccTen= nccTen;
		this.sotienVND = sotienVND;
		this.sotienNT = sotienNT;
		this.thanhtoan = thanhtoan;
		this.conlai= conlai;
		this.tygia = tygia;
		this.tienteId =ttId ;
	}

	public String getTienteId()
	{
		return this.tienteId;
	}

	public void setTienteId(String tienteId) 
	{
		this.tienteId = tienteId;	
	}

	public String getSotienVND()
	{
		return this.sotienVND;
	}

	public void setSotienVND(String sotienVND) 
	{
		this.sotienVND = sotienVND;	
	}


	public String getSotienNT()
	{
		return this.sotienNT;
	}

	public void setSotienNT(String sotienNT) 
	{
		this.sotienNT = sotienNT;	
	}

	public String getThanhtoan() 
	{
		return this.thanhtoan;
	}

	public void setThanhtoan(String thanhtoan)
	{
		this.thanhtoan = thanhtoan;
	}

	public String getTrahet()
	{
		return this.checked;
	}

	public void setTrahet(String trahet) 
	{
		this.checked = trahet;
	}

	public String getConlai()
	{
		return this.conlai;
	}


	public void setConlai(String conlai) 
	{
       this.conlai = conlai;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}


	public String getTygia() 
	{
		return this.tygia;
	}

	public void setTygia(String tygia) 
	{
		this.tygia = tygia;
	}

	public int compareTo(IHoadon arg0) 
	{
		return 0;
	}

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccId) 
	{
		this.nccId = nccId;
	}

	public String getNccTen() 
	{
		return this.nccTen;
	}

	public void setNccTen(String nccTen) 
	{
		this.nccTen = nccTen;
	}




}
