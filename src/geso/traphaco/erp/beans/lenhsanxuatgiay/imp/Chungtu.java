package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IChungtu;

public class Chungtu implements IChungtu 
{
	String ngaychungtu;
	String maso;
	int type;
	long soluong;
	long tonkho;
	long ngaytonkho;
	
	String phuthuoc;
	String prefix;
	
	public Chungtu()
	{
		this.ngaychungtu = "";
		this.maso = "";
		this.type = -1;
		this.soluong = -1;
		this.tonkho = -1;
	}

	public String getNgayCT() 
	{
		return this.ngaychungtu;
	}

	public void setNgayCT(String ngayct) 
	{
		this.ngaychungtu = ngayct;
	}

	public String getMaso() 
	{
		return this.maso;
	}

	public void setMaso(String maSo) 
	{
		this.maso = maSo;
	}

	public int getType() 
	{
		return this.type;
	}

	public void setType(int type) 
	{
		this.type = type;
	}

	public long getSoLuong()
	{
		return this.soluong;
	}

	public void setSoLuong(long soluong) 
	{
		this.soluong = soluong;
	}

	public long getTonkho() 
	{
		return this.tonkho;
	}

	public void setTonkho(long tonkho)
	{
		this.tonkho = tonkho;
	}

	public long getNgaytonkho() 
	{
		return this.ngaytonkho;
	}

	public void setNgaytonkho(long ngaytonkho) 
	{
		this.ngaytonkho = ngaytonkho;
	}

	public String getPhuThuoc() 
	{
		return this.phuthuoc;
	}

	public void setPhuThuoc(String phuthuoc) 
	{
		this.phuthuoc = phuthuoc;
	}

	public String getPrefix() 
	{
		return this.prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}
	
}
