package geso.traphaco.erp.beans.kichbansanxuatgiay;

import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.KichbanSX_CongdoanSX_ChiTiet;

import java.util.List;

public interface IKichBan_CongDoanSx
{
	public String getId();
	public void setId(String id);

	public String getDienGiai();
	public void setDienGiai(String dienGiai);

	public String getThoiGian();
	public void setThoiGian(String thoiGian);

	public String getThuTu();
	public void setThuTu(String thuTu);

	public String getVattuId();
	public void setVattuId(String vattuId);

	public String getSoluong();
	public void setSoluong(String soluong);
	
	public String getNhapkho();
	public void setNhapkho(String nhapkho);
	
//	public ResultSet getCongdoanRs();
//	public void setCongdoanRs(ResultSet congdoanRs);
	
	public void createRs();
	
	public String getChon();
	public void setChon(String chon);
	
	public List<KichbanSX_CongdoanSX_ChiTiet> getCdsxChitietList();
	public void setCdsxChitietList(List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList);
}
