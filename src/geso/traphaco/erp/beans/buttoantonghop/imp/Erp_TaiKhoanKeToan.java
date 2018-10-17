package geso.traphaco.erp.beans.buttoantonghop.imp;

import geso.traphaco.erp.beans.buttoantonghop.IErp_TaiKhoanKeToan;

public class Erp_TaiKhoanKeToan implements IErp_TaiKhoanKeToan
{

	String Id, Ten, Ma, DungTtcp, DungKho, DungNganhang, DungTaisan, DungNCC, DungKhachhang;

	public Erp_TaiKhoanKeToan()
	{
		this.Id = "";
		this.Ten = "";
		this.Ma = "";
		this.DungTtcp="";
		this.DungKho = "";
		this.DungNganhang = "";
		this.DungNCC = "";
		this.DungTaisan = "";
		this.DungKhachhang = "";
	}

	public String getId()
	{

		return this.Id;
	}

	public void setId(String Id)
	{
		this.Id = Id;
	}

	public String getTen()
	{

		return this.Ten;
	}

	public void setTen(String Ten)
	{
		this.Ten = Ten;
	}

	public String getMa()
	{
		return this.Ma;
	}

	public String setMa(String Ma)
	{

		return this.Ma = Ma;
	}

	public String getDungTTCP()
	{

		return this.DungTtcp;
	}

	public void setDungTTCP(String TTCP)
	{
		this.DungTtcp = TTCP;
	}

	public String getDungKho()
	{

		return this.DungKho;
	}

	public void setDungKho(String kho)
	{
		this.DungKho = kho;
	}

	public String getDungNganhang()
	{

		return this.DungNganhang;
	}

	public void setDungNganhang(String nganhang)
	{
		this.DungNganhang = nganhang;
	}

	public String getDungNCC()
	{

		return this.DungNCC;
	}

	public void setDungNCC(String ncc)
	{
		this.DungNCC = ncc;
	}

	public String getDungTaisan()
	{

		return this.DungTaisan;
	}

	public void setDungTaisan(String taisan)
	{
		this.DungTaisan = taisan;
	}

	public String getDungKhachhang()
	{

		return this.DungKhachhang;
	}

	public void setDungKhachhang(String khachhang)
	{
		this.DungKhachhang = khachhang;
	}

}
