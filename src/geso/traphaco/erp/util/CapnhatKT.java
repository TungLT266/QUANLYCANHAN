
package geso.traphaco.erp.util;

import geso.traphaco.center.db.sql.Idbutils;
 
 
 
public class CapnhatKT 
{ 
	Idbutils db;
	 
	String ngaychungtu;
	String ngayghinhan;
	String loaichungtu;
	String sochungtu;
	String diengiai;
	String taikhoanNO_fk;
	String taikhoanCO_fk;
	String NOIDUNGNHAPXUAT_FK;
	String NO;
	String CO;
	
	String DOITUONG_NO="";
	String MADOITUONG_NO="";
	String DOITUONG_CO="";
	String MADOITUONG_CO="";
	String LOAIDOITUONG="";
	String  SOLUONG;
	String DONGIA;
	String TIENTEGOC_FK;
	String DONGIANT;
	String TIGIA_FKl;
	String TONGGIATRI="0";
	String TONGGIATRINT="0";
	String khoanmuc="";
	String masp="";
	String tensp="";
	String donvi="";
	String SpId;
	
	String thang;
	String nam;
	String Solo;
	String  KhoXuatID="NULL";
	String  KhoNhanID="NULL";
	String Sohoadon="";
	String Ngayhoadon="";
	
	String ChiPhiId="";
	
	String ngaychotnv;
	
