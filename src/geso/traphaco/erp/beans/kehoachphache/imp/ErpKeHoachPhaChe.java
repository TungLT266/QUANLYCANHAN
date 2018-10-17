package geso.traphaco.erp.beans.kehoachphache.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaChe;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpKeHoachPhaChe implements IErpKeHoachPhaChe {
	private String userId;
	private String congtyId;
	private String id;
	private String ngaykehoach;
	private String bophanthuchien;
	private String loai;
	private String sanpham;
	private String diengiai;
	private String trangthai;
	private String msg;
	
	private List<ErpKeHoachPhaChe_ChiTiet> KhpcChitietList;
	
	private ResultSet KhuvucSXRs;
	private ResultSet SanphamRs;
	dbutils db;
	
	public ErpKeHoachPhaChe() {
		this.id = "";
		this.ngaykehoach = "";
		this.bophanthuchien = "";
		this.loai = "";
		this.sanpham = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.KhpcChitietList = new ArrayList<ErpKeHoachPhaChe_ChiTiet>();
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select ngaykehoach,phongbansx_fk,loai,sanpham_fk,diengiai,trangthai from ERP_KEHOACHPHACHE where pk_seq = " + this.id;
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try {
				rs.next();
				this.ngaykehoach = rs.getString("ngaykehoach");
				this.bophanthuchien = rs.getString("phongbansx_fk");
				this.loai = rs.getString("loai");
				this.sanpham = rs.getString("sanpham_fk");
				this.diengiai = rs.getString("diengiai");
				this.trangthai = rs.getString("trangthai");
				
				rs.close();
			} catch (SQLException e) {}
		}
		
		query = "select ngayphache, ghichu from ERP_KEHOACHPHACHE_CHITIET where kehoachphache_fk = "+this.id+" order by stt";
		rs = this.db.get(query);
		if(rs != null){
			try {
				ErpKeHoachPhaChe_ChiTiet KhpcChitiet;
				while(rs.next()){
					KhpcChitiet = new ErpKeHoachPhaChe_ChiTiet();
					
					KhpcChitiet.setNgayphache(rs.getString("ngayphache"));
					KhpcChitiet.setGhichu(rs.getString("ghichu"));
					
					this.KhpcChitietList.add(KhpcChitiet);
				}
				rs.close();
			} catch (SQLException e) {}
		}
		
		createRs();
	}
	
	public void createRs(){
		String query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_PHONGBANSX";
		this.KhuvucSXRs = this.db.get(query);
		
		query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_SANPHAM";
		this.SanphamRs = this.db.get(query);
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into ERP_KEHOACHPHACHE(ngaykehoach,phongbansx_fk,loai,sanpham_fk,DIENGIAI,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
					+ " values('"+this.ngaykehoach+"','"+this.bophanthuchien+"','"+this.loai+"','"+this.sanpham+"',N'"+this.diengiai+"','"+this.trangthai+"',"
					+ "'"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_KEHOACHPHACHE: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = db.get("select SCOPE_IDENTITY() as ID ");
			rs.next();
			this.id = rs.getString("ID");
			rs.close();
			
			ErpKeHoachPhaChe_ChiTiet KhpcChitiet;
			for(int i = 0; i < this.KhpcChitietList.size(); i++){
				KhpcChitiet = this.KhpcChitietList.get(i);
				
				if(KhpcChitiet.getNgayphache().length() > 0){
					query = "insert into ERP_KEHOACHPHACHE_CHITIET(kehoachphache_fk, stt, ngayphache, ghichu)"
							+ " values('"+this.id+"','"+(i+1)+"','"+KhpcChitiet.getNgayphache()+"',N'"+KhpcChitiet.getGhichu()+"')";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_KEHOACHPHACHE_CHITIET: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean update() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_KEHOACHPHACHE set ngaykehoach='"+this.ngaykehoach+"',phongbansx_fk='"+this.bophanthuchien+"',loai='"+this.loai+"',sanpham_fk='"+this.sanpham+"',"
					+ "DIENGIAI=N'"+this.diengiai+"',trangthai='"+this.trangthai+"',nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"' where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_KEHOACHPHACHE: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_KEHOACHPHACHE_CHITIET where kehoachphache_fk = " + this.id;
			if(!db.update(query)){
				this.msg = "Không thể xóa cập nhật ERP_KEHOACHPHACHE_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ErpKeHoachPhaChe_ChiTiet KhpcChitiet;
			for(int i = 0; i < this.KhpcChitietList.size(); i++){
				KhpcChitiet = this.KhpcChitietList.get(i);
				
				if(KhpcChitiet.getNgayphache().length() > 0){
					query = "insert into ERP_KEHOACHPHACHE_CHITIET(kehoachphache_fk, stt, ngayphache, ghichu)"
							+ " values('"+this.id+"','"+(i+1)+"','"+KhpcChitiet.getNgayphache()+"',N'"+KhpcChitiet.getGhichu()+"')";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_KEHOACHPHACHE_CHITIET: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try {
			if(this.KhuvucSXRs != null)
				this.KhuvucSXRs.close();
			if(this.SanphamRs != null)
				this.SanphamRs.close();
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getNgaykehoach() {
		return ngaykehoach;
	}

	public void setNgaykehoach(String ngaykehoach) {
		this.ngaykehoach = ngaykehoach;
	}

	public String getBophanthuchien() {
		return bophanthuchien;
	}

	public void setBophanthuchien(String bophanthuchien) {
		this.bophanthuchien = bophanthuchien;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getSanpham() {
		return sanpham;
	}

	public void setSanpham(String sanpham) {
		this.sanpham = sanpham;
	}

	public List<ErpKeHoachPhaChe_ChiTiet> getKhpcChitietList() {
		return KhpcChitietList;
	}

	public void setKhpcChitietList(List<ErpKeHoachPhaChe_ChiTiet> khpcChitietList) {
		KhpcChitietList = khpcChitietList;
	}

	public ResultSet getKhuvucSXRs() {
		return KhuvucSXRs;
	}
	public void setKhuvucSXRs(ResultSet khuvucSXRs) {
		KhuvucSXRs = khuvucSXRs;
	}

	public ResultSet getSanphamRs() {
		return SanphamRs;
	}

	public void setSanphamRs(ResultSet sanphamRs) {
		SanphamRs = sanphamRs;
	}
}
