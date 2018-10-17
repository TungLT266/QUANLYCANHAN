package geso.traphaco.erp.beans.erp_nhomtaisan;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_nhomCCDC extends Serializable, IPhan_Trang
{
	public String getId();

	public String getCtyId();
	
	public void setCtyId(String ctyId);
	
	public String getMa();

	public String getTen();

	public String getMsg();

	public String getNgaytao();

	public String getNguoitao();

	public String getNgaysua();

	public String getNguoisua();

	public String getTrangthai();

	public void setId(String id);

	public void setMa(String ma);

	public void setTen(String ten);

	public void setMsg(String Msg);

	public void setNgaytao(String ngaytao);

	public void setNguoitao(String nguoitao);

	public void setNgaysua(String ngaysua);

	public void setNguoisua(String nguoisua);

	public void setTrangthai(String trangthai);

	public ResultSet getRsts();

	public void setRsts(ResultSet Rsts);

	public void setUserid(String userId);

	public String getUserid();

	public void setUserTen(String userTen);

	public String getUserTen();

	public boolean CheckUnique();

	public boolean CheckReferences(String column, String table);

	boolean ThemMoiMa();

	boolean UpdateMa();

	public boolean DeleteNhts();

	public void init();
	
	public ResultSet getRslts();

	public void setRslts(ResultSet ltsRs);

	public void setLtsId(String ltsId);

	public String getLtsId();

	public ResultSet getRsTtcp();

	public void setRsTtcp(ResultSet ttcpRs);

	public void setTtcpIds(String[] ttcpIds);

	public String[] getTtcpIds();
	
	public void createRs();
		
	public ResultSet getRsCdts();

	public void setRsCdts(ResultSet cdtsRs);

	public void setCdtsIds(String[] cdtsIds);

	public String[] getCdtsIds();
	
	public ResultSet getChoncongdung();
	
	public ResultSet getChoncongdungthem();
	
	public ResultSet getChonTTCP();
	
	public ResultSet getChonTTCPThem();

	public String getCdIdsList();
	
	public String getTtcpIdsList();
	
	
	void DBClose();

}
