package geso.traphaco.erp.beans.tigia;

import java.sql.ResultSet;
import java.util.List;

public interface ITigia
{
	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String Id);

	public void setTiGiaQuyDoi(String tigiaquydoi);

	public String getTiGiaQuyDoi();

	public void setTuNgay(String tungay);

	public String getTuNgay();

	public String getDenNgay();

	public void setDenNgay(String denngay);

	public String getTrangThai();

	public void setTrangThai(String trangthai);

	public String getSoLuongGoc();

	public void setSoLuongGoc(String SoLuongGoc);

	public void init();

	public String getMessage();

	public void setMessage(String msg);
	
	public boolean CreateTiGia();

	public boolean UpdateTigia();

	public String getTenTienTe();

	public void setTenTienTe(String TenTienTe);

	public String getMaTienTe();

	public void setMaTienTe(String MaTienTe);

	public String getCongTy();

	public void setCongTy(String CongTy);

	public ResultSet getCongTyRs();

	public void setCongTyRs(ResultSet CongTyRs);

	public List<ITiGia_TienTe> getTiGia_TienTeList();

	public void setTiGia_TienTeList(List<ITiGia_TienTe> TiGia_TienTeList);

	public void createRs();
	
	public void CreateTiGiaTienTeList();

	public void closeDB();
}
