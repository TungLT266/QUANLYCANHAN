package geso.traphaco.erp.beans.kichbansanxuatgiay;

import java.sql.ResultSet;
import java.util.List;

public interface IErpKichBanSanXuatGiay
{
	public String getCtyId();

	public void setCtyId(String ctyId);

	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String Id);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public String getTrangThai();

	public void setTrangThai(String TrangThai);

	public String getSpSelected();

	public void setSpSelected(String SpSelected);

	public String getNhaMayId();

	public void setNhaMayId(String NhaMayId);
	
	
	public int getSongaysanxuat();
	public void setSongaysanxuat(int songaysanxuat);
 
	public ResultSet getSpRs();

	public void setSpRs(ResultSet spRs);

	public ResultSet getRsNhaMay();

	public void setRsNhaMay(ResultSet rsNhaMay);

	public ResultSet getRsVattu();

	public ResultSet setRsVattu(ResultSet rsVattu);

	public String getSoluongchuan();

	public void setSoluongchuan(String soluongchuan);

	public ResultSet getRsCongdoan();

	public void setRsCongdoan(ResultSet rsCongdoan);

	public String getMsg();

	public void setMsg(String msg);

	public void setCongDoanSxList(List<IKichBan_CongDoanSx> lst);

	public List<IKichBan_CongDoanSx> getCongdoanSxList();

	public boolean save();

	public boolean edit();

	public void createRs();

	public void init();

	public void DbClose();
	
	public String getUploadi();
	public void setUploadi(String uploadi);
	
	public String getHosolosx();
	public void setHosolosx(String hosolosx);

	public String getNgaybanhanhhsl();
	public void setNgaybanhanhhsl(String ngaybanhanhhsl);

	public String getLanbanhanhhsl();
	public void setLanbanhanhhsl(String lanbanhanhhsl);
}
