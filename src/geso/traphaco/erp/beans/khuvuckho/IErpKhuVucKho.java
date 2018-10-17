package geso.traphaco.erp.beans.khuvuckho;

import java.sql.ResultSet;

public interface IErpKhuVucKho
{
	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public void setMsg(String msg);
	public String getMsg();

	public void setTrangThai(String trangthai);
	public String getTrangThai();

	public void setUserId(String userId);
	public String getUserId();

	public void setDienGiai(String DienGiai);
	public String getDienGiai();

	public void setKhoId(String khoId);
	public String getKhoId();

	public void setMa(String ma);
	public String getMa();

	public void setId(String id);

	public String getId();

	public boolean Update();

	public boolean Create();

	public boolean Search();

	public boolean Delete();
	public boolean Init();

	public void CreateRs();

	public void Close();

	public ResultSet getKhoRs();

	public void setKhoRs(ResultSet Kho);

	public ResultSet getKhuVucKhoRs();

	public void setKhuVucKhoRs(ResultSet KhuVucKho);
	
}
