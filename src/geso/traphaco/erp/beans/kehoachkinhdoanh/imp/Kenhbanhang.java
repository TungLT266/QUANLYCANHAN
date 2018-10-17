package geso.traphaco.erp.beans.kehoachkinhdoanh.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.kehoachkinhdoanh.IKenhbanhang;
import geso.traphaco.erp.beans.kehoachkinhdoanh.ISanpham;

public class Kenhbanhang implements IKenhbanhang
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String id;
	String makenh;
	String tenkenh;

	List<ISanpham> sanpham;
	
	public Kenhbanhang()
	{
		this.id = "";
		this.makenh = "";
		this.tenkenh = "";
		this.sanpham = new ArrayList<ISanpham>();
	}

	public Kenhbanhang(String[] param, List<ISanpham>sanpham)
	{
		this.id = param[0];
		this.makenh = param[1];
		this.tenkenh = param[2];
		this.sanpham = sanpham;
	}

	public Kenhbanhang(String spId, String spMa, String spTen, List<ISanpham> sanpham)
	{
		this.id = spId;
		this.makenh = spMa;
		this.tenkenh = spTen;
		this.sanpham = sanpham;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMakenh()
	{
		return this.makenh;
	}

	public void setMakenh(String makenh)
	{
		this.makenh = makenh;
	}

	public String getTenkenh()
	{
		return this.tenkenh;
	}

	public void setTenkenh(String tenkenh)
	{
		this.tenkenh = tenkenh;
	}
	@Override
	public void setSanpham(List<ISanpham> list) {
		this.sanpham = list;
	}

	@Override
	public List<ISanpham> getSanpham() {
		return this.sanpham;
	}
}
