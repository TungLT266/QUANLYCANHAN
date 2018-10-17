package geso.traphaco.erp.beans.xoanoncc.imp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import geso.traphaco.erp.beans.xoanoncc.IHoadon;

public class Hoadon implements IHoadon
{
	String id;
	String kyhieu;
	String so;
	String ngay;
	String aVat;
	String thanhtoan;
	String checked;
	String hopdong;
	String loaict;
	
	public Hoadon()
	{
		this.id = "";
		this.kyhieu = "";
		this.so = "";
		this.ngay = "";
		this.aVat = "";
		this.thanhtoan = "";
		this.checked = "";
		this.hopdong = "";
		this.loaict = "";
	}
	
	public Hoadon(String id, String kyhieu, String so, String ngay, String avat, String thanhtoan, String checked)
	{
		this.id = id;
		this.kyhieu = kyhieu;
		this.so = so;
		this.ngay = ngay;
		this.aVat = avat;
		this.thanhtoan = thanhtoan;
		this.checked = checked;
	}

	public String getKyhieu()
	{
		return this.kyhieu;
	}

	public void setKyhieu(String kyhieu) 
	{
		this.kyhieu = kyhieu;	
	}

	public String getSo()
	{
		return this.so;
	}

	public void setSo(String so) 
	{
		this.so = so;	
	}

	public String getNgay() 
	{
		return this.ngay;
	}

	public void setNgay(String ngay) 
	{
		this.ngay = ngay;
	}

	public String getTongtiencoVAT()
	{
		return this.aVat;
	}

	public void setTongtiencoVAT(String aVat) 
	{
		this.aVat = aVat;	
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
		String tt = this.thanhtoan;
		if(tt.length() <= 0)
			tt = "0";
		
		if(this.aVat.length() <= 0 && tt.length() <= 0)
			return "";
		
		if(this.aVat.length() > 0 && tt.length() > 0)
		{
			String tienVAT = this.aVat.replaceAll(",", "");
			String thanhtoan = tt.replaceAll(",", "");
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			return formatter.format(Math.round(Double.parseDouble(tienVAT) - Double.parseDouble(thanhtoan)));
		}
		
		return "";
	}

	public void setConlai(String conlai) 
	{

	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public int compareTo(IHoadon obj) 
	{
		return 0;
	}

	
	public String getHopdong() {
		
		return this.hopdong;
	}

	
	public void setHopdong(String hopdong) {
		
		this.hopdong = hopdong;
	}

	
	public String getLoaict() {
		
		return this.loaict;
	}

	
	public void setLoaict(String loaict) {
		
		this.loaict = loaict;
	}
	
}
