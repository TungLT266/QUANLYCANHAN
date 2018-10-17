package geso.traphaco.erp.beans.kehoachphache.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaCheList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpKeHoachPhaCheList implements IErpKeHoachPhaCheList {
	private String userId;
	private String congtyId;
	private String ngayKeHoach;
	private String boPhanThucHien;
	private String loai;
	private String sanPham;
	private String trangthai;
	private String msg;
	
	private ResultSet KhuvucSXRs;
	private ResultSet sanphamRs;
	private ResultSet KehoachphacheRs;
	dbutils db;
	
	public ErpKeHoachPhaCheList() {
		this.ngayKeHoach = "";
		this.boPhanThucHien = "";
		this.loai = "";
		this.sanPham = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select khpc.pk_seq, khpc.ngaykehoach, pbsx.ma + ' - ' + pbsx.ten as phongbansx, khpc.loai,"
				+ " sp.ma + ' - ' + sp.ten as sanpham, khpc.trangthai, nvt.TEN as nguoitao, nvs.TEN as nguoisua, khpc.ngaytao, khpc.ngaysua"
				+ " from ERP_KEHOACHPHACHE khpc inner join nhanvien nvt on nvt.PK_SEQ = khpc.nguoitao"
				+ " inner join nhanvien nvs on nvs.PK_SEQ = khpc.nguoisua"
				+ " left join ERP_PHONGBANSX pbsx on pbsx.pk_seq = khpc.phongbansx_fk"
				+ " left join ERP_SANPHAM sp on sp.pk_seq = khpc.sanpham_fk"
				+ " where khpc.congty_fk = " + this.congtyId + "order by khpc.ngaykehoach desc, khpc.pk_seq desc";
		
		if(this.ngayKeHoach.length() > 0){
			query += " and khpc.ngaykehoach like '%" + this.ngayKeHoach + "%'";
		}
		
		if(this.boPhanThucHien.length() > 0){
			query += " and khpc.phongbansx_fk = '" + this.boPhanThucHien + "'";
		}
		
		if(this.loai.length() > 0){
			query += " and khpc.loai = '" + this.loai + "'";
		}
		
		if(this.sanPham.length() > 0){
			query += " and khpc.sanpham_fk = '" + this.sanPham + "'";
		}
		
		if(this.trangthai.length() > 0){
			query += " and khpc.trangthai = '" + this.trangthai + "'";
		}
		
		this.KehoachphacheRs = this.db.get(query);
		
		createRS();
	}
	
	public void createRS(){
		String query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_PHONGBANSX";
		this.KhuvucSXRs = this.db.get(query);
		
		query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_SANPHAM";
		this.sanphamRs = this.db.get(query);
	}
	
	public void delete(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_KEHOACHPHACHE set trangthai = '2' where pk_seq = " + id;
			
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {}
	}
	
	public void DBClose(){
		try {
			if(this.KhuvucSXRs != null)
				this.KhuvucSXRs.close();
			if(this.sanphamRs != null)
				this.sanphamRs.close();
			if(this.KehoachphacheRs != null)
				this.KehoachphacheRs.close();
			this.db.shutDown();
		} catch (SQLException e) {}
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNgayKeHoach() {
		return ngayKeHoach;
	}

	public void setNgayKeHoach(String ngayKeHoach) {
		this.ngayKeHoach = ngayKeHoach;
	}

	public String getBoPhanThucHien() {
		return boPhanThucHien;
	}

	public void setBoPhanThucHien(String boPhanThucHien) {
		this.boPhanThucHien = boPhanThucHien;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getSanPham() {
		return sanPham;
	}

	public void setSanPham(String sanPham) {
		this.sanPham = sanPham;
	}

	public ResultSet getKehoachphacheRs() {
		return KehoachphacheRs;
	}

	public void setKehoachphacheRs(ResultSet kehoachphacheRs) {
		KehoachphacheRs = kehoachphacheRs;
	}

	public ResultSet getKhuvucSXRs() {
		return KhuvucSXRs;
	}

	public void setKhuvucSXRs(ResultSet khuvucSXRs) {
		KhuvucSXRs = khuvucSXRs;
	}

	public ResultSet getSanphamRs() {
		return sanphamRs;
	}

	public void setSanphamRs(ResultSet sanphamRs) {
		this.sanphamRs = sanphamRs;
	}
}
