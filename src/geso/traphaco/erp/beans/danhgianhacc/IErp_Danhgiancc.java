package geso.traphaco.erp.beans.danhgianhacc;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_Danhgiancc extends Serializable{

	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getSpId();
	public void setSpId(String spId);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public String getDvthId();
	public void setDvthId(String dvthId);
	
	public ResultSet getDvthRs();
	public void setDvthRs(ResultSet dvthRs);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public void createRs();
	public void init();
	
	public boolean createDGNcc();
	public boolean updateDGNcc();
	
	public void close();
	
	public String getMessage();
	public void setMessage(String msg);
	
	public void setActive(String active);
	public String getActive();
	
	public String getTucach();
	public void setTucach(String tucach);
	
	public String getUytin();
	public void setUytin(String uytin);
	
	public String getChatluong();
	public void setChatluong(String chatluong);
	
//	public String getKhanangcn();
//	public void setKhanangcn(String khanangcn);
	
	public String getGiaca();
	public void setGiaca(String giaca);
	
	public String getPtthanhtoan();
	public void setPtthanhtoan(String ptthanhtoan);
	
	public String getPtvanchuyen();
	public void setPtvanchuyen(String ptvanchuyen);
	
	public String getTggiaohang();
	public void setTggiaohang(String tggiaohang);
	
	public String getHaumai();
	public void setHaumai(String haumai);
	
	public String getChinhsachkhac();
	public void setChinhsachkhac(String chinhsachkhac);
	
	public String getSoPO();
	public void setSoPO(String sopo);
	
	public ResultSet getPORs();
	public void setPORs(ResultSet pors);
}
