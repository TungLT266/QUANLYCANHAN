package geso.traphaco.center.beans.thongtinsanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IThongtinsanpham extends Serializable
{
	public void init();
	public String getId();
	public void setId(String id);
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMasp();
	public void setMasp(String masp);
	public String getMaspcu();
	public void setMaspcu(String maspcu);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTrangthaiDaily();
	public void setTrangthaiDaily(String trangthaiDL);
	
	public String getSpMoi();
	public void setSpMoi(String spmoi);
	
	public String getSpChuLuc();
	public void setSpChuLuc(String spchuluc);
	
	public String getDvdlId();
	public void setDvdlId(String dvdlId);
	
	public String getDvdlETCId();
	public void setDvdlETCId(String dvdlETCId);
	
	public String getDvdlTen();
	public void setDvdlTen(String dvdlTen);

	public ResultSet getDvdl();
	public void setDvkd(ResultSet dvkd);
	
	public void setDvkdId(String dvkdId);
	public String getDvkdId();
	
	public String getDvkdTen();
	public void setDvkdTen(String dvkdTen);

	public ResultSet getDvkd();
	
	public String getNhId();
	public void setNhId(String nhId);
	
	public String getNhTen();
	public void setNhTen(String nhTen);
	
	public String getClId();
	public void setClId(String clId);
	
	public String getClTen();
	public void setClTen(String clTen);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public void setGiablc(String giablc);
	public String getGiablc();	
	
	
	public String getMessage();
	public void setMessage(String msg);
	
	public void setNh(ResultSet nh);
	public ResultSet getNh();
	
	public void setCl(ResultSet cl);
	public ResultSet getCl();
	
	public void setNsp(ResultSet nsp);
	public ResultSet getNsp();
	
	public void setNspSelected(ResultSet nsp);
	public ResultSet getNspSelected();
	
	public Hashtable<Integer, String> getNspIds();
	public void setNspIds(String[] nspIds);
	
	public String[] getSl1();

	public void setSl1(String[] sl1);

	public String[] getDvdl1();

	public void setDvdl1(String[] dvdl1);
	
	public String[] getSl2();

	public void setSl2(String[] sl2);

	public String[] getDvdl2();

	public void setDvdl2(String[] dvdl2);

	public void CreateRS();
	
	public boolean UpdateSp();
	
	public boolean CreateSp();
	
	public ResultSet createDvdlRS()throws java.sql.SQLException;
	
	
	//them type
	public void setType(String type);
	public String getType();
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	public Hashtable<Integer, String> getSpIds();
	public void setSpIds(String[] spIds);
	
	public ResultSet getSanphamSelectedRs();
	public void setSanphamSelectedRs(ResultSet spRs);
	
	public ResultSet getPacksizeRs();
	public void setPacksizeRs(ResultSet rs);
	public String getPacksizeId();
	public void setPacksizeId(String packsizeid);
	
	public String getMachuan();
	public void setMachuan(String machuan);
	
	public String getNganhhangid();
	public void setNganhhangid(String nganhhangid);
	
	public ResultSet getRsNganhHang();
	public void setRsNganhhang(ResultSet rs);
	
	public void init2();
	public void DBClose();
	
	public void setKL(String kl);
	public void setDVKL(String dvkl);
	public void setDVTT(String dvtt);
	public void setTT(String tt);
	
	public String getKL();
	public String getDVKL();
	public String getDVTT();
	public String getTT();
	
	public String getquydoithuong();
	public void setquydoithuong(String quydoithuong);
	
	public String getTenchuan();
	public void setTenchuan(String Tenchuan);
	
	
	public String getHansudung();
	public void setHansudung(String hansudung);
	
	public String getNhanhangIds();
	public void setNhanhangIds(String nhanhangIds);
	
	public String getChungloaiIds();
	public void setChungloaiIds(String chungloaiIds);
	
	public String getNhomHangId();
	public void setNhomHangId(String nhomhangId);
	
	public ResultSet getNhomHangRs();
	public void setNhomHangRs(ResultSet nhomhangRs);
	public ResultSet getLoaispRs();
	public void setLoaispRs(ResultSet loaispRs);
	
	public String getHamluong();
	public void setHamluong(String hamluong);
	public String getVisa();
	public void setVisa(String visa);
	public String getNgaycap();
	public void setNgaycap(String ngaycap);
	public String getKkg();
	public void setKkg(String kkg);
	public String getNsx();
	public void setNsx(String nsx);
	public String getNkk();
	public void setNkk(String nkk);
	public String getNxb();
	public void setNxb(String nxb);
	public String getLoaisp();
	public void setLoaisp(String loaisp);
	// bo sung
	public String getTenhoatchat();
	public void setTenhoatchat(String tenhoatchat);
	
	public String getDangtrinhbay();
	public void setDangtrinhbay(String dangtrinhbay);
	
	public String getQuycachdonggoi();
	public void setQuycachdonggoi(String quycachdonggoi);
	
	public String getTieuchuanchatluong();
	public void setTieuchuanchatluong(String tieuchuanchatluong);
	
	public String getNguongocnguyenlieu();
	public void setNguongocnguyenlieu(String nguongocnguyenlieu);
	
	public String getGiabancothue();
	public void setGiabancothue(String giabancothue);
	
	public String getNuocsx();
	public void setNuocsx(String nuocsx);
	
	public String getTieuchuannsx();
	public void setTieuchuannsx(String tieuchuannsx);
	
	public String getGiakktheodvt();
	public void setGiakktheodvt(String giakk);
	
	public String getNhomsanphamHtotc();
	public void setNhomsanphamHtotc(String nhomsphtotc);
	
	public String getStttheoTT40();
	public void setStttheoTT40(String StttheoTT40);
	
	public String getNguongoc();
	public void setNguongoc(String nguongoc);
	
	public String getThuexuat();
	public void setThuetxuat(String thuexuat);
	
	public String getHethanvisa();
	public void setHethanvisa(String hethanvisa);
	
	public String getTenviettat();
	public void setTenviettat(String tenviettat);
	
	public String getTenxuathoadon();
	public void setTenxuathoadon(String tenxuathoadon);
	public String[] getTenxuathoadonArr();
	public void setTenxuathoadonArr(String[] tenxuathoadonArr);
	public String getNguongocnhapkhau();
	public void setNguongocnhapkhau(String nguongocnhapkhau);
	public String[] getNguongocnhapkhauArr();
	public void setNguongocnhapkhauArr(String[] nguongocnhapkhauArr);
	public String[] getNgayArr();
	public void setNgayArr(String[] ngayArr);
	public String[] getThongtinArr();
	public void setThongtinArr(String[] thongtinArr);
	public String[] getGhichuArr();
	public void setGhichuArr(String[] ghichuArr);
}