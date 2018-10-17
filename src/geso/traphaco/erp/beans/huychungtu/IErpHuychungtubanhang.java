package geso.traphaco.erp.beans.huychungtu;

import java.sql.ResultSet;

public interface IErpHuychungtubanhang 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);	

	public String getSoHoadon();
	public void setSoHoadon(String sohoadon);
	public String getSophieuxuatkho();
	public void setSoPhieuxuatkho(String soxuatkho);
	public String getSoDondathang();
	public void setSoDondathang(String sodathang);
	public String getHdkhacId();
	public void setHdkhacId(String hdkhacId);
	
	public String getloaichungtu();
	public void setloaichungtu(String loaichungtu);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getSochungtuRequest();
	public void setSochungtuRequest(ResultSet soctRequest);
	
	public ResultSet getTTHoaDonRequest();
	public void setTTHoaDonRequest(ResultSet TTHoaDonRequest);
	
	public ResultSet getTTHoaDonCKRequest();
	public void setTTHoaDonCKRequest(ResultSet TTHoaDonCKRequest);
	
	public void createRs();
	public void init();
	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai, String[] loaichungtu, String[] ngaytao, String[] thutu);
	public boolean updateHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai, String[] loaichungtu, String[] ngaytao, String[] thutu);
	
	public String getnppId();
	public void setnppId(String nppId);
	
	// FORM IN HỦY CHỨNG TỪ
	public String getSobienban();
	public void setSobienban(String sobienban);
	
	public String getNgaybienban();
	public void setNgaybienban(String ngaybienban);
	
	public String getNgaybb();
	public void setNgaybb(String ngaybb);
	
	public String getBenA_bb();
	public void setBenA_bb(String benA_bb);
	
	public String getDiachiA_bb();
	public void setDiachiA_bb(String diachiA_bb);
	
	public String getDienthoaiA_bb();
	public void setDienthoaiA_bb(String dienthoaiA_bb);
	
	public String getMstA_bb();
	public void setMstA_bb(String mstA_bb);
	
	public String getOngbaA_bb();
	public void setOngbaA_bb(String ongbaA_bb);
	
	public String getChucvuA_bb();
	public void setChucvuA_bb(String chucvuA_bb);
	
	public String getBenB_bb();
	public void setBenB_bb(String benB_bb);
	
	public String getDiachiB_bb();
	public void setDiachiB_bb(String diachiB_bb);
	
	public String getDienthoaiB_bb();
	public void setDienthoaiB_bb(String dienthoaiB_bb);
	
	public String getMstB_bb();
	public void setMstB_bb(String mstB_bb);
	
	public String getOngbaB_bb();
	public void setOngbaB_bb(String ongbaB_bb);
	
	public String getChucvuB_bb();
	public void setChucvuB_bb(String chucvuB_bb);
	
	public String getSohoadon1_bb();
	public void setSohoadon1_bb(String sohoadon1_bb);
	
	public String getSohoadon2_bb();
	public void setSohoadon2_bb(String sohoadon2_bb);
	
	public String getNgayhoadon1_bb();
	public void setNgayhoadon1_bb(String ngayhoadon1_bb);
	
	public String getSohoadon3_bb();
	public void setSohoadon3_bb(String sohoadon3_bb);
	
	public String getKyhieu1_bb();
	public void setKyhieu1_bb(String kyhieu1_bb);
	
	public String getKyhieu2_bb();
	public void setKyhieu2_bb(String kyhieu2_bb);
	
	public String getSohoadon4_bb();
	public void setSohoadon4_bb(String sohoadon4_bb);
	
	public String getNgayhoadon2_bb();
	public void setNgayhoadon2_bb(String ngayhoadon2_bb);
	
	public String getLydothuhoi_bb();
	public void setLydothuhoi_bb(String lydothuhoi_bb);
	
	public String Chot(String id);
	void DBClose();
	public String getCongtyId();
	public void setCongtyId(String congtyId);
}
