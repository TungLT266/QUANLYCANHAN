package geso.traphaco.erp.beans.yeucaukythuat;

import geso.traphaco.erp.beans.yeucaukythuat.imp.ErpYeuCauKyThuat_HoaChat;

import java.sql.ResultSet;
import java.util.List;

public interface IErpYeuCauKyThuat {
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
	public String getMa();
	public void setMa(String ma);
	public String getTen();
	public void setTen(String ten);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getThongsoTu();
	public void setThongsoTu(String thongsoTu);
	public String getThongsoDen();
	public void setThongsoDen(String thongsoDen);
	public String getDvt();
	public void setDvt(String dvt);
	public String getGioihan();
	public void setGioihan(String gioihan);
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet sanphamRs);
	public List<ErpYeuCauKyThuat_HoaChat> getHoachatList();
	public void setHoachatList(List<ErpYeuCauKyThuat_HoaChat> hoachatList);
}
