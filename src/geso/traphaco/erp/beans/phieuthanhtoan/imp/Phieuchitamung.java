package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.phieuthanhtoan.IHoadon;
import geso.traphaco.erp.beans.phieuthanhtoan.IPhieuchitamung;
import geso.traphaco.erp.beans.phieuthanhtoan.ISanpham;

public class Phieuchitamung implements IPhieuchitamung
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String sochungtu;
	String ngaychungtu;
	String noidungtt;
	String loaiHD;
	String sotienAvat;
	String sotienCantru;
	String conlai;
	String chon;
	
	public Phieuchitamung()
	{
		this.sochungtu = "";
		this.ngaychungtu ="";
		this.noidungtt ="";
		
		this.sotienAvat = "";
		this.sotienCantru = "";
		this.conlai = "";
		this.chon = "0";
	}

	public Phieuchitamung(String sochungtu, String ngaychungtu, String noidungtt, String sotienAvat, String sotienCantru, String conlai, String chon)
	{
		this.sochungtu = sochungtu;
		this.ngaychungtu = ngaychungtu;
		this.noidungtt = noidungtt;
		
		this.sotienAvat = sotienAvat;
		this.sotienCantru = sotienCantru;
		this.conlai = conlai;
		this.chon = chon;
	}

	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) 
	{
		this.sochungtu = sochungtu;
	}

	public String getSotienAvat() 
	{
		return this.sotienAvat;
	}

	public void setSotienAvat(String sotienAvat) 
	{
		this.sotienAvat = sotienAvat;
	}

	public String getSotienCantru() 
	{
		return this.sotienCantru;
	}

	public void setSotienCantru(String sotienCantru) 
	{
		this.sotienCantru = sotienCantru;
	}

	public String getNgaychungtu() 
	{
		return this.ngaychungtu ;
	}

	public void setNgaychungtu(String ngaychungtu) 
	{
		this.ngaychungtu = ngaychungtu;
	}

	public String getConlai() 
	{
		return this.conlai;
	}

	public void setConlai(String conlai) 
	{
		this.conlai = conlai;
	}

	public String getNoidungtt()
	{
		return this.noidungtt;
	}

	public void setNoidungtt(String noidungtt) 
	{
		this.noidungtt = noidungtt;
	}

	public String getChon() 
	{
		return this.chon;
	}

	public void setChon(String chon) 
	{
		this.chon = chon ;
		
	}
	public String getLoaiHD(){
		return this.loaiHD;
	}
	public void setLoaiHD(String loaiHD){
		this.loaiHD = loaiHD;
	}
	

}
