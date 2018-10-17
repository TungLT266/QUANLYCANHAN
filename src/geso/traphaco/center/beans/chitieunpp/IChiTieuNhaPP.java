package geso.traphaco.center.beans.chitieunpp;
import geso.traphaco.center.beans.chitieunpp.imp.ChiTieuDDKD;
import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IChiTieuNhaPP extends  Serializable, IPhan_Trang 
{
	public void setChitieu(double chitieu);
	public double getChitieu();

	public void setID(double id);
	public double getID();
 
	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	public void setThang(int thang);
	public int getThang();
	public void setNam(int nam);
	public int getNam();
	public void setKhuVucID(String khuvucid);
	public String getKhuVucID();
	public void setTenKhuVuc(String tenkhuvuc);
	public  String getTenKhuVuc();
	public void setMessage(String strmessage);
	public String getMessage();
	public void setSoNgayLamViec(String songaylamviec);
	public String getSoNgayLamViec();
	
	public String getVungId();
	public void setVungId(String vungId);
	
	public String getKhuvucId();
	public void setKhuvucId(String khuvucId);
	
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet khuvucRs);
	
	
 
	public void  setNgayKetThuc(String ngayketthuc);
	public String getNgayKetThuc();
	 
	public void setNgayTao(String ngaytao);
	public String getNgayTao();
 
	public void setNgaySua(String nguoisua);
	public String getNgaySua();
	
	 
	public void setTenNPP(String TenNPP);
	public String getTenNPP();
	 
	public void setUserId(String userid);
	public String getUserId();
    public String getDVKDId();
    public void setDVDKId(String dvkdid);
    public String getTenDVKD();
    public void setTenDVKD(String tendvkd);
	public void setDienGiai(String diengiai);
	public String getDienGiai();

	public boolean EditChiTieu(HttpServletRequest request);
 
	 public String[] getNhomSp();
	 public String[] getNhomSpId();
	public ResultSet getListChiTieu();
	public ResultSet getChitieuDDKd();
	
	public void setListChiTieu(String sql);
	public List<ChiTieuDDKD> getListChiTieuDDKD();
	public void setListChiTieuDDKD(List<ChiTieuDDKD> list);
	public void setKenhId(String kenhid);
	public String getKenhId();
	public ResultSet getRsKenh();
 
	public void setTrangThai(String trangthai);
	public String getTrangThai();
	public boolean ChotChiTieu();
	public void DBclose();
	
	public void init();
	 public ResultSet getrsdvkd();
	 public void setrsdvkd(ResultSet rsdvkd);
}
