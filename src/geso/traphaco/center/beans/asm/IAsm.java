package geso.traphaco.center.beans.asm;

import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IAsm {
	public String getId();
	public void setId(String Id);

	public String getTen();
	public void setTen(String asmTen);
	
	public ResultSet getTructhuocRs();
	public void setTructhuocRs(ResultSet tructhuocrs);
	
	public void setTructhuoc(String Tructhuoc);
	public String getTructhuoc();
	
	public String getNgaysinh();
	public void setNgaysinh(String ngaysinh);
	
	public String getDienthoai();
	public void setDienthoai(String dienthoai);
		
	public String getEmail();
	public void setEmail(String email);

	public String getDiachi();
	public void setDiachi(String diachi);

	public String getKbhId();
		
	public void setKbhId(String kbhId);

	public ResultSet getKbh();
		
	public void setKbh(ResultSet kbh);

	public String getDvkdId();
		
	public void setDvkdId(String dvkdId);

	public ResultSet getDvkd();
		
	public void setDvkd(ResultSet dvkd);

	public String[] getKvId();
		
	public void setKvId(String[] kvId);

	public ResultSet getKv();
		
	public void setKv(ResultSet kv);

	public String getTrangthai();
		
	public void setTrangthai(String trangthai);

	public String getMsg();
		
	public void setMsg(String msg);
		
	public String getUserId();
		
	public void setUserId(String userId);

	public void init_New();
		
	public void init_Update();
	
	public boolean Save(HttpServletRequest request);
	
	public Hashtable<String, String> getHTKvId();
	
	public void DBClose();
	
	public void setMaCt(String mact);
	public String getMaCt();
	
	public void setVitri(String vt);
	public String getVitri();
	
	public void setVungTT(String vungtt);
	public String getVungTT();
	
	public void setSonamlamviec(String sonam);
	public String getSonamlamviec();
	
	public void setSoDTcongty(String sdt);
	public String getSoDTcongty();
	
	public void setNgayvaoct(String ngayvaoct);
	public String getNgayvaoct();
	
	public void setLoaiHD(String loaihd);
	public String getLoaiHD();
	
	public void setSoHD(String sohd);
	public String getSoHD();
	
	public void setNgayketthucHD(String ngaykt);
	public String getNgayketthucHD();
	
	public void setSotk(String stk);
	public String getSotk();
	
	public void setGhichu(String ghichu);
	public String getGhichu();
	
	public void setNgaykyHD(String ngaykyhd);
	public String getNgaykyHD();
	
	public void setDakyHD(String dakyhd);
	public String getDakyHD();
	
	public void setNganHang(String nganhang);
	public String getNganHang();
	
	public void setChiNhanh(String chinhanh);
	public String getChiNhanh();
	
	public void setMaNhanVien(String manhanvien);
	public String getMaNhanVien();
	
	public void setMaThuViec(String mathuviec);
	public String getMaThuViec();
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	
	public String getMaFAST();
	public void MaFAST(String maFAST);
	
	public String getMaNhanSu();
	public void setMaNhanSu(String MaNhanSu);

}
