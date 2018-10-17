package geso.traphaco.center.beans.donmuahang;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IERP_DonDatHang
{

	public String getId();
	public void setId(String id);

	public String getNgaygiaodich();
	public void setNgaygiaodich(String ngaygiaodich);

	public String getNgaydenghigh();
	public void setNgaydenghigh(String ngaydenghigh);

	public String getNppTen();
	public void setNppTen(String nppTen);

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

	public double getChietkhau();
	public void setChietkhau(double chietkhau);

	public double getVAT();
	public void setVAT(double vat);

	public String getISKM();
	public void setISKM(String iskm);

	public void setloaichietkhau(String loaichietkhau);
	public String getloaichietkhau();

	public String getMessage();
	public void setMessage(String msg);

	public void setrs_nhacc(ResultSet rsncc);

	public ResultSet GetRsnhacc();

	public String[] getSotien();
	public void setSotien(String[] sotien);

	public String[] getScheme();
	public void setScheme(String[] scheme);

	public String getGhichu();
	public void setGhichu(String ghichu);

	public String getThongTin();
	public void setThongtin(String thongtin);

	public String getNoidungchietkhau();
	public void setNoidungchietkhau(String noidungchietkhau);

	public String getIdNhaCungCap();
	public void setIdNhaCungCap(String idnhacc);

	public String getTenNhaCungCap();
	public void setTenNhaCungCap(String tennhacc);

	public void setListSanPham(List<IERP_DonDatHang_SP> list);

	public String getIDKenhBanHang();
	public void setIDKenhBanHang(String kenhbanhangid);
	
	public void setrs_kbh(ResultSet rskbh);
	public ResultSet GetRsKbh();


	public List<IERP_DonDatHang_SP> getListSanPham();

	public List<IERP_DonDatHang_SP> getSpKmList();
	public void setSpKmList(List<IERP_DonDatHang_SP> spList);

	public String getNppId();
	public void setNppId(String nppId);

	public void setrs_nhapp(ResultSet rsnpp);

	public ResultSet GetRsnhapp();

	public String getKhottId();
	public void setKhottId(String khottid);
	
	public void setrs_khott(ResultSet rskhott);
	public ResultSet GetRskhott();

	public String getKhottTen();
	public void setKhottTen(String KhottTen);

	public double getTongtientruocVAT();
	public void setTongtientruocVAT(double tttvat);

	public double getTongtiensauVAT();
	public void setTongtiensauVAT(double ttsvat);

	public void Init();

	public void CreateRs();

	public Hashtable<String, Integer> getSpThieuList();

	public void setSpThieuList(Hashtable<String, Integer> spThieuList);

	public ResultSet getrsdvkd();
	public void setrsdvkd(ResultSet rsdvkd);

	public void setdvkdid(String dvkdid);
	public String getdvkdid();

	public void setUserTen(String Userten);
	public String getUserten();

	public String GetLoaidonhang();
	public void setLoaidonhang(String loaidonhang);

	public boolean Save();

	public boolean Edit(String _ischot);

	public boolean Delete();

	public void DBClose();

	public String getdiachi();
	public void setdiachi(String diachi);
	
	public String getdiachixhd();
	public void setdiachixhd(String diachixhd);
	
	public String getmasothue();
	public void setmasothue(String mst);

	public boolean SaveKm();
	public boolean Editkm(String _ischot);

	public float GetTrongLuong();
	public void setTrongLuong(float trongthung);

	public float GetTheTich();
	public void setTheTich(float TheTich);

	public float GetTongThung();
	public void setTongthung(float trongluong);

	public void setTongGoiVC(float tonggoivc);
	public float getTongGoiVC();

	public void setGiaVanChuyen(float GiaVanChuyen);
	public float getGiaVanChuyen();

	public void setChietKhauThuongMai(double chietkhautm);
	public double getChietKhauThuongMai();

	public void setTuVanChuyen(String TuVanChuyen);
	public String getTuVanChuyen();
	
	public void load_tudongchuyen();

	public List<IDonvi> getDvList();
	public void setDvList(List<IDonvi> dvList);

	public String getHinhthucvanchuyen();
	public void setHinhthucvanchuyen(String hinhthucvanchuyen);

	public void initDisplay();
	public Hashtable<String, String> getCtkmList();

	public void setCtkmList(Hashtable<String, String> ctkmList);
	public void initDisplayKm();

	public String getIsDuyetCKTM();
	public void setIsDuyetCKTM(String isduyet);
	
	
	public boolean DuyetCKTM();
	
	public void initExcel();

}
