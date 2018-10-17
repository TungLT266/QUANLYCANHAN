package geso.traphaco.erp.beans.taisancodinh;
import geso.traphaco.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.taisancodinh.IKhauHaoDuKien;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErp_TaiSanCoDinh extends Serializable, IPhan_Trang
{
public String getId();
	
	public String getMa();
	
	public String getDiengiai();
	
	public String getNtsId();
	
	public String getDvt();
	
	public String getSothangKH();
	
	public String getThangbatdauKH();
	
	public String getPpKH();
	
	public String getMsg();
	
	public String getNgaytao();
	public String getNguoitao();
	public String getNgaysua();
	public String getNguoisua();
	
	public void setPhanloai(String phanloai);
	public ResultSet getThanhphan_TSDD();
	public String getPhanloai();
	public void init_convert();
	public int getCurrentPage();
	public void setCurrentPage(int current);	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public String getTrangthai();
	
	public String getSoLuong();
	
	public void setSoLuong(String soluong);
	
	public String getDonGia();
	
	public void setDonGia(String dongia);
	
	public void setThanhTien(String thanhtien);
	
	public String getThanhTien();
	
	public void setId(String id);
	
	public void setMa(String ma);
	
	public void setDiengiai(String diengiai);
	
	public void setMsg(String Msg);
	
	public void setNgaytao(String ngaytao);
	
	public void setNguoitao(String nguoitao);
	
	public void setNgaysua(String ngaysua);
	
	public void setNguoisua(String nguoisua);
	
	public void setTrangthai(String trangthai);
	
	public void setNtsId(String ntsId);
	
	public void setCtyId(String ctyId);
	
	public String getCtyId();
	
	public void setDvt(String dvt);
	
	public void setLtsId(String ltsId);
	
	public String getLtsId();
	
	public void setSothangKH(String sothangKh);
	
	public void setThangbatdauKH(String thangbatdauKh);
	
	public void setPpKH(String ppKh);
	
	public ResultSet getRsts();
	
	public void setRsts(ResultSet rsTs);
	
	public ResultSet getRsLoaitaisan();
	
	public void setRsLoaitaisan(ResultSet LtsList);
	
	public ResultSet getRsNts();
	
	public void setRsNts(ResultSet rsNts);
	
	public ResultSet getRscd();
	
	public ResultSet getRsdvt();
	
	public void setPPKHrS(ResultSet ppkh);
	
	public ResultSet getPPKHrS();

	public void setRsdvt(ResultSet rsDvt);
	
	public void setUserid(String userId);
	
	public String getUserid();
	
	public void setUserTen(String userTen);
	
	public String getUserTen();
		
	boolean themmoiMa(HttpServletRequest request);
	
	boolean UpdateMa(HttpServletRequest request);
	
	public void init(String sql);
	
	public void createRs();
	
	public ResultSet Laykhauhao();
	
	public boolean getEnable();
	
	public boolean Khauhao();
	
	public ResultSet getRsTtcp();

	public void setRsTtcp(ResultSet ttcpRs);

	public void setTtcpIds(String[] ttcpIds);

	public String[] getTtcpIds();

	public ResultSet getRsCdts();

	public void setRsCdts(ResultSet cdtsRs);

	public void setCdtsIds(String[] cdtsIds);

	public String[] getCdtsIds();
	public ResultSet getKMCPList();
	
	public ResultSet getChoncongdung();
	
	
	
	
	public ResultSet getChoncongdungthem();
	

	
	public ResultSet getChonTTCP();
	
	public ResultSet getChonTTCPThem();

	public String getCdIdsList();
	
	public String getTtcpIdsList();
	
	public String getTtcp();
	public void setTtcp(String Ttcp);
	
	public String getLoaitaisan();
	
	
	public ResultSet getRsLapNganSach();
	public void setRsLapNganSach(ResultSet rsLns);
	public String getLapngansachId();
	public void setLapngansachId(String lnsId);
		
	public void Delete(String id);
	public boolean isKhauhao(String tsId);
	void DBClose();
	
	public ResultSet getDieuChinhNguyenGiaRs();

	public void setDieuChinhNguyenGiaRs(ResultSet dieuChinhNguyenGiaRs);
	

	public String getNgayghitang() ;

	public void setNgayghitang(String ngayghitang) ;
	
	public List<IKhauHaoDuKien> getKhauhaodukienList();

	public void setKhauhaodukienList(List<IKhauHaoDuKien> khauhaodukienList);
	
	
	public String getDvthId() ;

	public void setDvthId(String dvthId) ;
	
	public ResultSet getDvthList();

	public void setDvthList(ResultSet dvthList) ;
	
	public ResultSet getDieuChuyenTaiSanRs() ;

	public void setDieuChuyenTaiSanRs(ResultSet dieuChuyenTaiSanRs) ;
	
	

	

	public List<IPhanBo> getPhanBoList() ;

	public void setPhanBoList(List<IPhanBo> phanBoList) ;

	
}
