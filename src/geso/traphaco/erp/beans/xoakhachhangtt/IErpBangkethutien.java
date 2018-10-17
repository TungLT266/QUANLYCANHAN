package geso.traphaco.erp.beans.xoakhachhangtt;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.erp.beans.xoakhachhangtt.IHoadon;

public interface IErpBangkethutien 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
		
	public String getId();
	public void setId(String id);
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);
	public String getNgayghiso();
	public void setNgayghiso(String ngayghiso);
	
	public String getNppId();
	public void setNppId(String nccId);
	
	public String getHtttId();
	public void setHtttId(String htttId);
	public ResultSet getHtttRs();
	public void setHtttRs(ResultSet htttRs);
	
	public String getNganhangId();
	public void setNganhangId(String nganhangId);
	public ResultSet getNganhangRs();
	public void setNganhangRs(ResultSet nganhangRs);
	
	public String getChinhanhId();
	public void setChinhanhId(String cnId);
	public ResultSet getChinhanhRs();
	public void setChinhanhRs(ResultSet chinhanhRs);
	
	public boolean createTTHD();
	public boolean updateTTHD();
	public boolean chotTTHD(String userId, String tthdId);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void init();
	public void initDisplay();
	public void createRs();
	public void DBclose();

	public String getIsNPP();
	public void setIsNPP(String isNPP);
	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	public ResultSet getNvgnRs();
	public void setNvgnRs(ResultSet nvgnRs);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public String getKhuvucId();
	public void setKhuvucId(String kvId);
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	
	public String getMakhachhang();
	public void setMakhachhang(String makhachhang);
		
	public Hashtable<String, Double> getTienAVAT();
	public void setTienAVAT(Hashtable<String, Double> tienAVAT);
	
	public Hashtable<String, Double> getThanhtoan();
	public void setThanhtoan(Hashtable<String, Double> thanhtoan);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public List<IHoadon> getHoadonRs();
	public void setHoadonRs(List<IHoadon> hoadonRs);
	
	public String gettongtientt();
	public void settongtientt(String tongtientt);
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public String getQuanhuyenId();
	public void setQuanhuyenId(String quanhuyenId);
	public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet QuanhuyenRs);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	public void setLoainhanvien(Object loainhanvien);
	public String getDoituongId();
	public void setDoituongId(Object doituongId);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
}
