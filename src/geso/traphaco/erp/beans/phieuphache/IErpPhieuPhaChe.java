package geso.traphaco.erp.beans.phieuphache;

import geso.traphaco.erp.beans.phieuphache.imp.ThongTinNhapKho;

import java.sql.ResultSet;
import java.util.List;

public interface IErpPhieuPhaChe {
	public void init();
	
	public void createRs();
	
	public boolean create();
	
	public boolean update();
	
	public void DBClose();

	public String getUserId();
	public void setUserId(String userId);

	public String getCongtyId();
	public void setCongtyId(String congtyId);

	public String getId();
	public void setId(String id);

	/*public String getTrangthai();
	public void setTrangthai(String trangthai);*/

	public String getMsg();
	public void setMsg(String msg);

	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getSanpham();
	public void setSanpham(String sanpham);

//	public ResultSet getSanphamRs();
//	public void setSanphamRs(ResultSet sanphamRs);

	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);

	public String getKehoach();
	public void setKehoach(String kehoach);

	public String getNguoiphache();
	public void setNguoiphache(String nguoiphache);

	public String getTailieuphache();
	public void setTailieuphache(String tailieuphache);

	public String getCongthucphache();
	public void setCongthucphache(String congthucphache);

	public String getKhonhap();
	public void setKhonhap(String khonhap);

	public String getSoluongnhap();
	public void setSoluongnhap(String soluongnhap);

	public List<IErpPhieuPhaChe_SanPham> getSanphamList();
	public void setSanphamList(List<IErpPhieuPhaChe_SanPham> sanphamList);

	public ResultSet getKehoachRs();
	public void setKehoachRs(ResultSet kehoachRs);

	public ResultSet getTailieuphacheRs();
	public void setTailieuphacheRs(ResultSet tailieuphacheRs);

	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);
	
	public void createSanphamList();
	
	public List<ThongTinNhapKho> getThongTinNhapKhoList();
	public void setThongTinNhapKhoList(List<ThongTinNhapKho> thongTinNhapKhoList);
	
	public String getKehoachCt();
	public void setKehoachCt(String kehoachCt);
	
	public ResultSet getKehoachChitietRs();
	public void setKehoachChitietRs(ResultSet kehoachChitietRs);
	
	public ResultSet getBinRs();
	public void setBinRs(ResultSet binRs);
	
	public String getThuocloai();
	public void setThuocloai(String thuocloai);

	public String getSanphamId();
	public void setSanphamId(String sanphamId);

	public String getSpIsKqlsl();
	public void setSpIsKqlsl(String spIsKqlsl);
}
