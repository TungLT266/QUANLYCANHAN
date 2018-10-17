package geso.traphaco.erp.beans.nhapkhonhamay;

import java.sql.ResultSet;
import java.util.List;

public interface IErpNhapkhonhamay 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getNgaynhanhang();
	public void setNgaynhanhang(String ngaynhanhang);
	public String getNgaychot();
	public void setNgaychot(String ngaychot);
	
	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getDonmuahangId();
	public void setDonmuahangId(String dmhid);
	public ResultSet getDmhList();
	public void setDmhList(ResultSet dmhlist);
	public String getNcc();
	public void setNcc(String ncc);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void createRs();
	public void init();
	public boolean createNhanHang();
	public boolean updateNhanHang();
	
	public void close();	
}
