package geso.traphaco.center.beans.congtacvien;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface ICongtacvien extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String id);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getTen();
	public void setTen(String ten);
	public String getSodt();
	public void setSodt(String sodt);
	public String getDiachi();
	public void setDiachi(String diachi);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getGioitinh();
	public void setGioitinh(String gioitinh);
	
	public String getChuyenKhoa();
	public void setChuyenkhoa(String chuyenkhoa);
	
	public String getChucvu();
	public void setChucvu(String chucvu);
	
	public String getNgaysinh();
	public void setNgaysinh(String ngaysinh);
	
	public String getLichkham();
	public void setLichkham(String lichkham);
	
	public String getSothich();
	public void setSothich(String sothich);
	
	public String getMessage();
	public void setMessage(String msg);
			
	public String getDiabanId();
	public void setDiabanId(String diabanId);
	public ResultSet getDiabanRs();
	public void setDiabanRs(ResultSet diabanRs);
	
	public String[] getKhMa();
	public void setKhMa(String[] KhMa);
	public String[] getKhTen();
	public void setKhTen(String[] KhTen);
	public String getTDVId();
	public void setTDVId(String tdvId);
	public ResultSet getTDVRs();
	public void setTDVRs(ResultSet tdvRs);
	
	public String getKhachhangId();
	public void setKhachhangId(String khachhangId);
	public ResultSet getKhachhangRs();
	public void setKhachhangRs(ResultSet khachhangRs);
	
	public ResultSet getKenhbanhang();
	public void setKenhbanhang(ResultSet kenhbanhang);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public String getTinhthanhId();
	public void setTinhthanhId(String tinhthanhId);
	public ResultSet getTinhthanhRs();
	public void setTinhthanhRs(ResultSet tinhthanhRs);
	
	public String getQuanhuyenId();
	public void setQuanhuyenId(String quanhuyenId);
	public ResultSet getQuanhuyenRs();
	public void setQuanhuyenRs(ResultSet quanhuyenRs);
	
	public ResultSet getNppList();
	public String getNppId(); 
	public void setNppId(String nppId); 

	public boolean CreateDdkd();
	public boolean UpdateDdkd();
	public void createRS();
	
	public boolean getIsDelete();
	public void setIsDelete(boolean isDelete);	
	public void DBClose();
	
}