package geso.traphaco.erp.beans.tieuchuankiemnghiem.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IErpTieuChuanKiemNghiemList;

public class ErpTieuChuanKiemNghiemList implements IErpTieuChuanKiemNghiemList {
	String congtyId;
	String userId;
	String userTen;
	String id;
	String ma;
	String ten;
	String ngaycap;
	String tungay;
	String denngay;
	String loaispId;
	String sanphamId;
	String hosomau;
	String hosokiemnghiem;
	String phieukiemnghiem;
	String ishoatdong;
	String msg;
	String maTieuChuan;
	String maSanPham;
	
	ResultSet tieuchuanRs;
	dbutils db;
	
	public ErpTieuChuanKiemNghiemList (){
		 this.congtyId ="";
		 this.userId="";
		 this.id="";
		 this.userTen="";
		 this.ma="";
		 this.ten="";
		 this.ngaycap="";
		 this.tungay="";
		 this.denngay="";
		 this.loaispId="";
		 this.sanphamId="";
		 this.hosomau="";
		 this.hosokiemnghiem="";
		 this.phieukiemnghiem="";
		 this.ishoatdong="";
		 this.msg="";
		 this.maSanPham="";
		 this.maTieuChuan="";
		this.db = new dbutils();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserTen() {
		return userTen;
	}
	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getNgaycap() {
		return ngaycap;
	}
	public void setNgaycap(String ngaycap) {
		this.ngaycap = ngaycap;
	}
	public String getTungay() {
		return tungay;
	}
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	public String getDenngay() {
		return denngay;
	}
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	public String getLoaispId() {
		return loaispId;
	}
	public void setLoaispId(String loaispId) {
		this.loaispId = loaispId;
	}
	public String getSanphamId() {
		return sanphamId;
	}
	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}
	public String getHosomau() {
		return hosomau;
	}
	public void setHosomau(String hosomau) {
		this.hosomau = hosomau;
	}
	public String getHosokiemnghiem() {
		return hosokiemnghiem;
	}
	public void setHosokiemnghiem(String hosokiemnghiem) {
		this.hosokiemnghiem = hosokiemnghiem;
	}
	public String getPhieukiemnghiem() {
		return phieukiemnghiem;
	}
	public void setPhieukiemnghiem(String phieukiemnghiem) {
		this.phieukiemnghiem = phieukiemnghiem;
	}
	public String getIshoatdong() {
		return ishoatdong;
	}
	public void setIshoatdong(String ishoatdong) {
		this.ishoatdong = ishoatdong;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public void init() {
		
		String query = " select tc.pk_seq TCID, tc.ma TCMA, sp.ma SPMA,"
				+ " case tc.trangthai when 1 then N'Hoạt động' else N'Ngưng hoạt động' end "
				+ " tctrangthai,"
				+ " tc.ngaysua ngaysuatc, tc.ma TCMA, tc.ngaycap, sp.ten spten, nvsua.ten nvsuaten\r\n" + 
				"from ERP_TIEUCHUANKIEMGNGHIEM tc\r\n" + 
				"inner join erp_sanpham sp on tc.sanpham_fk = sp.pk_Seq\r\n" + 
				"inner join nhanvien nvsua on tc.nguoisua = nvsua.pk_seq  ";
		
		if(this.tungay.trim().length()>0){
			query += " and tc.ngaycap >= "+this.tungay+" ";
		}
		
		if(this.denngay.trim().length()>0){
			query += " and tc.ngaycap <= "+this.denngay+" ";
		}
		
		if(this.maSanPham.trim().length()>0){
			query += " and sp.ma like '%"+this.maSanPham+"%' ";
		}
		
		if(this.maTieuChuan.trim().length()>0){
			query += " and tc.ma like '%"+this.maTieuChuan+"%' ";
		}
		
		query += " order by tc.pk_seq desc ";
		System.out.println("query: " + query);
		this.tieuchuanRs = this.db.get(query);
	}

	public ResultSet getTieuchuanRs() {
		return tieuchuanRs;
	}

	public void setTieuchuanRs(ResultSet tieuchuanRs) {
		this.tieuchuanRs = tieuchuanRs;
	}

	public void Delete(String id, String userId) {
		
		String query = " update ERP_TIEUCHUANKIEMGNGHIEM set trangthai = 0, ishoatdong = 0 where pk_seq = "+id+" ";
		
		if(!db.update(query)) {
			this.msg = "Không thể tiêu chuẩn: " + query;
			return;
		}
	}

	public String getMaTieuChuan() {
		return maTieuChuan;
	}

	public void setMaTieuChuan(String maTieuChuan) {
		this.maTieuChuan = maTieuChuan;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}
}
