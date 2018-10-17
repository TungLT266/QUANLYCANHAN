package geso.traphaco.center.beans.giamsatbanhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IGiamsatbanhang extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);
	
	public String getEmail();
	public void setEmail(String email);
	
	public String getNhacungcap();
	public void setNhacungcap(String nhacungcap);
	
	public String getKenhbanhang();
	public void setKenhbanhang(String kenhbanhang);
	
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
	
	public String getMessage();
	public void setMessage(String msg);
	
	public ResultSet getNhacungcapList();
	public void setNhacungcapList(ResultSet nhacungcaplist);
	
	public ResultSet getKenhbanhangList();
	public void setKenhbanhangList(ResultSet kenhbanhanglist);
	
	public String getNccId();
	public void setNccId(String nccId);
	
	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);
	
	public ResultSet getDvkdList(); 
	public void setDvkdList(ResultSet dvkdlist); 
	
	public String getKbhId();
	public void setKbhId(String kbhId);	
	public String getVungId(); 

	public void setVungId(String vungId); 
	
	public String getKvId(); 

	public void setKvId(String kvId); 
	
	public ResultSet createVungRS();  			

	public ResultSet createKhuvucRS(); 
	public ResultSet createKVByGSBHRS();
	public Hashtable<Integer, String> getKvIds();
    public void init();
	public boolean CreateGsbh( HttpServletRequest request ) ;
	public boolean UpdateGsbh(HttpServletRequest request);
	public void createRS();
	public void setKvIds(String[] kvIds);
	
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
	
	public void setDsnpp(ResultSet Dsnpp);
    public ResultSet getDsnpp();
    
    public void setNpp(String[] npp);   
    public Hashtable<Integer, String> getnpp();
    
    public void setNppUpdate(String[] nppupdate);   
    public Hashtable<Integer, String> getnppUpdate();
    
    public ResultSet getAsmRs();
    public void setAsmRs(ResultSet asmRs);
    
    public ResultSet getBmRs();
    public void setBmRs(ResultSet bmRs);
    
    public String getAsmId();
    public void setAsmId(String asmId);
    
    public String getBmId();
    public void setBmId(String bmId);
    
	public String getTenDangNhap();
	public void setTenDangNhap(String dangnhap);

	public String getMatKhau();
	public void setMatKhau(String matkhau);
	
	
	public String getCmnd();
	public void setCmnd(String cmnd);

	public String getNgaysinh();
	public void setNgaysinh(String ngaysinh);

	public String getQuequan();
	public void setQuequan(String quequan);

	
	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public String getMaFAST();
	public void setMaFAST(String maFAST);

    
}

