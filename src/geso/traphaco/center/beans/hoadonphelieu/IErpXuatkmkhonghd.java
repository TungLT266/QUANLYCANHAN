package geso.traphaco.center.beans.hoadonphelieu;

import geso.traphaco.center.beans.hoadonphelieu.IErpHoaDonPL_SP;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IErpXuatkmkhonghd
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	public String getNgayhoadon();
	public void setNgayhoadon(String ngayhoadon);
	public String getKyhieuhoadon();
	public void setKyhieuhoadon(String kyhieuhd);
	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getThuevat();
	public void setThuevat(String thuevat);
	public String getVat();
	public void setVat(String vat);
	public String getBvat();
	public void setBvat(String bvat);
	public String getAvat();
	public void setAvat(String avat);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public String[] getTensansham();
	public void setTensanpham(String[] tensanpham);
	public String[] getDvt();
	public void setDvt(String[] donvi);
	public String[] getQuyDoi();
	public void setQuyDoi(String[] quyDoi);
	public String[] getSoluong();
	public void setSoluong(String[] soluong);
	public String[] getDongia();
	public void setDongia(String[] dongia);
	public String[] getTongtien();
	public void setTongtien(String[] tongtien);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean create();
	public boolean update();
	
	public void createRS();
	public void init();
	
	public void DbClose();
	
	public List<IErpHoaDonPL_SP> GetSanPhamList();
	public void setSanPhamList(List<IErpHoaDonPL_SP> SanPhamList);
	
	public String CreateLSIN(String ddhId, String loaihd);
	public void initInLS(String lsinId);	
	
	public void setSanphamGhiChu( Hashtable<String, String> sanpham_ghichu );
	public Hashtable<String, String> getSanphamGhiChu();
	
	public String getXuatcho();
	public void setXuatcho(String xuatcho);
	
	public String getMavuviec();
	public void setMavuviec(String mavuviec);
	
	public Hashtable<String, String> getSp_Chitiet();
	public void setSp_Chitiet(Hashtable<String, String> sp_chitiet);
	
	public ResultSet getSoloTheoSp(String spIds, String donvi, String tongluong);


}
