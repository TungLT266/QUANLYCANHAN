package geso.traphaco.erp.beans.tamung;

import geso.traphaco.center.util.*;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpTamUngList extends Serializable, IPhan_Trang {

	public String getTuNgay();

	public void setTuNgay(String TuNgay);

	public String getDenNgay();

	public void setDenNgay(String DenNgay);

	public String getNhanVienId();

	public void setNhanVienId(String NhanVienId);

	public String getNccId();

	public void setNccId(String NccId);

	public String getDoiTuongTamUng();

	public void setDoiTuongTamUng(String DoiTuongTamUng);
	
	public String getTenHienThi();

	public void setTenHienThi(String TenHienThi);

	public String getTrangThai();

	public void setTrangThai(String TrangThai);

	public String getSoTienTamUng();

	public void setSoTienTamUng(String SoTienTamUng);

	public String getTienTeId();

	public void setTienTeId(String TienTeId);

	public String getThoiGianHoanUng();

	public void setThoiGianHoanUng(String ThoiGianHoanUng);

	public String getUserId();

	public void setUserId(String UserId);

	public String getMsg();

	public void setMsg(String Msg);

	public ResultSet getRsTamUng();

	public void setRsTamUng(ResultSet rsTamUng);

	public ResultSet getRsTienTe();

	public void setRsTienTe(ResultSet rsTienTe);

	public void init();

	public void DBClose();
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getDvthId();
	public void setDvthId(String dvthid);	

	public ResultSet getNguoitaoRs();
	public void setNguoitaoRs(ResultSet nguoitaoRs);
	public void setNguoitaoIds(String nspIds);
	public String getNguoitaoIds();

}
