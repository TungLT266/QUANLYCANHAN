package geso.traphaco.center.beans.daidienkinhdoanh;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IDaidienkinhdoanh extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);

	public void setMaCongTy(String macty);
	public String getMaCongTy();
	public void setViTri(String vitri);
	public String getViTri();
	public void setKhuVucTT(String khuvuctt);
	public String getKhuVucTT();
	public void setSoNamLamViec(String nam);
	public String getSoNamLamViec();
	public void setSoDTCty(String sodt);
	public String getSoDTCty();
	public void setNgayVaoCty(String ngay);
	public String getNgayVaoCty();
	public void setHDLD(String hdld);
	public String getHDLD();
	public void setLoaiHD(String loaihd);
	public String getLoaiHD();
	public void setNgayKyHD(String ngay);
	public String getNgayKyHD();
	public void setNgayKetThucHD(String ngay);
	public String getNgayKetThucHD();
	public void setTeamLeader(String teamleader);
	public String getTeamLeader();
	public void setSoTaiKhoan(String sotk);
	public String getSoTaiKhoan();
	public void setEmail(String email);
	public String getEmail();
	public void setGhiChu(String ghichu);
	public String getGhiChu();

	public String getId();
	public void setId(String id);	
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
	public String getKenhbanhang();  
	public void setKenhbanhang(String kenhbanhang);
	public String getGsbanhang();  
	public void setGsbanhang(String gsbanhang);
	public String getNhaphanphoi();
	public void setNhaphanphoi(String nhaphanphoi);
	public String getMessage();
	public void setMessage(String msg);
			
	public ResultSet getGsbhList();	
	public void setGsbhList(ResultSet gsbhlist);	
	
	public ResultSet getNppList();
	public String getNppId(); 
	public void setNppId(String nppId); 

	public boolean CreateDdkd();
	public boolean UpdateDdkd();
	public void createRS();
	public void createGsbhList();
	public boolean getIsDelete();
	public void setIsDelete(boolean isDelete);	
	public void DBClose();
	public ResultSet GetRsDDKDTienNhiem();
	public void setDDKDTn(String id);
	public String getDDKDTn();
	public void setPhantramChuyen(String phantram);
	public String getPhanTramChuyen();
	
	public void setNganHang(String nganhang);
	public String getNganHang();
	
	public void setChiNhanh(String chinhanh);
	public String getChiNhanh();
	
	public void setMaNhanVien(String manhanvien);
	public String getMaNhanVien();
	
	public void setMaThuViec(String mathuviec);
	public String getMaThuViec();

	public void setMatkhau(String matkhau);
	public String getMatkhau();
	
	//CO THE CHON Nhan vien tien nhiem
	public void setCothechonTN(String mathuviec);
	public String getCothechonTN();
	
	
	public String getMaFAST();
	public void setMaFAST(String maFAST);
	public String getIsNVTT();
	public void setIsNVTT(String nvtt);
	
}