package geso.traphaco.erp.beans.hoadon.imp;

import geso.traphaco.erp.beans.hoadon.IHdDetail;

public class HdDetail implements IHdDetail
{
	String id, sochungtu, sohoadon, trangthai, ngayin, solanin, nguoiin, isHDnuocngoai;

	public HdDetail()
	{
		this.id = "";
		this.sochungtu = "";
		this.sohoadon = "";
		this.trangthai = "";
		this.ngayin = "";
		this.solanin = "";
		this.nguoiin = "";
		this.isHDnuocngoai = "";
	}
	
	public HdDetail(String id, String sochungtu, String sohoadon, String trangthai, String ngayin, String solanin, String nguoiin, String isHDnuocngoai )
	{
		this.id = id ;
		this.sochungtu = sochungtu ;
		this.sohoadon = sohoadon ;
		this.trangthai = trangthai ;
		this.ngayin = ngayin ;
		this.solanin = solanin ;
		this.nguoiin = nguoiin;
		this.isHDnuocngoai = isHDnuocngoai ;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getSochungtu() 
	{

		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) 
	{
		this.sochungtu = sochungtu ;
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon ;
	}

	public String getTrangthai() 
	{
		return this.trangthai ;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai ;
	}

	public String getNgayin() 
	{
		return this.ngayin;
	}

	public void setNgayin(String ngayin) 
	{
		this.ngayin = ngayin ;
	}

	public String getSolanin() 
	{
		return this.solanin;
	}

	public void setSolanin(String solanin) 
	{
		this.solanin = solanin ;
	}

	public String getNguoiin() 
	{
		return this.nguoiin;
	}

	public void setNguoiin(String nguoiin) 
	{
		this.nguoiin = nguoiin;
	}

	public String getIsHDnuocngoai() 
	{
		return this.isHDnuocngoai;
	}

	public void setIsHDnuocngoai(String isHDnuocngoai)
	{
		this.isHDnuocngoai = isHDnuocngoai;
	}

}
