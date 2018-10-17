package geso.traphaco.erp.beans.donmuahangtrongnuoc;

import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;

import java.util.List;

public interface ISanphamBom {
	
	public String getSpGiaCongId();
	public void setSpGiaCongId(String SpGiaCongId);
	 
	public String getSpId();
	public void setSpId(String spid);

	public String getMasanpham();
	public void setMasanpham(String masp);
	
	public String gettensanpham();
	public void settensanpham(String tensp);
	
	public String getDonVi();
	public void setDonvi(String donvi);
	
	public String getDVDL_FK();
	public void setDVDL_FK(String DVDL_FK);
	
	
	public String getIdMuahang();
	public void setIdMuahang(String Idmuahang);
	
	public String getDMVTId();
	public void setDMVTId(String DMVTId);
	
	public String getSoluongdenghi();
	public void setSoluongdenghi(String soluongdenghi);
	
	public String getSoluongnhap();
	public void setSoluongnhap(String soluongnhap);
	
	public String getSoluongDM_ThayThe();
	public void setSoluongDM_ThayThe(String soluongDM_ThayThe);
	
	
	
	public String getHamluong();
	public void setHamluong(String hamluong);
	public String getHamam();
	public void setHamam(String hamam);
	
	public String getIsHamam();
	public void setIsHamam(String Ishamam);
	
	public String getIsHamluong();
	public void setIsHamluong(String IsHamluong);
	
	
	
	
	//Số thứ tự ,là cột PK_SEQ trong bảng ERP_MUAHANG_SP,là cột MUAHANG_SP_FK trong bảng erp_muahang_bom
	public String getSoTT();
	public void setSoTT(String SoTT);
 
	public List<ISpSanxuatChitiet> getListCtSP();
	public void setListCtSP(List<ISpSanxuatChitiet>  list);
	
	public List<ISpSanxuatChitiet> getListCtSP_daYC();
	public void setListCtSP_daYC(List<ISpSanxuatChitiet>  list_daYC);
}
