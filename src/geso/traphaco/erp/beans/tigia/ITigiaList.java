package geso.traphaco.erp.beans.tigia;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface ITigiaList extends Serializable, IPhan_Trang
{
	public String getUserId();

	public void setUserId(String userId);

	public String getID();

	public void setID(String pk_seo);

	public String getTuNgay();

	public void setTuNgay(String tungay);

	public String getDenNgay();

	public void setDenNgay(String denngay);

	public void setTIENTE_FK(String tiente);

	public String getTIENTE_FK();

	public void setTIGIAQUYDOI(String tigiaquydoi);

	public String getTIGIAQUYDOI();

	public String getTRANGTHAI();

	public void setTRANGTHAI(String trangthai);

	public void init();

	public String getMessage();

	public void setMessage(String msg);

	public ResultSet getTigiaList();

	public void setTigiaList(ResultSet rsKho);

	public ResultSet getTienteList();

	public void setTienteList(ResultSet rsTiente);

	public void CreateRs();

	public String getCongTy();

	public void setCongTy(String CongTy);

	public void closeDB();
	
	public void setChixem(String chixem);
	public String getChixem();
}