	public String getSpId() {
		return SpId;
	}
	public void setSpId(String spId) {
		SpId = spId;
	}
	public String getKhoXuatID() {
		return KhoXuatID;
	}
	public void setKhoXuatID(String khoXuatID) {
		KhoXuatID = khoXuatID;
	}
	public String getKhoNhanID() {
		return KhoNhanID;
	}
	public void setKhoNhanID(String khoNhanID) {
		KhoNhanID = khoNhanID;
	}
	public Idbutils getDb() {
		return db;
	}
	public void setDb(Idbutils db) {
		this.db = db;
	}
	public String getNgaychungtu() {
		return ngaychungtu;
	}
	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}
	public String getNgayghinhan() {
		return ngayghinhan;
	}
	public void setNgayghinhan(String ngayghinhan) {
		this.ngayghinhan = ngayghinhan;
	}
	public String getLoaichungtu() {
		return loaichungtu;
	}
	public void setLoaichungtu(String loaichungtu) {
		this.loaichungtu = loaichungtu;
	}
	public String getSochungtu() {
		return sochungtu;
	}
	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}
	public String getTaikhoanNO_fk() {
		return taikhoanNO_fk;
	}
	public void setTaikhoanNO_fk(String taikhoanNO_fk) {
		this.taikhoanNO_fk = taikhoanNO_fk;
	}
	public String getTaikhoanCO_fk() {
		return taikhoanCO_fk;
	}
	public void setTaikhoanCO_fk(String taikhoanCO_fk) {
		this.taikhoanCO_fk = taikhoanCO_fk;
	}
	public String getNOIDUNGNHAPXUAT_FK() {
		return NOIDUNGNHAPXUAT_FK;
	}
	public void setNOIDUNGNHAPXUAT_FK(String nOIDUNGNHAPXUAT_FK) {
		NOIDUNGNHAPXUAT_FK = nOIDUNGNHAPXUAT_FK;
	}
	public String getNO() {
		return NO;
	}
	public void setNO(String nO) {
		NO = nO;
	}
	public String getCO() {
		return CO;
	}
	public void setCO(String cO) {
		CO = cO;
	}
	public String getDOITUONG_NO() {
		return DOITUONG_NO;
	}
	public void setDOITUONG_NO(String dOITUONG_NO) {
		DOITUONG_NO = dOITUONG_NO;
	}
	public String getMADOITUONG_NO() {
		return MADOITUONG_NO;
	}
	public void setMADOITUONG_NO(String mADOITUONG_NO) {
		MADOITUONG_NO = mADOITUONG_NO;
	}
	public String getDOITUONG_CO() {
		return DOITUONG_CO;
	}
	public void setDOITUONG_CO(String dOITUONG_CO) {
		DOITUONG_CO = dOITUONG_CO;
	}
	public String getMADOITUONG_CO() {
		return MADOITUONG_CO;
	}
	public void setMADOITUONG_CO(String mADOITUONG_CO) {
		MADOITUONG_CO = mADOITUONG_CO;
	}
	public String getLOAIDOITUONG() {
		return LOAIDOITUONG;
	}
	public void setLOAIDOITUONG(String lOAIDOITUONG) {
		LOAIDOITUONG = lOAIDOITUONG;
	}
	public String getSOLUONG() {
		return SOLUONG;
	}
	public void setSOLUONG(String sOLUONG) {
		SOLUONG = sOLUONG;
	}
	public String getDONGIA() {
		return DONGIA;
	}
	public void setDONGIA(String dONGIA) {
		DONGIA = dONGIA;
	}
	public String getTIENTEGOC_FK() {
		return TIENTEGOC_FK;
	}
	public void setTIENTEGOC_FK(String tIENTEGOC_FK) {
		TIENTEGOC_FK = tIENTEGOC_FK;
	}
	public String getDONGIANT() {
		return DONGIANT;
	}
	public void setDONGIANT(String dONGIANT) {
		DONGIANT = dONGIANT;
	}
	public String getTIGIA_FKl() {
		return TIGIA_FKl;
	}
	public void setTIGIA_FKl(String tIGIA_FKl) {
		TIGIA_FKl = tIGIA_FKl;
	}
	public String getTONGGIATRI() {
		return TONGGIATRI;
	}
	public void setTONGGIATRI(String tONGGIATRI) {
		TONGGIATRI = tONGGIATRI;
	}
	public String getTONGGIATRINT() {
		return TONGGIATRINT;
	}
	public void setTONGGIATRINT(String tONGGIATRINT) {
		TONGGIATRINT = tONGGIATRINT;
	}
	public String getKhoanmuc() {
		return khoanmuc;
	}
	public void setKhoanmuc(String khoanmuc) {
		this.khoanmuc = khoanmuc;
	}
	public String getMasp() {
		return masp;
	}
	public void setMasp(String masp) {
		this.masp = masp;
	}
	public String getTensp() {
		return tensp;
	}
	public void setTensp(String tensp) {
		this.tensp = tensp;
	}
	public String getDonvi() {
		return donvi;
	}
	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}
	public String getThang() {
		return thang;
	}
	public void setThang(String thang) {
		this.thang = thang;
	}
	public String getNam() {
		return nam;
	}
	public void setNam(String nam) {
		this.nam = nam;
	}
	public String getNgaychotnv() {
		return ngaychotnv;
	}
	public void setNgaychotnv(String ngaychotnv) {
		this.ngaychotnv = ngaychotnv;
	}
   
	public String getSolo() {
		return Solo;
	}
	public void setSolo(String solo) {
		Solo = solo;
	}
	 
	public String getSohoadon() {
		return Sohoadon;
	}
	public void setSohoadon(String sohoadon) {
		Sohoadon = sohoadon;
	}
	public String getNgayhoadon() {
		return Ngayhoadon;
	}
	public void setNgayhoadon(String ngayhoadon) {
		Ngayhoadon = ngayhoadon;
	}
	 
	public String getChiPhiId() {
		return ChiPhiId;
	}
	public void setChiPhiId(String chiPhiId) {
		ChiPhiId = chiPhiId;
	}
	
	public String getDiengiai() {
		return diengiai;
	}
	public void setDiengiai(String diengiai_) {
		diengiai = diengiai_;
	}
	
	
	public String CapNhatKeToan_Kho(geso.traphaco.distributor.util.Utility util,Idbutils db){
		try{
			 String msg= util.Update_TaiKhoan_SP_ERP(db,thang,  nam,  ngaychungtu,  ngayghinhan,  loaichungtu,  sochungtu,  taikhoanNO_fk,  taikhoanCO_fk,  NOIDUNGNHAPXUAT_FK,  NO,  CO, 
					 DOITUONG_NO,  MADOITUONG_NO,  DOITUONG_CO,  MADOITUONG_CO,  LOAIDOITUONG,  SOLUONG,  DONGIA,  TIENTEGOC_FK,  DONGIANT,  TIGIA_FKl,  TONGGIATRI,  TONGGIATRINT,  khoanmuc,
					 SpId,  masp,  tensp,  donvi,Solo,  KhoNhanID,  KhoXuatID,  Sohoadon,  Ngayhoadon,  ChiPhiId );
			 
			 return msg;

		}catch(Exception err){
			err.printStackTrace();
			return "Lỗi trong quá trình cập nhật kế toán, vui lòng thử lại, hoặc báo admin để được trợ giúp : "+err.getMessage();
		}
	}
	
}

