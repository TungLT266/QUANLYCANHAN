package geso.traphaco.erp.beans.erp_nhomtaisan;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_nhomtaisanList extends Serializable, IPhan_Trang
{
	public String getId();

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

	public String getUserTen();

	public boolean CheckReferences(String column, String table);

	public boolean DeleteNhts();

	public void init();

	public ResultSet getRslts();

	public void setRslts(ResultSet ltsRs);

	public void setLtsId(String ltsId);

	public String getLtsId();
	
	public ResultSet getRsCdts();

	public void setRsCdts(ResultSet cdtsRs);
	
	public void setCdtsId(String cdtsId);

	public String getCdtsId();	
	
	void DBClose();

}
