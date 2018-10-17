package geso.traphaco.erp.beans.lenhsanxuatgiay;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErpLenhsanxuatList extends Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getLSXId();
	public void setLSXId(String LSXId);
	
	public String getIsduyet();
	public void setIsduyet(String Isduyet);
 
	public String getUserId();
	public void setUserId(String userId);

	public String getTungayTao();
	public void setTungayTao(String tungay);
	public String getDenngayTao();
	public void setDenngayTao(String denngay);
	
	public String getTungayDk();
	public void setTungayDk(String ngaydk);
	public String getDenngayDk();
	public void setDenngayDk(String ngaydk);
	
	public String getMasp();
	public void setMasp(String masp);
	public String getTenSp();
	public void setTenSp(String tensp);
	
	public String getIddvkd();
	public void setIddvkd(String iddvkd);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getMsg();
	public void setMsg(String msg);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsxRs);
	
	public ResultSet getNguoiTaoRs();
	public void setNguoiTaoRs(ResultSet nguoitaoRs);
	public String getNhamayId();
	public void setNhamayId(String nhamayid);
	
	public ResultSet getNhaMayRs();
	public void setNhaMayRs(ResultSet nhamayRs);
	public String getNguoitaoId();
	public void setNguoitaoId(String nguoitaoid);
	
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet DvkdRs);	
	public void init(String search);
	public void DBclose();
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getTungayBD();
	public void setTungayBD(String tungayBD);
	public String getDenngayBD();
	public void setDenngayBD(String denngayBD);
	public String getTungayKT();
	public void setTungayKT(String tungayKT);
	public String getDenngayKT();
	public void setDenngayKT(String denngayKT);

	public String getSoLSX();
	public void setSoLSX(String SoLSX);
	
	public String getNppId();
	public void setNppId(String nppId);
	public void setPhanLoai(String phanloai);
	public String getPhanLoai();
}
