package geso.traphaco.center.util;

import geso.traphaco.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DKLocBaoCaoKeToan
{
	private String tuNgay;
	private String denNgay;
	private String congTyId;
	private String soHieuTaiKhoanNo;
	private String soHieuTaiKhoanCo;
	private String loaiDoiTuongNoId;
	private String nhomKhachHangId;
	private String loaiDoiTuongCoId;
	private String doiTuongNoId;
	private String doiTuongCoId;
	private String loaiChungTu;
	private String soChungTu;
	private String nkh_fk;
	private String quay_fk;
	
	private List<Erp_Item> congTyList;
	private List<Erp_Item> taiKhoanList;
	private List<Erp_Item> taiKhoanCongNoList;
	private List<Erp_Item> loaiDoiTuongList;
	private List<Erp_Item> doiTuongNoList;
	private List<Erp_Item> quayList;
	private List<Erp_Item> nhomTaiKhoanList;
	private List<Erp_Item> nhomKhachHangList;
	private List<Erp_Item> doiTuongCoList;
	private String nhomTaiKhoanId;
	ResultSet khRs;
	
	// Loc nhom
	private String loaiNhom;
	private String nhomDoiTuongId;
	private List<Erp_Item> nhomDoiTuong;
	public DKLocBaoCaoKeToan()
	{
		this.denNgay = Utility.getCurrentDate();
		this.tuNgay = denNgay;
//		this.tuNgay = denNgay.split("-")[0] + "-" + denNgay.split("-")[1] + "-01";
		this.congTyId = "1";
		this.soHieuTaiKhoanNo = "";
		this.soHieuTaiKhoanCo = "";
		this.loaiDoiTuongNoId = "";
		this.loaiDoiTuongCoId = "";
		this.doiTuongNoId = "";
		this.nkh_fk="";
		this.quay_fk="";
		this.doiTuongCoId = "";
		this.loaiChungTu = "";
		this.soChungTu = "";
		this.nhomKhachHangId="";
		this.congTyList = new ArrayList<Erp_Item>();
		this.taiKhoanList = new ArrayList<Erp_Item>();
		this.taiKhoanCongNoList = new ArrayList<Erp_Item>();
		this.loaiDoiTuongList = new ArrayList<Erp_Item>();
		this.doiTuongNoList = new ArrayList<Erp_Item>();
		this.doiTuongCoList = new ArrayList<Erp_Item>();
		this.nhomKhachHangList=new ArrayList<Erp_Item>();
		this.quayList=new ArrayList<Erp_Item>();
		this.nhomDoiTuong = new ArrayList<Erp_Item>();
		this.loaiNhom = "";//Khách hàng
		this.nhomDoiTuongId = "";
		this.nhomTaiKhoanId = "";
		this.nhomTaiKhoanList = new  ArrayList<Erp_Item>();
	}

	public void init(Idbutils db)
	{
		String query = "select npp.pk_seq, npp.MA + ' - ' + npp.ten as ten from NHAPHANPHOI npp where npp.trangThai = 1 and npp.isKhachHang = 0";
		Erp_Item.getListFromQuery(db, query, this.congTyList);
		
		
		query = "select npp.pk_seq, npp.MA + ' - ' + npp.ten as ten from NHAPHANPHOI npp where npp.congnochung=1 ";
		System.out.println("quay :"+query);
		Erp_Item.getListFromQuery(db, query, this.quayList);
		
		
		
		//Dùng cho báo cáo sổ cái hk comment lại
		query = "select tk.SOHIEUTAIKHOAN as pk_seq, tk.SOHIEUTAIKHOAN + ' - ' + tk.TENTAIKHOAN as ten from ERP_TAIKHOANKT tk where tk.TRANGTHAI = 1 and npp_fk = " + (this.congTyId.trim().length() > 0 ? this.congTyId : "1") + " order by tk.soHieuTaiKhoan";
		Erp_Item.getListFromQuery(db, query, this.taiKhoanList);
		
		query = "select tk.SOHIEUTAIKHOAN as pk_seq, tk.SOHIEUTAIKHOAN + ' - ' + tk.TENTAIKHOAN as ten from ERP_TAIKHOANKT tk where tk.trangThai = 1 and tk.taiKhoanCoChiTiet = 1 and npp_fk = " + (this.congTyId.trim().length() > 0 ? this.congTyId : "1") + " order by tk.soHieuTaiKhoan";
		Erp_Item.getListFromQuery(db, query, this.taiKhoanCongNoList);
		
		query = "select distinct loai as pk_seq, loai TEN from dmdoituong2 ";
		Erp_Item.getListFromQuery(db, query, this.loaiDoiTuongList);
		
		
		
		query = "select distinct pk_seq, diengiai TEN from NHOMKHACHHANGNPP where trangthai=1";
		System.out.println("aaaaaaaaa"+query);
		Erp_Item.getListFromQuery(db, query, this.nhomKhachHangList);

/*		query = "select pk_seq,ma + ' - ' + ten as ten from dmdoituong where loai = N'" + this.loaiDoiTuongNoId + "'\n";
		if (this.loaiDoiTuongNoId.trim().contains("Khách hàng"))
			if (this.congTyId.trim().equals("1"))
				query += "and isKhachHangHO = 1";
			else if (this.congTyId.trim().length() > 0)
				query += "and CONGTY_FK = " + this.congTyId;
	
		System.out.println("::: LAY LOAI DOI TUONG: " + query);
		Erp_Item.getListFromQuery(db, query, this.doiTuongNoList);
*/		query  = "select nhom.pk_seq, nhom.MA + ' - ' + nhom.diengiai as ten from GROUP_TAIKHOAN_NHOM nhom where trangthai = 1 " ;
		System.out.println("nhom tai khoan  :"+query);
		Erp_Item.getListFromQuery(db, query, this.nhomTaiKhoanList);
		
		query = "";
		System.out.println("Loai nhom " + this.loaiNhom);
		
		if(this.loaiNhom.equals("0")){
			query = "select distinct pk_seq,ISNULL(TEN,'') + ' - ' + diengiai TEN from NHOMKHACHHANGNPP where trangthai=1";
		}else if(this.loaiNhom.equals("1")){
			query = "select distinct pk_seq, ISNULL(TEN,'') + ' - ' + diengiai TEN from ERPNHOMNV where trangthai=1";
		}else if(this.loaiNhom.equals("2")){
			query = "select distinct pk_seq, ISNULL(TEN,'') + ' - ' + diengiai TEN  from NHOMNHACUNGCAPCN where trangthai=1";
		}else if ( this.loaiNhom.equals("3")) {
			query = "select distinct pk_seq, ISNULL(TEN,'') + ' - ' + diengiai TEN  from ErpNhomDTKhac where trangthai=1";
		}

		if(query.trim().length()> 0)
		{
			System.out.println("aaaaaaabbbbaa"+query);
			Erp_Item.getListFromQuery(db, query, this.nhomDoiTuong);
		}
		
		
		System.out.println("CongTy Id :" +this.congTyId);
		if (this.loaiDoiTuongNoId.trim().length() > 0)
		{
			if (this.loaiDoiTuongNoId.trim().contains("Khách hàng")){
				
				
				if(this.congTyId.trim().length() > 0 && !this.congTyId.trim().equals("1")){
					query = "SELECT     N'Khách hàng' AS LOAI, maFAST AS ma,maFAST + ' - ' + TEN AS TEN, pk_seq, npp_fk, 1 as isKhachHangHO, npp_fk congty_fk " +
							"FROM  LINK_DMS_THAT_NOIBO.DataCenter.dbo.KHACHHANG ";
					query += " and NPP_FK = " + this.congTyId + "";
				}else if(this.congTyId.trim().length() > 0 && this.congTyId.trim().equals("1")){

						query = "SELECT     N'Khách hàng' AS LOAI, maFAST as ma,maFAST + ' - ' + TEN AS TEN, pk_seq, 1 AS NPP_FK, '1' AS isKhachHangHO, 1 congty_fk " +
						"FROM         nhaPhanPhoi where iskhachhang='1'";
				}
				
			}else if (this.loaiDoiTuongNoId.trim().contains("Chi nhánh/Đối tác")){
				
				query = "SELECT     N'Chi nhánh/Đối tác' AS LOAI, maFAST as ma,maFAST + ' - ' + TEN AS TEN, pk_seq, 1 AS NPP_FK, '0' AS isKhachHangHO, 1 congty_fk " +
						"FROM         nhaPhanPhoi ";
				
			}else if (this.loaiDoiTuongNoId.trim().contains("Nhà cung cấp")){
				
				query = "SELECT     N'Nhà cung cấp' AS LOAI, ma,ma + ' - ' + TEN AS TEN, pk_seq, npp_fk, 0 AS isKhachHangHO, npp_fk congty_fk " +
						"FROM         erp_nhacungcap"	;
				
			}else if (this.loaiDoiTuongNoId.trim().contains("Nhân viên")){
				
				query = "SELECT     N'Nhân viên' AS LOAI, ma, MA + ' - ' + TEN AS TEN, pk_seq, NPP_FK, '0' AS ISKHACHHANGHO, npp_fk congty_fk " +
						"FROM ERP_NHANVIEN"	;
				
			}else if (this.loaiDoiTuongNoId.trim().contains("Ngân hàng")){
				
				query = "SELECT N'Ngân hàng' AS LOAI, ma,MA + ' - '+ TEN AS TEN, pk_seq, 1 AS NPP_FK, '0' AS ISKHACHHANGHO, 1 congty_fk " +
						"FROM ERP_NGANHANG"	;
				
			}else if (this.loaiDoiTuongNoId.trim().contains("Tài sản cố định")){
				
				query = "SELECT N'Tài sản cố định' AS LOAI, ma,MA + ' - '+ TEN AS TEN, pk_seq, 1 AS NPP_FK, '0' AS ISKHACHHANGHO, 1 congty_fk " +
						"FROM ERP_TAISANCODINH";
				
			}else if (this.loaiDoiTuongNoId.trim().contains("Đối tượng khác")){
				
				query = "SELECT N'Đối tượng khác' AS LOAI, MADOITUONG AS MA,MADOITUONG + ' - '+ TENDOITUONG AS TEN, PK_SEQ, NPP_FK, '0' AS ISKHACHHANGHO, npp_fk congty_fk " +
						"FROM ERP_DOITUONGKHAC" ;	
			}
			System.out.println(query);
//			this.khRs = db.get(query);
			String[] param = {"pk_seq", "TEN", "ma"};
			Erp_Item.getListFromQuery_3(db, query, this.doiTuongNoList, param);
			
		}
	}
	
	
	
	public List<Erp_Item> getNhomTaiKhoanList() {
		return nhomTaiKhoanList;
	}

	public void setNhomTaiKhoanList(List<Erp_Item> nhomTaiKhoanList) {
		this.nhomTaiKhoanList = nhomTaiKhoanList;
	}

	public String getNhomTaiKhoanId() {
		return nhomTaiKhoanId;
	}

	public void setNhomTaiKhoanId(String nhomTaiKhoanId) {
		this.nhomTaiKhoanId = nhomTaiKhoanId;
	}

	public ResultSet getKhRs() {
		return khRs;
	}

	public void setKhRs(ResultSet khRs) {
		this.khRs = khRs;
	}

	public String getLoaiNhom() {
		return loaiNhom;
	}

	public void setLoaiNhom(String loaiNhom) {
		this.loaiNhom = loaiNhom;
	}

	public String getNhomDoiTuongId() {
		return nhomDoiTuongId;
	}

	public void setNhomDoiTuongId(String nhomDoiTuongId) {
		this.nhomDoiTuongId = nhomDoiTuongId;
	}

	public List<Erp_Item> getNhomDoiTuong() {
		return nhomDoiTuong;
	}

	public void setNhomDoiTuong(List<Erp_Item> nhomDoiTuong) {
		this.nhomDoiTuong = nhomDoiTuong;
	}

	public ResultSet getKHRS(){
		return this.khRs;
	}
	
	public String getTenCongTy(Idbutils db)
	{
		String tenCongTy = "";
		try {
			String query = "select ten from nhaPhanPhoi tk where pk_seq = " + this.congTyId;
			
			ResultSet rs = db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
					tenCongTy = rs.getString(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenCongTy;
	}
	
	public String getTenTaiKhoanNo(Idbutils db)
	{
		String tenTaiKhoanNo = "";
		try {
			String query = "select soHieuTaiKhoan + ' - ' + TENTAIKHOAN ten from erp_TaiKhoanKT tk where trangThai = 1 and tk.npp_fk = " + this.congTyId + " and soHieuTaiKhoan = '" + this.soHieuTaiKhoanNo + "'";
			
			ResultSet rs = db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
					tenTaiKhoanNo = rs.getString(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenTaiKhoanNo;
	}
	
	public String getTenTaiKhoanCo(Idbutils db)
	{
		String tenTaiKhoanCo = "";
		try {
			String query = "select soHieuTaiKhoan + ' - ' + TENTAIKHOAN ten from erp_TaiKhoanKT tk where trangThai = 1 and tk.npp_fk = " + this.congTyId + " and soHieuTaiKhoan = '" + this.soHieuTaiKhoanCo + "'";
			
			ResultSet rs = db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
					tenTaiKhoanCo = rs.getString(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenTaiKhoanCo;
	}
	
	public String getTuNgay() {
		return tuNgay;
	}

	public void setTuNgay(String tuNgay) {
		this.tuNgay = tuNgay;
	}

	public String getDenNgay() {
		return denNgay;
	}

	public void setDenNgay(String denNgay) {
		this.denNgay = denNgay;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getSoHieuTaiKhoanNo() {
		return soHieuTaiKhoanNo;
	}

	public void setSoHieuTaiKhoanNo(String soHieuTaiKhoanNo) {
		this.soHieuTaiKhoanNo = soHieuTaiKhoanNo;
	}

	public String getSoHieuTaiKhoanCo() {
		return soHieuTaiKhoanCo;
	}

	public void setSoHieuTaiKhoanCo(String soHieuTaiKhoanCo) {
		this.soHieuTaiKhoanCo = soHieuTaiKhoanCo;
	}

	public String getLoaiDoiTuongNoId() {
		return loaiDoiTuongNoId;
	}

	public void setLoaiDoiTuongNoId(String loaiDoiTuongNoId) {
		this.loaiDoiTuongNoId = loaiDoiTuongNoId;
	}

	public String getLoaiDoiTuongCoId() {
		return loaiDoiTuongCoId;
	}

	public void setLoaiDoiTuongCoId(String loaiDoiTuongCoId) {
		this.loaiDoiTuongCoId = loaiDoiTuongCoId;
	}

	public String getDoiTuongNoId() {
		return doiTuongNoId;
	}

	public void setDoiTuongNoId(String doiTuongNoId) {
		this.doiTuongNoId = doiTuongNoId;
	}

	public String getDoiTuongCoId() {
		return doiTuongCoId;
	}

	public void setDoiTuongCoId(String doiTuongCoId) {
		this.doiTuongCoId = doiTuongCoId;
	}

	public String getLoaiChungTu() {
		return loaiChungTu;
	}

	public void setLoaiChungTu(String loaiChungTu) {
		this.loaiChungTu = loaiChungTu;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public List<Erp_Item> getCongTyList() {
		return congTyList;
	}

	public void setCongTyList(List<Erp_Item> congTyList) {
		this.congTyList = congTyList;
	}

	public List<Erp_Item> getTaiKhoanList() {
		return taiKhoanList;
	}

	public void setTaiKhoanList(List<Erp_Item> taiKhoanList) {
		this.taiKhoanList = taiKhoanList;
	}

	public List<Erp_Item> getLoaiDoiTuongList() {
		return loaiDoiTuongList;
	}

	public void setLoaiDoiTuongList(List<Erp_Item> loaiDoiTuongList) {
		this.loaiDoiTuongList = loaiDoiTuongList;
	}

	public List<Erp_Item> getDoiTuongNoList() {
		return doiTuongNoList;
	}

	public void setDoiTuongNoList(List<Erp_Item> doiTuongNoList) {
		this.doiTuongNoList = doiTuongNoList;
	}

	public List<Erp_Item> getDoiTuongCoList() {
		return doiTuongCoList;
	}

	public void setDoiTuongCoList(List<Erp_Item> doiTuongCoList) {
		this.doiTuongCoList = doiTuongCoList;
	}

	public void setTaiKhoanCongNoList(List<Erp_Item> taiKhoanCongNoList) {
		this.taiKhoanCongNoList = taiKhoanCongNoList;
	}

	public List<Erp_Item> getTaiKhoanCongNoList() {
		return taiKhoanCongNoList;
	}

	public String getNkh_fk() {
		return nkh_fk;
	}

	public void setNkh_fk(String nkh_fk) {
		this.nkh_fk = nkh_fk;
	}

	public List<Erp_Item> getNhomKhachHangList() {
		return nhomKhachHangList;
	}

	public void setNhomKhachHangList(List<Erp_Item> nhomKhachHangList) {
		this.nhomKhachHangList = nhomKhachHangList;
	}

	public String getNhomKhachHangId() {
		return nhomKhachHangId;
	}

	public void setNhomKhachHangId(String nhomKhachHangId) {
		this.nhomKhachHangId = nhomKhachHangId;
	}

	public String getQuay_fk() {
		return quay_fk;
	}

	public void setQuay_fk(String quay_fk) {
		this.quay_fk = quay_fk;
	}

	public List<Erp_Item> getQuayList() {
		return quayList;
	}

	public void setQuayList(List<Erp_Item> quayList) {
		this.quayList = quayList;
	}
}