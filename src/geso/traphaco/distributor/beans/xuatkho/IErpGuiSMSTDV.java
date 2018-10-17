package geso.traphaco.distributor.beans.xuatkho;

import java.sql.ResultSet;

public interface IErpGuiSMSTDV
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	
	public String getNgaygiaohanggui();
	public void setNgaygiaohanggui(String ngaygiaohanggui);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getXuatcho();
	public void setXuatcho(String xuatcho);
	
	public String getTinhthanhId();
	public void setTinhthanhId(String tinhthanhId);
	public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet tinhthanhRs);
	
	public String getQuanhuyenId();
	public void setQuanhuyenId(String quanhuyenId);
	public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet qunahuyenRs);
	
	public String getNVGNId();
	public void setNVGNId(String nvgnId);
	public ResultSet getNVGNRs();
	public void setNVGNRs(ResultSet nvgnRs);
	
	public String getNVBHId();
	public void setNVBHId(String nvbhId);
	public ResultSet getNVBHRs();
	public void setNVBHRs(ResultSet nvbhRs);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String getDondathangId();
	public void setDondathangId(String kbhId);
	public ResultSet getDondathangRs();
	public void setDondathangRs(ResultSet ddhRs);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public boolean create( String[] machungtu );
	public boolean update( String[] machungtu );
	
	public void createRs();
	public void init();
	public void initDisplay();
	
	public void DBclose();
	
	public String getPhanloai();
	public void setPhanloai(String phanloai);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getChanhxe();
	public void setChanhxe(String chanhxe);
	public String getDienthoai();
	public void setDienthoai(String dienthoai);
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getDonvi();
	public void setDonvi(String donvi);
	
	public ResultSet getNhanvienRs();
	public void setNhanvienRs(ResultSet nvRs);
	
	public String getNhanvien_TuId();
	public void setNhanvien_TuId(String nvTuId);
	
	public String getNhanvien_DenId();
	public void setNhanvien_DenId(String nvDenId);
	public String getNhanvien_DenQL();
	public void setNhanvien_DenQL(String nvDenQl);
	
	public String getNhanvien_CCQLId();
	public void setNhanvien_CCQLId(String nvDenId);
	public String getNhanvien_CCQL();
	public void setNhanvien_CCQL(String nvDenQl);
	
	public String getMaFast();
	public void setMaFast(String mafast);
	public String getTenkhachhang();
	public void setTenkhachhang(String tenkh);
	public String getMachungtu();
	public void setMachungtu(String machungtu);
	public String getNguoitaodon();
	public void setNguoitaodon(String nguoitaodon);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	//NEU DANG NHAP LA TDV, THI CHI LAY NPP VA TDV NAY
	public String getNpp_duocchon_id();
	public void setNpp_duocchon_id(String npp_duocchon_id);
	public String getTdv_dangnhap_id();
	public void setTdv_dangnhap_id(String tdv_dangnhap_id);
}
