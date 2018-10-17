package geso.traphaco.erp.beans.kehoachkinhdoanh;

import geso.traphaco.erp.beans.donmuahang.*;

import java.sql.ResultSet;
import java.util.List;

public interface IErpKehoachkinhdoanh
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String id);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public List<IKenhbanhang> getKbhListMB();
	public void setKbhListMB(List<IKenhbanhang> kbhListMB);
	public List<IKenhbanhang> getKbhListMN();
	public void setKbhListMN(List<IKenhbanhang> kbhListMN);

	public String getLoai();
	public void setLoai(String loai);
	
	public String getMsg();
	public void setMsg(String msg);

	public void createRs();
	public void init();
	public boolean createKhkd();
	public boolean updateKhkd();

	public void close();

}
