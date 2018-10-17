package geso.traphaco.erp.beans.chiphinhapkhau;

import geso.traphaco.center.util.Erp_Item;

import java.sql.ResultSet;
import java.util.List;

public interface IErpChiphinhapkhau
{
	
	public List<ISanPhamPhanBo> getSpList();
	public void setSpList(List<ISanPhamPhanBo> spList) ;

	public String getUserId();
	public void setUserId(String userId);
	public void loadSanPhamPhanBo();
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getGhichu();
	public void setGhichu(String ghichu);

	public String getNgaynhap();
	public void setNgaynhap(String tungay);

	public String getTigia();
	public void setTigia(String tigia);
	
	public String getTienteId();
	public void setTienteId(String tienteId);
	public ResultSet getTienteRs();
	public void setTienteRs(ResultSet tienteRs);
	
	public String getPnkId();
	public void setPnkId(String khId);
	public ResultSet getPhieunhapRs();
	public void setPhieunhapRs(ResultSet pnRs);
	
	
	public String getSoToKhaiId();
	public void setSoToKhaiId(String SoToKhaiId);
	public ResultSet getSoToKhaiRs();
	public void setSoToKhaiRs(ResultSet SoToKhairs);
	
	
	public String[] getDiengiai();
	public void setDiengiai(String[] diengiai);
	public String[] getMaHD();
	public void setMaHD(String[] maHD);
	public String[] getMausoHD();
	public void setMausoHD(String[] masoHD);
	public String[] getKyhieu();
	public void setKyhieu(String[] kyhieu);
	public String[] getSochungtu();
	public void setSochungtu(String[] sochungtu);
	public String[] getNgaychungtu();
	public void setNgaychungtu(String[] ngayct);
	public String[] getNhacungcap();
	public void setNhacungcap(String[] nhacc);
	public String[] getDiaChiNCC();
	public void setDiaChiNCC(String[] diaChiNCC);
	public String[] getTienhang();
	public void setTienhang(String[] tienhang);
	public String[] getThuesuat();
	public void setThuesuat(String[] thuesuat);
	public String[] getTienthue();
	public void setTienthue(String[] tienthue);
	public String[] getTongtien();
	public void setTongtien(String[] tongtien);
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet pnRs);
 
	public void setNcc(String ncc); 
	
	public String getNcc();
	
	public void setNccId(String nccId); 
	
	public String getNccId();

	public String[] getMst();
	
	public void setMst(String[] mst);		
	
	public String getMsg();
	
	public void setMsg(String msg);
	
	public boolean Create();
	public boolean Update();
	
	public void createRs();
	public void init();
	
	public void setNcc_cn(String ncc_cn); 
	
	public String getNcc_cn();
	
	public void setNccId_cn(String nccId_cn); 
	
	public String getNccId_cn();
	
	public void DbClose();
	
	// Them vao 3 o tong tien
	public String getTongtienhang();
	public void setTongtienhang(String Tongtienhang);
	
	public String getTongtienthue();
	public void setTongtienthue(String Tongtienthue);
	
	public String getTongtien_AVAT();
	public void setTongtien_AVAT(String Tongtien_VAT);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getPnkIds();
	
	public void setPnkIds(String pnkIds);
	
	public List<Erp_Item> getSanPhamKhoList();
	
	public void setSoChungTu_Chu(String soChungTu_Chu);
	public String getSoChungTu_Chu();

	public void setSoChungTu_So(String soChungTu_So);
	public String getSoChungTu_So();
}