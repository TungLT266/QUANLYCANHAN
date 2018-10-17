package geso.traphaco.erp.beans.kehoachphache;

import geso.traphaco.erp.beans.kehoachphache.imp.ErpKeHoachPhaChe_ChiTiet;

import java.sql.ResultSet;
import java.util.List;

public interface IErpKeHoachPhaChe {
	public void init();
	
	public void createRs();
	
	public boolean create();
	
	public boolean update();
	
	public void DBClose();

	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);

	public String getId();
	public void setId(String id);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getMsg();
	public void setMsg(String msg);

	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getNgaykehoach();
	public void setNgaykehoach(String ngaykehoach);

	public String getBophanthuchien();
	public void setBophanthuchien(String bophanthuchien);

	public String getLoai();
	public void setLoai(String loai);

	public String getSanpham();
	public void setSanpham(String sanpham);

	public List<ErpKeHoachPhaChe_ChiTiet> getKhpcChitietList();
	public void setKhpcChitietList(List<ErpKeHoachPhaChe_ChiTiet> khpcChitietList);

	public ResultSet getKhuvucSXRs();
	public void setKhuvucSXRs(ResultSet khuvucSXRs);

	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet sanphamRs);
}
