package geso.traphaco.erp.beans.chinhsachduyetmuahang;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IChinhsachduyetmuahang {
	public boolean isHoatDong() ;
	public String getCtyId();

	public void setCtyId(String ctyId);
	
	public String getCsId();

	public void setCsId(String csId);
	
	public String getKcpId();

	public void setKcpId(String kcpId);	
		
	public String getDvthId();

	public void setDvthId(String dvthId);

	public void setDvth(ResultSet dvth);
	
	public String getDiengiai();

	public void setDiengiai(String diengiai);	
	
	public ResultSet getDvth();
	
	public void setKcp(ResultSet kcp);

	public ResultSet getKcp();

	public void setCS(ResultSet cs);

	public ResultSet getCS();
	
	public String getMessage();
	
	public void setMessage(String msg);

	public void setChon(String[] chon);
	
	public void setThutu(String[] thutu);
	
	public void setQuyetdinh(String[] quyetdinh);
	
	public void setRequest(HttpServletRequest request);

	public String[] getKcpIds();

	public void setKcpIds(String[] kcpIds);
	
	public void init();
	
	public void init_new();
	
	public void CpSave();
	public void Save();
	public void createCsList();
	
	public ResultSet Chinhsachduyet(String khoangchiphiId);
	
	public ResultSet Chucdanhconlai(String khoangchiphiId);
	
	public void DBClose();
	
	public void SetUserId(String userid);
	public void setHoatDong(String hoatdong);
	
	public void setTab(String tab);
	public String getTab();
	
	public ResultSet getNcc();
	public ResultSet getSp();
	public ResultSet ChucdanhconlaiNcc(String nccId);
	public ResultSet ChucdanhconlaiSp(String spId);
	public void setNccChon(String[] nccchon);
	public void setNccQuyetdinh(String[] nccquyetdinh);
	public void NccSave();
	public void setNccId(String nccId);
	public String getThutuDuyetNhaCC(String sodong);
	public void setSpChon(String[] spchon);
	public void setSpQuyetdinh(String[] spquyetdinh);
	public void setSpId(String spId);
	public void SpSave();
	public String getThutuDuyetSp(String sodong);
	public int getNumberofNcc();
	public int getNumberofSp();
	
}
