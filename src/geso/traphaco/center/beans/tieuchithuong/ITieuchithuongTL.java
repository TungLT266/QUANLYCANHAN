package geso.traphaco.center.beans.tieuchithuong;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface ITieuchithuongTL 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public String getActiveTab();
	public void setActiveTab(String active);
	
	public String getKT();
	public void setKT(String kt);
	
	public String getMucNPP();
	public void setMucNPP(String MucNpp);
	
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	
	public String getNgayDS_Tungay();
	public void setNgayDS_Tungay(String tungay);
	public String getNgayDS_Denngay();
	public void setNgayDS_Denngay(String denngay);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public void setSanphamRs(ResultSet spRs);
    public ResultSet getSanphamRs();
    public String getSpIds();
	public void setSpIds(String spIds);
	
	public void setNppRs(ResultSet nppRs);
    public ResultSet getNppRs();
    public String getNppIds();
	public void setNppIds(String nppIds);
	
	// phan bo nha phan phoi
	
	public String getNppIds1();
	public void setNppIds1(String nppIds1);
	public String getNppIds2();
	public void setNppIds2(String nppIds2);
	public String getNppIds3();
	public void setNppIds3(String nppIds3);
	public String getNppIds4();
	public void setNppIds4(String nppIds4);
	public String getNppIds5();
	public void setNppIds5(String nppIds5);
	
	public void setVungRs(ResultSet vungRs);
    public ResultSet getVungRs();
    public String getVungIds();
	public void setVungIds(String vungIds);
	
	public void setKhuvucRs(ResultSet kvRs);
    public ResultSet getKhuvucRs();
    public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);
	
	public void setKenhRs(ResultSet kenhRs);
    public ResultSet getKenhRs();
    public String getKenhIds();
	public void setKenhIds(String kenhIds);
    
	public void setKhoRs(ResultSet khoRs);
    public ResultSet getKhoRs();
    public String getKhoIds();
	public void setKhoIds(String khoIds);
	
	public void setNhomsanphamRs(ResultSet spRs);
    public ResultSet getNhomsanphamRs();
    public String getNhomsanphamIds();
	public void setNhomsanphamIds(String nhomspIds);
	
    //Cac Muc 1 thang
    public String[] getDiengiaiMuc();
    public void setDiengiaiMuc(String[] diengiai);
    public String[] gethttt1();
    public void sethttt1(String[] httt1);
    
    public String[] getTumuc();
    public void setTumuc(String[] tumuc);
    public String[] getDenmuc();
    public void setDenmuc(String[] denmuc);
    public String[] getThuongSR();
    public void setThuongSR(String[] thuongSR);
    public String[] getThuongTDSR();
    public void setThuongTDSR(String[] thuongTDSR);
    public String[] getThuongSS();
    public void setThuongSS(String[] thuongSS);
    public String[] getThuongTDSS();
    public void setThuongTDSS(String[] thuongTDSS);
    public String[] getThuongASM();
    public void setThuongASM(String[] thuongASM);
    public String[] getThuongTDASM();
    public void setThuongTDASM(String[] thuongTDASM);
    
    //Cac Muc 3 thang
    public String[] getDiengiaiMuc3();
    public void setDiengiaiMuc3(String[] diengiai);
    public String[] getTumuc3();
    public void setTumuc3(String[] tumuc);
    public String[] getDenmuc3();
    public void setDenmuc3(String[] denmuc);
    public String[] getThuongSR3();
    public void setThuongSR3(String[] thuongSR);
    public String[] getThuongTDSR3();
    public void setThuongTDSR3(String[] thuongTDSR);
    public String[] getThuongSS3();
    public void setThuongSS3(String[] thuongSS);
    public String[] getThuongTDSS3();
    public void setThuongTDSS3(String[] thuongTDSS);
    public String[] getThuongASM3();
    public void setThuongASM3(String[] thuongASM);
    public String[] getThuongTDASM3();
    public void setThuongTDASM3(String[] thuongTDASM);
    
    public String getMucvuot();
    public void setMucvuot(String mucvuot);
    public String getChietkhauMucvuot();
    public void setChietkhauMucvuot(String ckMv);
    public String getDonviMucvuot();
    public void setDonviMucvuot(String dvMv);
    
	public void init();
	public void createRs();
	
	public void initDisplay();
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
	
	
	//San pham tra
	public String getHinhthuctra();
    public void setHinhthuctra(String htTra);
    
    public String[] getMaspTra();
    public void setMaspTra(String[] maspTra);
    public String[] getTenspTra();
    public void setTenspTra(String[] tenspTra);
    public String[] getSoluongspTra();
    public void setSoluongspTra(String[] soluongspTra);
    public String[] getIdspTra();
    public void setIdspTra(String[] idspTra);
    public String[] getDongiaspTra();
    public void setDongiaspTra(String[] dongiaspTra);
	

	///////////////////////////
    
    public String getDoanhsotheoThung();
    public void setDoanhsotheoThung(String isThung);
    
    public String getHTTT();
    public void setHTTT(String httt);
    public String getPT_TRACK();
    public void setPT_TRACK(String ptTRACK);
    
    //LUU CAC THONG TIN THEO MUC
    public Hashtable<String, String> getMuc_Tiendo();
    public void setMuc_Tiendo(Hashtable<String, String> tiendo);
    
    public Hashtable<String, String> getMuc_SpTra();
    public void setMuc_SpTra(Hashtable<String, String> spTRA);
    
    public Hashtable<String, String> getPhanbo();
    public void setPhanbo(Hashtable<String, String> phanbo);
    
    public Hashtable<String, String> getPhanboTheoMucNPP1();
    public void setPhanboTheoMucNPP1(Hashtable<String, String> phanbo1);
    public Hashtable<String, String> getPhanboTheoMucNPP2();
    public void setPhanboTheoMucNPP2(Hashtable<String, String> phanbo2);
    public Hashtable<String, String> getPhanboTheoMucNPP3();
    public void setPhanboTheoMucNPP3(Hashtable<String, String> phanbo3);
    public Hashtable<String, String> getPhanboTheoMucNPP4();
    public void setPhanboTheoMucNPP4(Hashtable<String, String> phanbo4);
    public Hashtable<String, String> getPhanboTheoMucNPP5();
    public void setPhanboTheoMucNPP5(Hashtable<String, String> phanbo5);
    
    public String getPhanloai();
    public void setPhanloai(String phanloai);
    
    public Hashtable<String, String> getDieukien();
    public void setDieukien(Hashtable<String, String> dieukien);
    public Hashtable<String, String> getQuydoi();
    public void setQuydoi(Hashtable<String, String> quydoi);
    
    public String[] getDKKMTICHLUY_KHACHHANG_Id();
	public void setDKKMTICHLUY_KHACHHANG_Id(String[] dKKMTICHLUY_KHACHHANG_Id) ;
	public ResultSet getKhDangKyRs();
	public String[] getKhDcDuyet() ;
	public void setKhDcDuyet(String[] khDcDuyet);
	public String[] getKhDcDuyetDisplay() ;
	public void setKhDcDuyetDisplay(String[] khDcDuyetDisplay) ;
	
	public void loadSP_NHOM();
    
	public String getNgayTB_Tungay();
	public void setNgayTB_Tungay(String tungay);
	public String getNgayTB_Denngay();
	public void setNgayTB_Denngay(String denngay);
	
	public String getPhaidangky();
    public void setPhandangky(String phaidangky);
    
    public String getChiavaodongia();
    public void setChiavaodongia(String chiavaodongia);
	
    public void setKhoanmuccpRs(ResultSet khoanmucRs);
    public ResultSet getKhoanmuccpRs();
    public void setKhoanmuccpId(String khoanmuccpId);
    public String getKhoanmuccpId();
    
    public String getKhongcanduyettraTL();
    public void setKhongcanduyettraTL(String khongcanduyet);
    
    public void setGiobatdau(String giobatdau);
    public int getGiobatdau();
    public void setGioketthuc(String gioketthuc);
    public int getGioketthuc();
    
    public void setSoxuattoida(String soxuattoida);
    public String getSoxuattoida();
    
    public void setKbhRs(ResultSet kbh);
    public ResultSet getKbhRs();
    public void setKbhIds(String kbhIds);
    public String getKbhIds();
    
    public void setLoaikhRs(ResultSet loaiKh);
    public ResultSet getLoaikhRs();
    public void setLoaikhIds(String lkhIds);
    public String getLoaikhIds();
    
}
