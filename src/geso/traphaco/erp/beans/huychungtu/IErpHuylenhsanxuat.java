package geso.traphaco.erp.beans.huychungtu;

import java.sql.ResultSet;

public interface IErpHuylenhsanxuat 
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getSoThanhtoan();
	public void setSoThanhtoan(String sothanhtoan);
	public String getSoHoadon();
	public void setSoHoadon(String sohoadon);
	public String getSophieunhapkho();
	public void setSoPhieunhapkho(String sonhapkho);
	public String getSoPhieunhanhang();
	public void setSoPhieunhanhang(String sonhanhang);
	public String getSoDonMuahang();
	public void setSoDonmuahang(String somuahang);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	
	public String getNgayghinhan();
	public void setNgayghinhan(String Ngayghinhan);
 
	public String getMsg();
	public void setMsg(String msg);
	public String getloaichungtu();
	public void setloaichungtu(String loaichungtu);
	public ResultSet getSochungtuRequest();
	public void setSochungtuRequest(ResultSet soctRequest);
	public ResultSet getSochungtuDetail();
	public void setSochugntuDetail(ResultSet soctdetail);
	
	public void setDienGiaiCT(String dienGiaiCT);
	public String getDienGiaiCT();
	
	public void createRs();
	public void init();
	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai, String[] loaichungtu, String[] ngaytao, String[] thutu,String[] type, String[] chon);
 	
	public void DbClose();
	 
	
	public String getKdclId_new();
	public void setKdclId_new(String kdclId_new) ;
}
