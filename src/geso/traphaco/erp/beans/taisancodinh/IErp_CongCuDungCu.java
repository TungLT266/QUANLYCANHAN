package geso.traphaco.erp.beans.taisancodinh;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErp_CongCuDungCu
{
	public String getId();
	
	public String getMa();
	
	public String getDiengiai();
	
	public String getNccdcId();
	
	public String getDvt();
	
	public String getSothangKH();
	
	public String getThangbatdauKH();
	
	public String getPpKH();
	
	public String getMsg();
	
	public String getNgaytao();
	public String getNguoitao();
	public String getNgaysua();
	public String getNguoisua();
	
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
	
	public void setNccdcId(String nccdcId);
	
	public void setCtyId(String ctyId);
	
	public String getCtyId();
	
	public void setDvt(String dvt);
	
	public void setLccdcId(String LccdcId);
	
	public String getLccdcId();
	
	public void setLccdcTen(String lccdcTen);
	
	public String getLccdcTen();
	
	public void setSothangKH(String sothangKh);
	
	public void setThangbatdauKH(String thangbatdauKh);
	
	public void setPpKH(String ppKh);
	
	public ResultSet getRsCcdc();
	
	public void setRsCcdc(ResultSet rsCcdc);
	
	public ResultSet getRsLoaiCCDC();
	
	public void setRsLoaiCCDC(ResultSet LtsList);
	
	public ResultSet getRsNccdc();
	
	public void setRsNccdc(ResultSet rsNccdc);
	
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

	public ResultSet getRsCdccdc();

	public void setRsCdccdc(ResultSet cdccdcRs);

	public void setCdccdcIds(String[] cdccdcIds);

	public String[] getCdccdcIds();
	
	public ResultSet getChoncongdung();
	
	public ResultSet getChonkhoanmucchiphi();
	
	public ResultSet getChoncongdungthem();
	
	public ResultSet getChonTTCP();
	
	public ResultSet getChonTTCPThem();

	public String getCdIdsList();
	
	public String getTtcpIdsList();
	
	public String getTtcp();
	
	public void setTtcp(String ttcp);
	
	public String getLoaiCCDC();
	
	public void Delete(String id);
	
	public boolean isKhauhao(String tsId);
	
	

	
	public ResultSet getDvthList();

	public void setDvthList(ResultSet dvthList);
	
	
	public String getDvthId();

	public void setDvthId(String dvthId);

	public List<IPhanBo> getPhanBoList();

	public void setPhanBoList(List<IPhanBo> phanBoList) ;

	public ResultSet getDieuChuyenCCDCRs();

	public void setDieuChuyenCCDCRs(ResultSet dieuChuyenCCDCRs);

	
	void DBClose();
}
