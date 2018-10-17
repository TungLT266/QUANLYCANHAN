package geso.traphaco.erp.beans.khoasothang.imp;

import geso.traphaco.erp.beans.khoasothang.ISanpham;

public class Sanpham implements ISanpham 
{
	String pnkId;
	String sonhapkho;
	String ngaynhap;
	String ngaychot;
	String spId;
	String spMa;
	String spTen;
	String soluong;
	
	String giaNT;
	String tygiaQD;
	String giaOld;
	
	String giaNew;
	
	
	public Sanpham()
	{
		this.pnkId = "";
		this.sonhapkho = "";
		this.ngaynhap = "";
		this.ngaychot = "";
		this.spId = "";
		this.spMa = "";
		this.spTen = "";
		this.soluong = "";
		this.giaOld = "";
		
		this.giaNT = "";
		this.tygiaQD = "";

		this.giaNew = "";
	}
	
	public Sanpham(String pnkId, String spId, String spMa, String spten, String soluong, String giaOld, String giaNew)
	{
		this.pnkId = pnkId;
		this.spId = spId;
		this.spMa = spMa;
		this.spTen = spten;
		this.soluong = soluong;
		this.giaOld = giaOld;
		this.giaNew = giaNew;
		
		this.giaNT = "";
		this.tygiaQD = "";
		this.sonhapkho = "";
		this.ngaynhap = "";
	}

	public String getPnkId() 
	{
		return this.pnkId;
	}

	public void setPnkId(String pnkId)
	{
		this.pnkId = pnkId;
	}

	public String getSpId() 
	{
		return this.spId;
	}
	
	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public String getSpMa()
	{
		return this.spMa;
	}

	public void setSpMa(String spMa)
	{
		this.spMa = spMa;
	}

	public String getSpTen() 
	{
		return this.spTen;
	}

	public void setSpTen(String spTen) 
	{
		this.spTen = spTen;
	}

	public String getSoluong() 
	{
		return this.soluong;
	}

	public void setSoluong(String soluong)
	{
		this.soluong = soluong;
	}

	public String getGiaOld()
	{
		return this.giaOld;
	}

	public void setGiaOld(String giaOld) 
	{
		this.giaOld = giaOld;
	}

	public String getGiaNew() 
	{
		return this.giaNew;
	}

	public void setGiaNew(String giaNew)
	{
		this.giaNew = giaNew;
	}

	public String getSonhapkho() 
	{
		return this.sonhapkho;
	}

	public void setSonhapkho(String sonhapkho) 
	{
		this.sonhapkho = sonhapkho;
	}

	public String getNgaynhap() 
	{
		return this.ngaynhap;
	}

	public void setNgaynhap(String ngaynhap) 
	{
		this.ngaynhap = ngaynhap;
	}

	public String getGiaNgoaiTe() 
	{
		return this.giaNT;
	}

	public void setGiaNgoaiTe(String giaNT) 
	{
		this.giaNT = giaNT;
	}

	public String getTyGia() 
	{
		return this.tygiaQD;
	}

	public void setTyGia(String tygia) 
	{
		this.tygiaQD = tygia;
	}

	public String getNgaychot() 
	{
		return this.ngaychot;
	}

	public void setNgaychot(String ngaychot) 
	{
		this.ngaychot = ngaychot;
	}
	
}
