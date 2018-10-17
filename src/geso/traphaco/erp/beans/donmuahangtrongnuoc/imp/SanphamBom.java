package geso.traphaco.erp.beans.donmuahangtrongnuoc.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanphamBom;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;

public class SanphamBom  implements ISanphamBom {

	//Số thứ tự ,là cột PK_SEQ trong bảng ERP_MUAHANG_SP,là cột MUAHANG_SP_FK trong bảng erp_muahang_bom
	private String SoTT;
	private String SpGiacongId;
	private String SpId;
	private String SpMa;
	private String SpTen;
	private String Donvi;
	private String DVDL_FK;
	
	private String Muahangid;
	private String BomId;
	private String soluongdenghi;
	private String soluongnhap;
	private String soluongDM_Thaythe;
	private String Hamam;
	private String Hamluong;
	private String IsHamam;
	private String IsHamluong;
	
	
	List<ISpSanxuatChitiet>  listct =new ArrayList<ISpSanxuatChitiet>();
	List<ISpSanxuatChitiet>  listct_dayc =new ArrayList<ISpSanxuatChitiet>();
	public String getSpGiaCongId() {
		 
		return this.SpGiacongId;
	}

 
	public void setSpGiaCongId(String SpGiaCongId) {
		 this.SpGiacongId=SpGiaCongId;
	}

 
	public String getSpId() {
	 
		return this.SpId;
	}
 
	public void setSpId(String spid) {
	 this.SpId=spid;
		
	}

 
	public String getMasanpham() {
		 
		return this.SpMa;
	}

 
	public void setMasanpham(String masp) {
		 
		this.SpMa=masp;
	}

 
	public String gettensanpham() {
	 
		return this.SpTen;
	}

 
	public void settensanpham(String tensp) {
	 this.SpTen=tensp;
		
	}

	 
	public String getDonVi() {
	 
		return this.Donvi;
	}

 
	public void setDonvi(String donvi) {
		 this.Donvi=donvi;
		
	}

 
	public String getIdMuahang() {
 
		return this.Muahangid;
	}
 
	public void setIdMuahang(String Idmuahang) {
	 
		this.Muahangid=Idmuahang;
	}

 
	public String getDMVTId() {
 
		return this.BomId;
	}

 
	public void setDMVTId(String DMVTId) {
		 this.BomId=DMVTId;
	}

	 
	public String getSoluongdenghi() {
 
		return this.soluongdenghi;
	}

 
	public void setSoluongdenghi(String soluongdenghi_) {
	 
		this.soluongdenghi=soluongdenghi_;
	}

	 
	public String getSoluongnhap() {
		 
		return this.soluongnhap;
	}

	public void setSoluongnhap(String soluongnhap_) {
		 
		this.soluongnhap=soluongnhap_;
	}


	@Override
	public String getSoTT()
	{
		// TODO Auto-generated method stub
		return this.SoTT;
	}


	@Override
	public void setSoTT(String _SoTT)
	{
		// TODO Auto-generated method stub
		this.SoTT=_SoTT;
	}


	@Override
	public List<ISpSanxuatChitiet> getListCtSP() {
		// TODO Auto-generated method stub
		return listct;
	}


	@Override
	public void setListCtSP(List<ISpSanxuatChitiet> list) {
		// TODO Auto-generated method stub
		this.listct=list;
	}


	@Override
	public String getHamluong() {
		// TODO Auto-generated method stub
		return this.Hamluong;
	}


	@Override
	public void setHamluong(String hamluong) {
		// TODO Auto-generated method stub
		this.Hamluong=hamluong;
	}


	@Override
	public String getHamam() {
		// TODO Auto-generated method stub
		return this.Hamam;
	}


	@Override
	public void setHamam(String hamam) {
		// TODO Auto-generated method stub
		this.Hamam=hamam;
	}


	@Override
	public String getIsHamam() {
		// TODO Auto-generated method stub
		return this.IsHamam;
	}


	@Override
	public void setIsHamam(String Ishamam) {
		// TODO Auto-generated method stub
		this.IsHamam=Ishamam;
	}


	@Override
	public String getIsHamluong() {
		// TODO Auto-generated method stub
		return this.IsHamluong;
	} 


	@Override
	public void setIsHamluong(String IsHamluong) {
		// TODO Auto-generated method stub
		this.IsHamluong=IsHamluong;
	}


	@Override
	public String getDVDL_FK() {
		// TODO Auto-generated method stub
		return this.DVDL_FK;
	}


	@Override
	public void setDVDL_FK(String DVDL_FK) {
		// TODO Auto-generated method stub
		this.DVDL_FK =DVDL_FK;
	}


	@Override
	public String getSoluongDM_ThayThe() {
		// TODO Auto-generated method stub
		return this.soluongDM_Thaythe;
	}


	@Override
	public void setSoluongDM_ThayThe(String soluongDM_ThayThe) {
		// TODO Auto-generated method stub
		this.soluongDM_Thaythe=soluongDM_ThayThe;
	}


	@Override
	public List<ISpSanxuatChitiet> getListCtSP_daYC() {
		// TODO Auto-generated method stub
		return listct_dayc;
	}


	@Override
	public void setListCtSP_daYC(List<ISpSanxuatChitiet> list_daYC) {
		// TODO Auto-generated method stub
		listct_dayc=list_daYC;
	}
}
