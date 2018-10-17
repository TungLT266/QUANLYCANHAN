package geso.traphaco.erp.beans.tienguinganhang;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface ISotienguinganhang
{
	public void setuserId(String userId);

	public String getuserId();

	public void setCtyId(String ctyId);

	public String getCtyId();

	public String getCtyTen();

	public String getDiachi();

	public String getMasothue();
	
	public void setTungay(String tungay);

	public String getTungay();

	public String getDenngay();

	public void setDenngay(String denngay);

	public void setMsg(String msg);

	public String getMsg();

	public void setTkId(String tkId);

	public String getTkId();
	
	public String getSodudau();

	public ResultSet getData();
	
	public ResultSet getDauky();

	public String getNganhang();

	public String getSoTaikhoan();

	public String getTiente();

	public void init();
	
	public String getDate();
	
	public ResultSet getTaikhoan();
	
	public void setView(String view);

	public String getView();
	
	public void setCtyRs(ResultSet ctyRs);
	
	public ResultSet getCtyRs();
	
	public void setCtyIds(String[] ctyIds);

	public String[] getCtyIds();
	
	public String getErpCongtyId();
	
	public void setErpCongtyId(String rs);
	
	public void init_0();
	
	public void DBClose();
	
	public String getChiNhanh();

	public void setChiNhanh(String chiNhanh);

	public String getChiNhanhTen();

	public void setChiNhanhTen(String chiNhanhTen);

	public String getChiNhanhMaSoThue();

	public void setChiNhanhMaSoThue(String chiNhanhMaSoThue);

	public String getChiNhanhDiaChi();
	public void setChiNhanhDiaChi(String chiNhanhDiaChi);

	public ResultSet getChiNhanhRs();

	public void setChiNhanhRs(ResultSet chiNhanhRs);
}
