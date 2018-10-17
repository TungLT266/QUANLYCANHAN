package geso.traphaco.erp.beans.tigia.imp;

import geso.traphaco.erp.beans.tigia.ITiGia_TienTe;

public class TiGia_TienTe implements ITiGia_TienTe
{

	String TiGiaId, TienTeId, TenTienTe, MaTienTe, SoLuongGoc, TyGiaQuyDoi, CongTyId, TuNgay, DenNgay;

	public TiGia_TienTe()
	{
		this.TiGiaId = "";
		this.TienTeId = "";
		this.TenTienTe = "";
		this.MaTienTe = "";
		this.SoLuongGoc = "";
		this.TyGiaQuyDoi = "";
		this.TuNgay="";
		this.DenNgay="";
		this.CongTyId = "";
		
	}

	public String getTiGiaId()
	{

		return this.TiGiaId;
	}

	public void setTiGiaId(String TiGiaId)
	{
		this.TiGiaId = TiGiaId;

	}

	public String getTenTienTe()
	{

		return this.TenTienTe;
	}

	public void setTenTienTe(String TenTienTe)
	{
		this.TenTienTe = TenTienTe;
	}

	public String getMaTienTe()
	{

		return this.MaTienTe;
	}

	public void setMaTienTe(String MaTienTe)
	{
		this.MaTienTe = MaTienTe;
	}

	public String getSoLuongGoc()
	{

		return this.SoLuongGoc;
	}

	public void setSoLuongGoc(String SoLuongGoc)
	{
		this.SoLuongGoc = SoLuongGoc;
	}

	public void setTiGiaQuyDoi(String tigiaquydoi)
	{
		this.TyGiaQuyDoi = tigiaquydoi;
	}

	public String getTiGiaQuyDoi()
	{

		return this.TyGiaQuyDoi;
	}

	public void setTuNgay(String TuNgay)
	{
		this.TuNgay = TuNgay;
	}

	public String getTuNgay()
	{

		return this.TuNgay;
	}

	public String getDenNgay()
	{

		return this.DenNgay;
	}

	public void setDenNgay(String denngay)
	{
		this.DenNgay = denngay;
	}

	public String getCongTyId()
	{

		return this.CongTyId;
	}

	public void setCongTyId(String CongTyId)
	{
		this.CongTyId = CongTyId;
	}

	@Override
	public String getTienTeId()
	{

		return this.TienTeId;
	}

	@Override
	public void setTienTeId(String TienTeId)
	{

		this.TienTeId = TienTeId;
	}

}
