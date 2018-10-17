package geso.traphaco.erp.beans.doiquycach;

import geso.traphaco.center.util.IPhan_Trang;

import java.sql.ResultSet;

public interface IDoiquycachList extends  IPhan_Trang
{	
	
	public String getSpnTen() ;
	public void setSpnTen(String spnTen);
	
	public void setSpId(String spId);

	public String getSpId();
	
	public void setSpTen(String spTen);

	public String getSpTen();
	
	public void setNgay(String ngay);

	public String getNgay();

	public void setSoluong(String soluong);

	public String getSoluong();

	public void setCtyId(String ctyId);

	public String getCtyId();
	
	public ResultSet getDqcRS();

	public void setUserId(String userId);

	public String getUserId();

	public void setMsg(String msg);
	
	public String getMsg();
	
	public void settrangthai(String trangthai);
	
	public String gettrangthai();
	
	
	public void init(String st);
	
	public void Xoa(String Id);
	
	public ResultSet getNdqcRs(String Id);
	
	public void DbClose();
	
	public String getTungay();
	
	public void setTungay(String tungay);
	
	public String getDenngay();
	
	public void setDenngay(String denngay) ;
	
	public String getSochungtu();

	public void setSochungtu(String sochungtu);
	
	public String getNguoitao();

	public void setNguoitao(String nguoitao);
	
}
