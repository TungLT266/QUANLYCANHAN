package geso.traphaco.erp.beans.loaitaisan;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_loaiCCDCList extends Serializable, IPhan_Trang
{
	public String getId();

	public String getMa();

	public String getTen();

	public String getTkId();
	
	public void setCtyId(String ctyId);

	public String getCtyId();

	public String getMsg();

	public String getNgaytao();

	public String getNguoitao();

	public String getNgaysua();

	public String getNguoisua();

	public String getTrangthai();

	public void setId(String id);

	public void setMa(String ma);

	public void setTen(String ten);

	public void setTkId(String tkId);

	public void setMsg(String Msg);

	public void setNgaytao(String ngaytao);

	public void setNguoitao(String nguoitao);

	public void setNgaysua(String ngaysua);

	public void setNguoisua(String nguoisua);

	public void setTrangthai(String trangthai);

	public ResultSet getRsts();

	public void setRsts(ResultSet Rsts);

	public ResultSet getRsTk();

	public void setRsTk(ResultSet Rstk);

	public void setUserid(String userId);

	public String getUserTen();

	public boolean CheckReferences(String column, String table);

	public boolean Delete();

	public void init();

	void DBClose();

}
