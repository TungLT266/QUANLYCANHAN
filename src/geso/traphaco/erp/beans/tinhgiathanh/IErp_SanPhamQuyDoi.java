package geso.traphaco.erp.beans.tinhgiathanh;

public interface IErp_SanPhamQuyDoi {

	
	public String getId();
	public void SetId(String id);
	
	
	public String getMaKT_DemQD();
	public void setMaKT_DemQD(String MaKT_DemQD);

	public String getMaKT_RaQD();
	public void setMaKT_RaQD(String MaKT_RaQD);
	
	
	public void SetSL_DemQD(double soluong);
	
	public double GetSL_DemQD();
	
	public void SetSL_RaQD(double soluong);
	
	public double GetSL_RaQD();
	
	public void SetTongSL_RaQD(double soluong);
	
	public double GetTongSL_RaQD();

	public double GetGiaTriNL();
	
	public double GetChiPhiKhac();
	
	public void SetGiaTriNL(double GiaTriNL);

	public void SetChiPhiKhac(double ChiPhiKhac);
	
	
}
