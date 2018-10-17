package geso.traphaco.erp.beans.donbanhang;

import geso.traphaco.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IErp_DonbanhangList extends  Serializable, IPhan_Trang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getNguoitaoTen();
	public void setNguoitaoTen(String ten);
	public String getUserId();
	public void setUserId(String userId);

	public String getTen();
	public void setTen(String ten);
	
	public String getSKU();
	public void setSKU(String sku);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getLoaiDonHang();
	public void setLoaiDonHang(String loaidonhang);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMaspstr();
	public void setMaspstr(String maspstr);
	
	public String getKvId();	
	public void setKvId(String kvId);
	public ResultSet getKvList();
	public void setKvList(ResultSet kvList);
	
	public String getSO();
	public void setSO(String so);

	public ResultSet getDhList();
	public void setDhList(ResultSet dhList);
	
	public ResultSet getKhoDatRs();
	public void setKhoDatRs(ResultSet khodatrs);
	public String getKhodatId();	
	public void setKhodatId(String khodatid);
	
	public String getMalist();
	public void setMalist(String malist);
		
	public void createDdhlist(String querystr);
	public void createDdhKTlist(String querystr);
	
	/*
	 * Duyet don hang trang thai da chot.Cap tren phai duyet 
	 *  
	 */
	public void createDuyetDonHangRs(String query);
	
	public ResultSet getDuyetDonHangRs();
	public void setDuyetDonHangRs(ResultSet duyetDonHangRs);
	
	
	
	public void createDthlist(String querystr);
	public ResultSet getDdhKTList();
	
	public void SetMsg(String msg);
	public String getMsg();	
	public  ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	public String getDvkd();
	public void setDvkd(String dvkd);
	
	public void setIsDhKhuyenMai(String isKm);
	public String geIsDhKhuyenMai();
	
	public void setNguoiduyet(String nguoiduyet);
	public String getNguoiduyet();
	
	public ResultSet getHopDongRs();
	public void setHopDongRs(ResultSet hdRs);
	public String getHopdongId();
	public void setHopdongId(String hdId);

	public ResultSet getKhachHangRs();
	public void setKhachHangRs(ResultSet rs);
	public String getKhachHangId();
	public void setKhachHangId(String id);

	public ResultSet getNguoiTaoRs();
	public void setNguoiTaoRs(ResultSet rs);
	public String getNguoiTaoId();
	public void setNguoiTaoId(String id);
	
	
	public void DBclose();
	public void createNguoiDuyet(String string);	
	
	
	public String getIDKenhBanHang();
	public void setIDKenhBanHang(String IDkenh);
	
	public String getduyetDH();
	public void setduyetDH(String duyetdh);
	
	public ResultSet getKenhbhRs();
	public void setKenhbhRs(ResultSet kenhbhrs);
	public String getKenhbhId();	
	public void setKenhbhId(String kenhbhid);
	
	public String getSohoadon();	
	public void setSohoadon(String sohoadon);
	
	public String getPhieuxuatkhoId();	
	public void setPhieuxuatkhoId(String phieuxuatkhoId);
	
	public String getngaytaodh();	
	public void setngaytaodh(String ngaytaodh);
	
	public String getDateTime();
}