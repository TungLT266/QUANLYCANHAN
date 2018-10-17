package geso.traphaco.erp.beans.dutoanvattu.imp;
import geso.traphaco.erp.beans.dutoanvattu.INhacungcap;
import geso.traphaco.erp.beans.dutoanvattu.ISanpham;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.util.List;

public class Nhacungcap implements INhacungcap
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String ma;
	String ten;
	String msg;
	String tongtienBvat;
	String tongtienAvat;
	String vat;
	String tongtienBvatNT;
	String tongtienAvatNT;
	
	List<ISanpham> sanpham;
	
	dbutils db;
	
	public Nhacungcap()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.tongtienAvat = "";
		this.tongtienBvat = "";
		this.vat = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Nhacungcap(String id, String ten, String bvat, String vat, String avat)
	{
		this.id = id;
		this.ten = ten;
		this.tongtienAvat = avat;
		this.tongtienBvat = bvat;
		this.vat = vat;
		this.db = new dbutils();
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
	
	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	
	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	
	public String getTongtienBvat() 
	{
		return this.tongtienBvat;
	}

	public void setTongtienBvat(String bvat) 
	{
		this.tongtienBvat = bvat;
	}

	public String getTongtienAvat() 
	{
		return this.tongtienAvat;
	}

	public void setTongtienAvat(String avat)
	{
		this.tongtienAvat = avat;
	}

	public String getVat() 
	{
		return this.vat;
	}

	public void setVat(String vat) 
	{
		this.vat = vat;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg ;
	}

	public void setSanPham(List<ISanpham> list) 
	{
		this.sanpham = list;
	}

	public List<ISanpham> getSanPham() 
	{
		return this.sanpham;
	}

	public String getTongtienBvatNT() 
	{
		return this.tongtienAvatNT;
	}

	public void setTongtienBvatNT(String bvatNT) 
	{
		this.tongtienAvatNT = bvatNT;
	}

	public String getTongtienAvatNT() 
	{
		return this.tongtienAvatNT;
	}

	public void setTongtienAvatNT(String avatNT) 
	{
		this.tongtienAvatNT = avatNT;
	}

	
	
}
