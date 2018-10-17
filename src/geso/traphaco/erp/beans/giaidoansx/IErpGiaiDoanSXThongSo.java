package geso.traphaco.erp.beans.giaidoansx;

import geso.traphaco.erp.beans.giaidoansx.imp.ErpGiaiDoanSX_TS_ChiTiet;

import java.sql.ResultSet;
import java.util.List;

public interface IErpGiaiDoanSXThongSo {
	public String getTscdId();
	public void setTscdId(String tscdId);
	
	public String getThietBiSXId();
	public void setThietBiSXId(String thietBiSXId);
	
	/*public String getHieuSuat();
	public void setHieuSuat(String hieuSuat);*/
	
	public String getThongSoChung();
	public void setThongSoChung(String thongSoChung);

	public ResultSet getThietBiSXRs();
	public void setThietBiSXRs(ResultSet thietBiSXRs);
	
	public List<ErpGiaiDoanSX_TS_ChiTiet> getTbsxThongsoList();
	public void setTbsxThongsoList(List<ErpGiaiDoanSX_TS_ChiTiet> tbsxThongsoList);
	
	public void init();
	
	public void DbClose();
	
	public void createThietbisxThongso();
}
