package geso.traphaco.erp.beans.kehoachphache;

import java.sql.ResultSet;

public interface IErpKeHoachPhaCheList {
	public void init();
	public void createRS();
	public void delete(String id);
	public void DBClose();
	public String getUserId();
	public void setUserId(String userId);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public String getNgayKeHoach();
	public void setNgayKeHoach(String ngayKeHoach);
	public String getBoPhanThucHien();
	public void setBoPhanThucHien(String boPhanThucHien);
	public String getLoai();
	public void setLoai(String loai);
	public String getSanPham();
	public void setSanPham(String sanPham);
	public ResultSet getKehoachphacheRs();
	public void setKehoachphacheRs(ResultSet kehoachphacheRs);
	public ResultSet getKhuvucSXRs();
	public void setKhuvucSXRs(ResultSet khuvucSXRs);
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet sanphamRs);
}
