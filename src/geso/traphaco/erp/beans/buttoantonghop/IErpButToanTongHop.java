package geso.traphaco.erp.beans.buttoantonghop;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IErpButToanTongHop
{
	
	public Hashtable<String, String> getBtth_diachi();
	public void setBtth_diachi(Hashtable<String, String> btth_diachi);
	public String getSoHieu( String pk, dbutils db);
	public String getBTTTHTiepTheo();
	public String thangnam(String ngay);
	public dbutils getDb() ;
	public void setDb(dbutils db);
	public String getSoChungTu();
	public void setSoChungTu(String soChungTu);
	
	public ResultSet getRsSanPham() ;
	public void setRsSanPham(ResultSet rsSanPham);
	public ResultSet getRsTienTe();
	public void setRsTienTe(ResultSet rsTienTe);
	public String getId();
	
	public String getTienTe();
	public void setTienTe(String tienTe);
	public double getTiGia() ;
	public void setTiGia(double tiGia) ;
	public void setId(String Id);
	
	public String getNgayButToan();
	
	public void setNgayButToan(String NgayButToan);
	
	public String getDienGiai();
	
	public void setDienGiai(String DienGiai);
	
	public String getMsg();
	
	public void setMsg(String Msg);
	
	public String getUserId();
	
	public void setUserId(String UserId);
	
	public String[] getTaiKhoanNoIds();
	
	public void setTaiKhoanNoIds(String[] TaiKhoanNoIds);
	
	public String[] getTaiKhoanCoIds();
	
	public void setTaiKhoanCoIds(String[] TaiKhoanCoIds);

	public String[] getSotien();
	
	public void setSotien(String[] sotien);
	
	public String[] getTtcpNoIds();
	
	public String[] getTtcpCoIds();
	
	public void setTtcpNoIds(String[] TtcpNoIds);
	
	public void setTtcpCoIds(String[] TtcpCoIds);
	
	public String[] getDg();
	
	public void setDg(String[] dg);
	
	public String[] getDungcho_No();
	
	public void setDungcho_No(String[] dc_noIds);
	
	public String[] getDungcho_NoTen();
	
	public void setDungcho_NoTen(String[] dc_noTens);
	
	
	public String[] getDungcho_Co();
	
	public void setDungcho_Co(String[] dc_coIds);
	
	public String[] getDungcho_CoTen();
	
	public void setDungcho_CoTen(String[] dc_coTens);
	
	
	public String[] getKbhIds();
	
	public void setKbhIds(String[] kbhIds);
	
	public ResultSet getSanpham_NoRs();
	
	public ResultSet getSanpham_CoRs();
	
	//khu vực popup sản phẩm----------------------------------//
	public Hashtable<String, String> getStt() ;
	
	public void setStt(Hashtable<String, String> stt) ;
	
	public Hashtable<String, String> getMaSanPham() ;
	
	public void setMaSanPham(Hashtable<String, String> maSanPham);
	
	public Hashtable<String, Double> getPhanTram();
	
	public void setPhanTram(Hashtable<String, Double> phanTram) ;
	
	public Hashtable<String, Double> getTienViet();
	
	public void setTienViet(Hashtable<String, Double> tienViet) ;
	
	public Hashtable<String, Double> getTienNT() ;
	
	public void setTienNT(Hashtable<String, Double> tienNT);
	
	
	
	//----------------------------------------------------//

	public ResultSet getKho_NoRs();
	
	public ResultSet getNganhang_NoRs();

	public ResultSet getNganhang_CoRs();
	
	public ResultSet getNcc_NoRs();
	
	public ResultSet getNcc_CoRs();
	
	public ResultSet getTaisan_NoRs();

	public ResultSet getTaisan_CoRs();
	
	public ResultSet getKhachhang_NoRs();

	public ResultSet getKhachhang_CoRs();
	
	public ResultSet getKbhRs();
	
	public ResultSet getMavvRs();
	
	public ResultSet getDiabanRs();
	
	public ResultSet getTinhthanhRs();
	
	public ResultSet getBenhvienRs();
	
	public ResultSet getSanphamRs();
	
	public void createRs();

	public boolean Save();	
	
	public boolean Edit();
	
	public boolean Editsauchot();
	
	public void Init();

	public int getCount();
	
	public void setCount(int count);		
			
	public String Chot();
	
	public String Delete();

	public String getCongtyId() ;

	public void setCongtyId(String congtyId) ;

	public ResultSet getTrungTamChiPhiNoRs(String tknoId);
	
	public ResultSet getTrungTamChiPhiCoRs(String tkcoId);

	public ResultSet getTaiKhoanKT_CoRs() ;

	public ResultSet getTaiKhoanKT_NoRs() ;
	
	public ResultSet getNhanvien_NoRs() ;
	
	public ResultSet getNhanvien_CoRs() ;

	public ResultSet getKho_CoRs();
	
	public void DBClose();
			
	public ResultSet getDoituong_Rs(String dungchoSelected, String loai, int vitri);

	public String[] getMavvIds();
	
	public void setMavvIds(String[] mavvIds);
	
	public String[] getDiabanIds();
	
	public void setDiabanIds(String[] diabanIds);
	
	public String[] getTinhthanhIds();
	
	public void setTinhthanhIds(String[] tinhthanhIds);

	public String[] getBenhvienIds();
	
	public void setBenhvienIds(String[] benhvienIds);
	
	public String[] getSanphamIds();
	
	public void setSanphamIds(String[] sanphamIds);
	
	public String[] getPKSEQIds();
	
	public Hashtable<String, String> getBtth_Mauhd();
	public void setBtth_Mauhd(Hashtable<String, String> btth_Mauhd); 
	
	public void setPKSEQIds(String[] PKSEQIds);
	
	// HÓA ĐƠN
	
	
	public Hashtable<String, String> getBtth_Kyhieuhd();
	public void setBtth_Kyhieuhd(Hashtable<String, String> btth_Kyhieuhd); 
	
	public Hashtable<String, String> getBtth_Sohd();
	public void setBtth_Sohd(Hashtable<String, String> btth_Sohd); 
	
	public Hashtable<String, String> getBtth_Ngayhd();
	public void setBtth_Ngayhd(Hashtable<String, String> btth_Ngayhd); 
	
	public Hashtable<String, String> getBtth_TenNCChd();
	public void setBtth_TenNCChd(Hashtable<String, String> btth_TenNCChd); 
	
	public Hashtable<String, String> getBtth_MSThd();
	public void setBtth_MSThd(Hashtable<String, String> btth_MSThd); 
	
	public Hashtable<String, String> getBtth_Tienhanghd();
	public void setBtth_Tienhanghd(Hashtable<String, String> btth_Tienhanghd); 
	
	public Hashtable<String, String> getBtth_Thuesuathd();
	public void setBtth_Thuesuathd(Hashtable<String, String> btth_Thuesuathd); 
	
	public Hashtable<String, String> getBtth_Tienthuehd();
	public void setBtth_Tienthuehd(Hashtable<String, String> btth_Tienthuehd); 
	
	public Hashtable<String, String> getBtth_Conghd();
	public void setBtth_Conghd(Hashtable<String, String> btth_Conghd); 
	
	public Hashtable<String, String> getBtth_Ghichuhd();
	public void setBtth_Ghichuhd(Hashtable<String, String> btth_Ghichuhd);
	

	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String[] getSoTienNT();
	public void setSoTienNT(String[] soTienNT) ;
	
	
	
	public String getnppId();
	public void setnppId(String nppId);
	
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String MoChot();
}