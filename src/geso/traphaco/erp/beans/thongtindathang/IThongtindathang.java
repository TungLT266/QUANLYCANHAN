package geso.traphaco.erp.beans.thongtindathang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IThongtindathang extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);

	public String getMasp();
	public void setMasp(String masp);
	
	public String getTensp();
	public void setTensp(String tensp);
	
	public String getMancc();
	public void setMancc(String mancc);
	
	public String getTenncc();
	public void setTenncc(String tenncc);
	
	public String getMOU();
	public void setMOU(String MOU);

	public String getLeadtime();
	public void setLeadtime(String leadtime);
	
	public String getIsMua();
	public void setIsMua(String isMua);
	
	public String getCtyId();
	public void setCtyId(String ctyId);

	public String getMsg();
	public void setMsg(String msg);

	public boolean createThongtindathang();
	public boolean updateThongtindathang();

	public String convertDate(String date);
	public void init();
	public void createRs();
	public ResultSet getSpRs();
	public ResultSet getNccRs();
	public String getNccId();
	public void setNccId(String nccId);
	public String getSpId();
	public void setSpId(String spId);

	public ResultSet getDtsxRs();
	public ResultSet getDtsxRs_ss();
	public String getDtsxId();
	public void setDtsxId(String dtsxId);
	
	public ResultSet getNhamayRs();
	public String getNhamayId();
	public void setNhamayId(String nmId);
		
	public String getLotsize();
	public void setLotsize(String lotsize);
	public String getThoigianSX();
	public void setThoigianSX(String thoigiansx);
	public String getCleanUp();
	public void setCleanUp(String cleanup);
	public String[] getDtsxIds();
	public void setDtsxIds(String[] dtsxIds);
	public String[] getDtsxIds_Selected();
	public void setDtsxIds_Selected(String[] dtsxIds_selected);
	
	public String[] getTgsx_Selected();
	public void setTgsx_Selected(String[] tgsx_selected);
	public String[] getDtsxIds_SS_Selected();

	public void setDtsxIds_SS_Selected(String[] dtsxIds_ss_selected);
	public String[] getTgsx_SS_Selected();
	public void setTgsx_SS_Selected(String[] tgsx_ss_selected);

	public void DbClose();
}
