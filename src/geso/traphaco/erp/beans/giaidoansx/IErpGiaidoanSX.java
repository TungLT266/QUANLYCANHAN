package geso.traphaco.erp.beans.giaidoansx;

import java.sql.ResultSet;
import java.util.List;

public interface IErpGiaidoanSX {
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean create();
	public boolean update();
	public void init();
	public void createRs();
	public void DbClose();
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
	public ResultSet getTscdRs();
	public void setTscdRs(ResultSet tscdRs);
	
	public List<IErpGiaiDoanSXThongSo> getThongSoList();
	public void setThongSoList(List<IErpGiaiDoanSXThongSo> thongSoList);
	
	public String getGiaodiennhap();
	public void setGiaodiennhap(String giaodiennhap);
	
	public ResultSet getLoaimauinSxRs();
	public void setLoaimauinSxRs(ResultSet loaimauinSxRs);
	
	public String getLoaimauinId();
	public void setLoaimauinId(String loaimauinId);
	
	public String getLoaisanpham();
	public void setLoaisanpham(String loaisanpham);

	public String getIsAllBom();
	public void setIsAllBom(String isAllBom);

	public ResultSet getLoaisanphamRs();
	public void setLoaisanphamRs(ResultSet loaisanphamRs);
	
	public String getSoluongmau();
	public void setSoluongmau(String soluongmau);
	
	public String getSolannhap();
	public void setSolannhap(String solannhap);
}
