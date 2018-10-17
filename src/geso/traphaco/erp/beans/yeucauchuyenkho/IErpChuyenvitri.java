package geso.traphaco.erp.beans.yeucauchuyenkho;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErpChuyenvitri
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	
	public String getLydoyeucau();
	public void setLydoyeucau(String lydoyeucau);
	
	public String gettask();
	public void settask(String task);
	
	public String getKyHieu();
	public void setKyHieu(String kyhieu);
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getTongSLYC();
	public void setTongSLYC(String tongSLYC);
	
	public String getKhoXuatId();
	public void setKhoXuatId(String khoxuattt);
	public String getKhoXuatTen();
	public void setKhoXuatTen(String khoxuattt);
	public ResultSet getKhoXuatRs();
	public void setKhoXuatRs(ResultSet khoxuatRs);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public String getKhoNhapTen();
	public void setKhoNhapTen(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public List<IYeucau> getSpList();
	public void setSpList(List<IYeucau> spList);
	
	public String getMsg();
	public void setMsg(String msg);
	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public boolean createCK();
	public boolean updateCK();
	
	public void createRs();
	public void init();
	public void initDisplay();
	
	public void DBclose();

	public String getCoDoituong();
	public void setCoDoituong(String codoituong);
	public String getLoaidoituongId();
	public void setLoaidoituongId(String loaidoituong);
	public String getDoituongId();
	public void setDoituongId(String doituongId);
	public ResultSet getDoituongRs();
	public void setDoituongRs(ResultSet doituongRs);
	
	public String getCoKhonhan();
	public void setCoKhonhan(String cokhoNHAN);
	public String getCoDoituongNhan();
	public void setCoDoituongNhan(String codoituongNhan);
	public String getLoaidoituongNhanId();
	public void setLoaidoituongNhanId(String loaidoituongNhan);
	public String getDoituongNhanId();
	public void setDoituongNhanId(String doituongNhanId);
	public ResultSet getDoituongNhanRs();
	public void setDoituongNhanRs(ResultSet doituongNhanRs);
	
	//LUU SO LO, SOLUONG THAY DOI
	public Hashtable<String, String> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong); 
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong);
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN();
	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong);
	public Hashtable<String, String> getSanpham_Vitrinhan();
	public void setSanpham_Vitrinhan(Hashtable<String, String> sp_vitri);
	
	public String getVitrichuyenId();
	public void setVitrichuyenId(String vitriChuyenId);
	public ResultSet getVitriChuyenRs();
	public void setVitriChuyenRs(ResultSet vitriChuyenRs);
	
	public ResultSet getVitriNhanRs();
	public void setVitriNhanRs(ResultSet vitriNhanRs);
	
	public String getCochiphi();
	public void setCochiphi(String coChiphi);
	public String getChiphiId();
	public void setChiphiId(String chiphiId);
	public ResultSet getChiphiRs();
	public void setChiphiRs(ResultSet chiphiRs);
	
	
	
}
