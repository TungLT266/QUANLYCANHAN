package geso.traphaco.erp.beans.kiemdinhchatluong;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErpKiemdinhchatluong_kho  extends IPhan_Trang, Serializable 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getId();
	public void setId(String Id);

	public String getSpId();
	public void setSpId(String spId);

	public String getSpTen();
	public void setSpTen(String spTen);

	public String getSolo();
	public void setSolo(String solo);

	public String getsoluongkhongDat();
	public void setsoluongkhongDat(String soluongkhongDat);

	public String getCongtyId();
	public void setCongtyId(String congtyId);

	public String getTrangThai();
	public void setTrangThai(String trangthai);

	public String getNgayKiem();
	public void setNgayKiem(String ngaykiem);
	
	public String getMsg();
	public void setMsg(String msg);

	public boolean updateKiemdinh(HttpServletRequest request);

	public boolean duyetKiemDinh();

	public void init(String search);

	public void createRs();

	public void DbClose();
	
	public String getKhoId();
	public void setKhoId(String khoId);
	
	public String getKhoChoXuLyId();

	public void setKhoChoXuLyId(String khoId);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public void setRsSolo(ResultSet soloRs);
	
	public ResultSet getRsSolo();
	public void setRsKho(ResultSet khoRs);	
	public ResultSet getRsKho();
	public ResultSet getRsKhoChoXuLy();
	
	public ResultSet getRsSanPham();
	public ResultSet getRsLoaiSanPham();
	
	public String getLOAISPID();

	public void setLOAISPID(String loaispId);
	
	public void setRsKdcl(ResultSet kdclRs);	
	
	public ResultSet getRsKdcl();
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getIsKhuvuc();
	public void setIsKhuvuc(String iskhuvuc);
	
	
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String Hoantatkiemdinh(String id, String userId);
	public String Xoakiemdinh(String id, String userId);
	
	public List<ISpDetail> getSpDetailList();
	public void setSpDetailList(List<ISpDetail> list);
	public void init_sanpham();
	
	
}
