package geso.traphaco.center.beans.bchoadonbanhang;

import geso.traphaco.center.util.IPhan_Trang;
import java.io.Serializable;
import java.sql.ResultSet;

public interface IBchoadonbanhangList extends IPhan_Trang, Serializable
{
	public String getUserId();
	public void setUserId(String userId);

	public String getTungay();
	public void setTungay(String tungay);

	public String getDenngay();
	public void setDenngay(String denngay);

	public String getMakh();
	public void setMakh(String makh);

	public String getMafast();
	public void setMafast(String mafast);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getKhohang();
	public void setKhohang(String khohang);

	public String getTrinhduocvien();
	public void setTrinhduocvien(String trinhduocvien);

	public String getMsg();
	public void setMsg(String msg);

	public ResultSet getBchoadonbanhangRs();
	public void setBchoadonbanhangRs(ResultSet bchoadonbanhangRs);

	public String getBchoadonbanhangId();
	public void setBchoadonbanhangId(String bchoadonbanhangId);

	public ResultSet getKhohangRs();
	public void setKhohangRs(ResultSet khohangRs);

	public String getKhohangId();
	public void setKhohangId(String khohangId);

	public ResultSet getTrinhduocvienRs();
	public void setTrinhduocvienRs(ResultSet trinhduocvienRs);

	public String getTrinhduocvienId();
	public void setTrinhduocvienId(String trinhduocvienId);

	public void init(String query);
	

	
	public void DbClose();

}
