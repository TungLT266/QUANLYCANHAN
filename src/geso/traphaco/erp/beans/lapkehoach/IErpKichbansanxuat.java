package geso.erp.beans.lapkehoach;

import geso.erp.beans.danhmucvattu.IDanhmucvattu_SP;

import java.sql.ResultSet;
import java.util.List;

public interface IErpKichbansanxuat
{
	public String getCtyId();

	public void setCtyId(String ctyId);

	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String Id);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public ResultSet getSpRs();

	public void setSpRs(ResultSet spRs);

	public String getSpId();

	public void setSpId(String spId);

	public ResultSet getDaychuyenRs();

	public void setDaychuyenRs(ResultSet dcRs);

	public String getDaychuyenId();

	public void setDaychuyenId(String dcId);

	public ResultSet getBomRs();

	public void setBomRs(ResultSet bomRs);

	public String getBomId();

	public void setBomId(String bomId);

	public String getSoluongchuan();

	public void setSoluongchuan(String slgchuan);

	public String getChophepTT();

	public void setChophepTT(String chophepTT);

	public void setListDanhMuc(List<IDanhmucvattu_SP> list);

	public List<IDanhmucvattu_SP> getListDanhMuc();

	public String getMsg();

	public void setMsg(String msg);

	public boolean createKichbansanxuat();

	public boolean updateKichbansanxuat();

	public void createRs();

	public void changeBom();

	public void init();

	public void DbClose();

}
