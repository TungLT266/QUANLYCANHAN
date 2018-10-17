package geso.traphaco.distributor.beans.khoasongay;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKhoasongay extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getNgaykhoaso();
	public void setNgaykhoaso(String ngaykhoaso);
	
	public String getMessege();
	public void setMessege(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getNgayKhoaSoGanNhat();
	public void setNgayKhoaSoGanNhat(String nksgn);
	
	public String getThangks();
	public void setThangks(String thangks);
	
	public String getNamks();
	public void setNamks(String namks);
	
	public boolean isPxkChuaChot();
	public void setIsPxkChuaChot(boolean pxkChuaChot);
	public boolean isPthChuaChot();
	public void setIsPthChuaChot(boolean pthChuaChot);
	
	public ResultSet getDhChuaChotList();
	public void setDhChuaChotList(ResultSet dhcclist);
	public ResultSet getDhDaXuatKhoList();
	public void setDhDaXuatKhoList(ResultSet dhdxklist);
	public ResultSet getDhDaChotList();
	public void setDhDaChotList(ResultSet dhdclist);
	
	public boolean KhoaSoNgay(String ngayks);
	public void init();
	public void createRs();
	public void DBclose();
	
	public String getDksThanhCong();
	public void setDksThanhCong(String tc);
}
