package geso.traphaco.erp.beans.kehoachlaymau;

import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.kehoachlaymau.imp.PhieuKiemDinh;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface IKeHoachLayMauList extends Serializable, IPhan_Trang {
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void DBclose();
	public void createRs();
	public List<PhieuKiemDinh> getDsPhieuKiemDinhs();
 
	public void init();
	
	public String getNgayKeHoach();
	public void setNgayKeHoach(String ngayKeHoach);
	
	public String getSoTT();
	public void setSoTT(String soTT);
 
	public String getSanphamId();
	public void setSanphamId(String sanphamId);
	
	public String getPhongBanId();
	public void setPhongBanId(String phongBanId);
	
	public ResultSet getRsPhongBan();
	public void setRsPhongBan(ResultSet rsPhongBan);
	
	public ResultSet getRsSanPham();
	public void setRsSanPham(ResultSet rsSanPham);
	
	public ResultSet getRsTrangThai();
	public void setRsTrangThai(ResultSet rsTrangThai);
	
	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getNppId();
	public void setNppId(String nppId);

	public String getId();
	public void setId(String id);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getRsKeHoach();
	public void setRsKeHoach(ResultSet rsKeHoach);
 
}
