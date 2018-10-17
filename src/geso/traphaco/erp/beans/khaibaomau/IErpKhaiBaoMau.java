package geso.traphaco.erp.beans.khaibaomau;

import java.sql.ResultSet;

public interface IErpKhaiBaoMau
{
	public String getId();

	public String getMa();

	public String getTen();

	public String getTkId();

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

	public String getTkkhId();
	
	public void setTkkhId(String tkkhId);
	
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

	public String getUserid();

	public void setUserTen(String userTen);

	public String getUserTen();

	public boolean CheckUnique();

	public boolean CheckReferences(String column, String table);

	boolean ThemMoiMa();

	boolean UpdateMa();

	public boolean DeleteNhts();

	public void init();

	public void createRs();

	/* public void init(); */
	void DBClose();

}
