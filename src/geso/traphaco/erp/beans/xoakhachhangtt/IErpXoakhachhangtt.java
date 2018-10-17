package geso.traphaco.erp.beans.xoakhachhangtt;

import java.sql.ResultSet;
import java.util.List;

public interface IErpXoakhachhangtt 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getNgayghiso();
	public void setNgayghiso(String ngayghiso);
	
	public String getNppId();
	public void setNppId(String nccId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nccRs);
	
	public String getHtttId();
	public void setHtttId(String htttId);
	public ResultSet getHtttRs();
	public void setHtttRs(ResultSet htttRs);
	
	public String getNoidungId();
	public void setNoidungId(String ndId);
	public ResultSet getNoidungRs();
	public void setNoidungRs(ResultSet ndRs);
	
	public String getNganhangId();
	public void setNganhangId(String nganhangId);
	public ResultSet getNganhangRs();
	public void setNganhangRs(ResultSet nganhangRs);
	
	public String getChinhanhId();
	public void setChinhanhId(String cnId);
	public ResultSet getChinhanhRs();
	public void setChinhanhRs(ResultSet chinhanhRs);
	
	public String getSotaikhoan();
	public void setSotaikhoan(String sotk);
	
	public String getCttratruocId();
	public void setCttratruocId(String ctttId);
	public ResultSet getCttratruocRs();
	public void setCttratruocRs(ResultSet ctttRs);
	
	public String getNoidungtt();
	public void setNoidungtt(String ndtt);
	
	public String getSotientt();
	public void setSotientt(String sotientt);
	
	public String getSotienttVND();
	public void setSotienttVND(String sotienttVND);
	
	public String getTongthanhtoan(); 
	public void setTongthanhtoan(String tongthanhtoan); 

	public String getTongthanhtoanVND(); 
	public void setTongthanhtoanVND(String tongthanhtoanVND); 
	
	public String getChenhlech(); 
	public void setChenhlech(String chenhlech); 
	
	public String getChenhlechVND(); 
	public void setChenhlechVND(String chenhlechVND);
	
	public String getHoadonIds();
	public void setHoadonIds(String hdIds);
	public List<IHoadon> getHoadonRs();
	public void setHoadonRs(List<IHoadon> hoadonRs);
	
	public String getCtttIds();
	public void setCtttIds(String ctttIds);
	public List<IHoadon> getCtttList();
	public void setCtttList(List<IHoadon> hoadonRs);
	
	public boolean createTTHD();
	public boolean updateTTHD();
	public boolean chotTTHD(String userId, String tthdId);
	
	public String getMsg();
	public void setMsg(String msg);
	

	public String getLoaixnId();
	public void setLoaixnId(String loaixnId);
	public String getDoiTuongTamUng();
	public void setDoiTuongTamUng(String DoiTuongTamUng);
	
	public String getNvtuId();
	public void setNvtuId(String nvtuId);
	public ResultSet getNvtuRs();
	public void setNvtuRs(ResultSet nvtuRs);
	
	public String getNccId();
	public void setNccId(String nccId);
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public ResultSet getTienteRs();

	public void setTienteRs(ResultSet tienteRs);
	public String getTienteId();
	public void setTienteId(String ttId);

	public void init();
	public void initDisplay();
	public void createRs();
	public void DBclose();
	
	public String getTigia();
	public void setTigia(String Tigia);
	
	public String getTungay();
	public void setTungay(String Tungay);
	
	public String getDenngay();
	public void setDenngay(String Denngay);
	
	public String getTungaytk();
	public void setTungaytk(String Tungaytk);
	
	public String getDenngaytk();
	public void setDenngaytk(String Denngaytk);
		
	public String getmaphieutk();
	public void setmaphieutk(String maphieutk);
	
	public String getkhachhangtk();
	public void setkhachhangtk(String khachhangtk);
	
	public String getkenhbanhangtk();
	public void setkenhbanhangtk(String kenhbanhangtk);
	
	public String getnhomkhachhangtk();
	public void setnhomkhachhangtk(String nhomkhachhangtk);
	
	public String getsotientk();
	public void setsotientk(String sotientk);
	
	public String gettrangthaitk();
	public void settrangthaitk(String trangthaitk);
	
	public String getCongtyId();
	public void setCongtyId(String CongtyId);
	
	public String getLoaikh();
	public void setLoaikh(String Loaikh);
	
	public String getKHten();
	public void setKHten(String KHten);
	
	public String getisNPP();
	public void setisNPP(String isNPP);
	
	public String getNppIdGoc();
	public void setNppIdGoc(String nppIdGoc);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
}
