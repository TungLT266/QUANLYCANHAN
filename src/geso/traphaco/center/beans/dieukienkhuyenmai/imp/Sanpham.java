package geso.traphaco.center.beans.dieukienkhuyenmai.imp;

import geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham;

 
public class Sanpham implements ISanpham
{	
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String masp;
	String tensp;
	String soluong;
	String dongia;
	String batbuoc;
	
	String thuongSS;
	String thuongTDSS;
	String thuongSR;
	String thuongTDSR;
	String thuongASM;
	String thuongTDASM;
	
	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.dongia = "";
		this.batbuoc = "0";
		
		this.thuongSS = "";
		this.thuongTDSS = "";
		this.thuongSR = "";
		this.thuongTDSR = "";
		this.thuongASM = "";
		this.thuongTDASM = "";
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.dongia = param[4];
		this.batbuoc = "0";
		
		this.thuongSS = "";
		this.thuongTDSS = "";
		this.thuongSR = "";
		this.thuongTDSR = "";
		this.thuongASM = "";
		this.thuongTDASM = "";
	}
	
	public Sanpham(String spId, String spMa, String spTen, String soluong, String dongia)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.dongia = dongia;
		this.batbuoc = "0";
		
		this.thuongSS = "";
		this.thuongTDSS = "";
		this.thuongSR = "";
		this.thuongTDSR = "";
		this.thuongASM = "";
		this.thuongTDASM = "";
	}
	
	public String getId() 
	{	
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;	
	}
	
	public String getMasanpham()
	{	
		return this.masp;
	}
	
	public void setMasanpham(String masp)
	{
		this.masp = masp;		
	}
	
	public String getTensanpham() 
	{	
		return this.tensp;
	}
	
	public void setTensanpham(String tensp) 
	{
		this.tensp = tensp;	
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public String getDongia()
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	
	public String getThuongSS() 
	{
		return this.thuongSS;
	}

	public void setThuongSS(String thuongSS) 
	{
		this.thuongSS = thuongSS;
	}

	public String getThuongTDSS() 
	{
		return this.thuongTDSS;
	}

	public void setThuongTDSS(String thuongTDSS) 
	{
		this.thuongTDSS = thuongTDSS;
	}

	public String getThuongSR()
	{
		return this.thuongSR;
	}

	public void setThuongSR(String thuongSR) 
	{
		this.thuongSR = thuongSR;
	}

	public String getThuongTDSR() 
	{
		return this.thuongTDSR;
	}

	public void setThuongTDSR(String thuongTDSR)
	{
		this.thuongTDSR = thuongTDSR;
	}

	public String getThuongASM() 
	{
		return this.thuongASM;
	}

	public void setThuongASM(String thuongASM) 
	{
		this.thuongASM = thuongASM;
	}

	public String getThuongTDASM()
	{
		return this.thuongTDASM;
	}

	public void setThuongTDASM(String thuongTDASM) 
	{
		this.thuongTDASM = thuongTDASM;
	}

	public String getBatbuoc()
	{
		return this.batbuoc;
	}

	public void setBatbuoc(String batbuoc) 
	{
		this.batbuoc = batbuoc;
	}
	
}

