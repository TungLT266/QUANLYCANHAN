package geso.traphaco.erp.beans.nhapkho.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.nhapkho.ISanpham;
import geso.traphaco.erp.beans.nhapkho.ISanphamCon;

public class Sanpham implements ISanpham
{
	String id;
	String masp;
	String soluongnhan;
	String soluongnhapkho;
	String solo;
	String diengiai;
	String ngayhethan;
	
	String donvitinh;
	String quycach;
	String thung;
	String le;
	String thetich;
	String trongluong;
	String dongia;
	
	String tiente;
	String dongiaViet;
	String nhapkhoId,ngaysanxuat,ngaynhapkho;
	
	List<ISanphamCon> spConList;

	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.diengiai = "";
		this.solo = "";
		this.soluongnhan = "";
		this.soluongnhapkho = "";
		this.ngayhethan = "";
		
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.thetich = "";
		this.trongluong = "";
		this.dongia = "";
		this.tiente = "";
		this.dongiaViet = "";
		this.nhapkhoId="";
		this.ngaysanxuat="";
		this.ngaynhapkho="";
		this.spConList = new ArrayList<ISanphamCon>();
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.diengiai = param[2];
		this.solo = param[3];
		this.soluongnhan = param[4];
		this.soluongnhapkho = param[5];
		
		this.ngayhethan = "";
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.thetich = "";
		this.trongluong = "";
		this.dongia = "";
		this.tiente = "";
		this.dongiaViet = "";
		this.nhapkhoId="";
		this.ngaysanxuat="";
		this.ngaynhapkho="";
		this.spConList = new ArrayList<ISanphamCon>();
	}
	
	public Sanpham(String id, String masp, String diengiai, String solo, String soluongnhan, String soluongnhap)
	{
		this.id = id;
		this.masp = masp;
		this.diengiai = diengiai;
		this.solo = solo;
		this.soluongnhan = soluongnhan;
		this.soluongnhapkho = soluongnhap;
		
		this.donvitinh = "";
		this.quycach = "";
		this.thung = "";
		this.le = "";
		this.thetich = "";
		this.trongluong = "";
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
		return this.masp;
	}

	public void setMa(String masp) 
	{
		this.masp = masp;
	}
	public String getSolo()
	{
		return this.solo;
	}

	public void setSolo(String solo)
	{
		this.solo = solo;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getSoluongnhan()
	{
		return this.soluongnhan;
	}

	public void setSoluongnhan(String soluongnhan)
	{
		this.soluongnhan = soluongnhan;
	}

	public String getSoluongnhapkho()
	{
		return this.soluongnhapkho;
	}

	public void setSoluongnhapkho(String soluongnhap) 
	{
		this.soluongnhapkho = soluongnhap;
	}

	public List<ISanphamCon> getSpConList()
	{
		return this.spConList;
	}

	public void setSpConList(List<ISanphamCon> spConList)
	{
		this.spConList = spConList;
	}
	
	public String getDVT() 
	{
		return this.donvitinh;
	}

	public void setDVT(String dvt)
	{
		this.donvitinh = dvt;
	}

	public String getQuycach()
	{
		return this.quycach;
	}

	public void setQuycach(String quycach)
	{
		this.quycach = quycach;
	}

	public String getThung() 
	{
		return this.thung;
	}

	public void setThung(String thung)
	{
		this.thung = thung;
	}

	public String getLe() 
	{
		return this.le;
	}

	public void setLe(String le) 
	{
		this.le = le;
	}

	public String getTrongluong() 
	{
		return this.trongluong;
	}

	public void setTrongluong(String trongluong)
	{
		this.trongluong = trongluong;
	}

	public String getThetich() 
	{
		return this.thetich;
	}

	public void setThetich(String thetich) 
	{
		this.thetich = thetich;
	}

	public String getNgayhethan() 
	{
		return this.ngayhethan;
	}

	public void setNgayhethan(String ngayhethan) 
	{
		this.ngayhethan = ngayhethan;
	}

	public String getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String dongia)
	{
		this.dongia = dongia;
	}
	
	public String getTiente()
	{
		return this.tiente;
	}

	public void setTiente(String tiente) 
	{
		this.tiente = tiente;
	}
	
	public String getDongiaViet() 
	{
		return this.dongiaViet;
	}

	public void setDongiaViet(String dongiaViet) 
	{
		this.dongiaViet = dongiaViet;
	}

	
	public String getNhapKhoId()
	{
		
		return this.nhapkhoId;
	}

	
	public void setNhapKhoId(String nhapkhoId)
	{
		this.nhapkhoId=nhapkhoId;
	}

	
	public String getNgayNhapKho()
	{
		
		return this.ngaynhapkho;
	}

	
	public void setNgayNhapKho(String ngaynhapkho)
	{
		this.ngaynhapkho=ngaynhapkho;
		
	}

	
	public String getNgaySanXuat()
	{
		
		return this.ngaysanxuat;
	}

	
	public void setNgaySanXuat(String ngaysanxuat)
	{
		this.ngaysanxuat=ngaysanxuat;
		
	}
	
}


