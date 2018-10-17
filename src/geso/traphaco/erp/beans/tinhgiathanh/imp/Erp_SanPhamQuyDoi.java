package geso.traphaco.erp.beans.tinhgiathanh.imp;

import geso.traphaco.erp.beans.tinhgiathanh.IErp_SanPhamQuyDoi;

public class Erp_SanPhamQuyDoi implements IErp_SanPhamQuyDoi{

	
	double GiaTriNl;
	Double ChiPhiKhac;
	String Id;
	
	String MaKT_DemQD;
	
	String MaKT_RaQD;
	
	double SL_DemQD;
	double SL_RaQD;
	double TongSL_RaQD;
	
	@Override
	public double GetGiaTriNL() {
		// TODO Auto-generated method stub
		return this.GiaTriNl;
	}

	@Override
	public double GetChiPhiKhac() {
		// TODO Auto-generated method stub
		return this.ChiPhiKhac;
	}

	@Override
	public void SetGiaTriNL(double GiaTriNL) {
		// TODO Auto-generated method stub
		this.GiaTriNl=GiaTriNL;
	}

	@Override
	public void SetChiPhiKhac(double _ChiPhiKhac) {
		// TODO Auto-generated method stub
		this.ChiPhiKhac=_ChiPhiKhac;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void SetId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getMaKT_DemQD() {
		// TODO Auto-generated method stub
		return this.MaKT_DemQD;
	}

	@Override
	public void setMaKT_DemQD(String _MaKT_DemQD) {
		// TODO Auto-generated method stub
		this.MaKT_DemQD=_MaKT_DemQD;
	}

	@Override
	public String getMaKT_RaQD() {
		// TODO Auto-generated method stub
		return this.MaKT_RaQD;
	}

	@Override
	public void setMaKT_RaQD(String _MaKT_RaQD) {
		// TODO Auto-generated method stub
		this.MaKT_RaQD=_MaKT_RaQD;
	}

	@Override
	public void SetSL_DemQD(double soluong) {
		// TODO Auto-generated method stub
		this.SL_DemQD=soluong;
	}

	@Override
	public double GetSL_DemQD() {
		// TODO Auto-generated method stub
		return this.SL_DemQD;
	}

	@Override
	public void SetSL_RaQD(double soluong) {
		// TODO Auto-generated method stub
		this.SL_RaQD=soluong;
	}

	@Override
	public double GetSL_RaQD() {
		// TODO Auto-generated method stub
		return this.SL_RaQD;
	}

	@Override
	public void SetTongSL_RaQD(double soluong) {
		// TODO Auto-generated method stub
		this.TongSL_RaQD=soluong;
	}

	@Override
	public double GetTongSL_RaQD() {
		// TODO Auto-generated method stub
		return this.TongSL_RaQD;
	}
	

}
