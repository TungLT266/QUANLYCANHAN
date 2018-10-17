

package geso.traphaco.center.beans.nhiemvu.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.beans.nhiemvu.INhiemVu;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

public class NhiemVu implements INhiemVu {

	String userId;
	String id;
	String ngayTao;
	String nguoiTao;
	String nguoiSua;
	String ngaySua;
	String tieuChi;
	String dienGiai;
	String doiTuong;
	String loaiTieuChi;
	boolean isTuDong;
	String trangThai;
	String msg;
	String tenloaitieuchi;
	
	dbutils db;
	 
	public NhiemVu() {
		this.id = "";
		this.tieuChi = "";
		this.dienGiai = "";
		this.loaiTieuChi = "";
		this.doiTuong = "";
		this.isTuDong = true;
		this.trangThai = "";
		this.ngayTao = "";
		this.nguoiTao = "";
		this.ngaySua = "";
		this.nguoiSua = "";
		this.msg = "";
		this.tenloaitieuchi = "";
		 
		db = new dbutils();
	}
	
	public NhiemVu(String id) {
		this.id = id;
		this.tieuChi = "";
		this.dienGiai = "";
		this.loaiTieuChi = "";
		this.doiTuong = "";
		this.isTuDong = true;
		this.trangThai = "";
		this.ngayTao = "";
		this.nguoiTao = "";
		this.ngaySua = "";
		this.nguoiSua = "";
		this.msg = "";
		this.tenloaitieuchi = "";
		
		db = new dbutils();
	}
	 
	public NhiemVu(String[] param) {
		this.id = param[0];
		this.tieuChi = param[1];
		this.dienGiai = param[2];
		this.loaiTieuChi = param[3];
		this.doiTuong = param[4];
		this.isTuDong = param[5].equals("1") ? true : false;
		this.trangThai = param[6];
		this.ngayTao = param[7];
		this.nguoiTao = param[8];
		this.ngaySua = param[9];
		this.nguoiSua = param[10];
		this.msg = "";
		this.tenloaitieuchi = "";
		db = new dbutils();
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setTieuChi(String value) {
		this.tieuChi = value;
	}

	@Override
	public String getTieuChi() {
		return this.tieuChi;
	}

	@Override
	public void setDienGiai(String value) {
		this.dienGiai = value;
	}

	@Override
	public String getDienGiai() {
		return this.dienGiai;
	}

	@Override
	public void setIsTuDong(boolean value) {
		this.isTuDong = value;		
	}

	@Override
	public boolean getIsTuDong() {
		return this.isTuDong;
	}

	@Override
	public void setLoaiTieuChi(String value) {
		this.loaiTieuChi = value;
	}

	@Override
	public String getLoaiTieuChi() {
		return this.loaiTieuChi;
	}

	@Override
	public void setTenLoaiTieuChi(String value) {
		this.tenloaitieuchi = value;
	}

	@Override
	public String getTenLoaiTieuChi() {
		return this.tenloaitieuchi;
	}

	@Override
	public void setDoiTuong(String value) {
		this.doiTuong = value;
	}

	@Override
	public String getDoiTuong() {
		return this.doiTuong;
	}

	@Override
	public void setNguoiTao(String nguoitao) {
		this.nguoiTao = nguoitao;
		
	}

	@Override
	public String getNguoiTao() {
		return this.nguoiTao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		this.nguoiSua = nguoisua;
	}

	@Override
	public String getNguoiSua() {
		return this.nguoiSua;
	}

	@Override
	public void setNgayTao(String ngaytao) {
		this.ngayTao = ngaytao;
	}

	@Override
	public String getNgayTao() {
		return this.ngayTao;
	}

	@Override
	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}

	@Override
	public String getNgaySua() {
		return ngaySua;
	}

	@Override
	public void setTrangThai(String trangthai) {
		this.trangThai = trangthai;
	}

	@Override
	public String getTrangThai() {
		return this.trangThai;
	}

	@Override
	public void setUserId(String userid) {
		this.userId = userid;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public String getMessage() 
	{
		return this.msg;
	}

	@Override
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public void init(){
		String query = "SELECT * FROM NHIEMVU WHERE pk_seq='" + this.id + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.tieuChi = rs.getString("TIEUCHI"); if(this.tieuChi != null) tieuChi = tieuChi.trim(); else tieuChi = "";
			this.dienGiai = rs.getString("DIENGIAI"); if(dienGiai != null) dienGiai = dienGiai.trim(); else dienGiai = "";
			String itd= rs.getString("ISTUDONG"); isTuDong = itd != null ? itd.trim().equals("1") : false;
			this.loaiTieuChi = rs.getString("LOAITIEUCHI"); if(loaiTieuChi != null) loaiTieuChi = loaiTieuChi.trim(); else loaiTieuChi = "";
			this.doiTuong = rs.getString("DOITUONG"); if(doiTuong != null) doiTuong = doiTuong.trim(); else doiTuong = "";
			this.ngayTao = rs.getString("NGAYTAO"); if(ngayTao != null) ngayTao = ngayTao.trim(); else ngayTao = "";
			this.ngaySua = rs.getString("NGAYSUA"); if(ngaySua != null) ngaySua = ngaySua.trim(); else ngaySua = "";
			this.nguoiTao = rs.getString("NGUOITAO"); if(nguoiTao != null) nguoiTao = nguoiTao.trim(); else nguoiTao = "";
			this.nguoiSua = rs.getString("NGUOISUA"); if(nguoiSua != null) nguoiSua = nguoiSua.trim(); else nguoiSua = "";
			this.trangThai = rs.getString("TRANGTHAI"); if(trangThai != null) trangThai = trangThai.trim(); else trangThai = "";
			rs.close();
			
			System.out.println("[NhiemVu.init] " + this.toString());
			
		} catch(Exception e) {
			System.out.println("[NhiemVu.init] Exception Message: " + e.getMessage());
		}
	}

	@Override
	public boolean create() {
		try{
			this.db.getConnection().setAutoCommit(false);
			
			this.ngayTao = getDateTime();
			this.nguoiTao = this.userId;
			
			String _isTuDong = this.isTuDong ? "1" : "0";
			
			String command;
			if(this.isTuDong) {
				command = "insert into nhiemvu(tieuchi, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, loaitieuchi, doituong, istudong) "+
							 "values(N'" + this.tieuChi + "',N'" + this.dienGiai + "','" + this.trangThai + "','" + this.ngayTao + "','" + this.ngaySua + "','" + 
							 			   this.userId + "','" + this.userId + "','" + this.loaiTieuChi + "','" + this.doiTuong + "','" + _isTuDong + "')";
			} else {
				command = "insert into nhiemvu(tieuchi, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, loaitieuchi, doituong, istudong) "+
				 "values(N'" + this.tieuChi + "',N'" + this.dienGiai + "','" + this.trangThai + "','" + this.ngayTao + "','" + this.ngaySua + "','" + 
				 			   this.userId + "','" + this.userId + "', NULL ,'" + this.doiTuong + "','" + _isTuDong + "')";
			}
		
			if (!db.update(command)){
				this.msg = "Không thể tạo mới nhiệm vụ.";
				db.update("rollback");
				return false;
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		
		}
		catch(Exception e) {
			this.msg = "Xảy ra lỗi khi tạo mới nhiệm vụ (Lỗi: " + e.getMessage() + ")";
			this.db.update("rollback");
			return false;
		}
		
		return true;
		
	}

	@Override
	public boolean update() {
		
		this.ngaySua = getDateTime();
		this.nguoiSua = this.userId;
		
		String _isTuDong = this.isTuDong ? "1" : "0";
		
		String command;
		if(this.isTuDong) {
			command ="update nhiemvu set tieuchi = N'" + this.tieuChi + 
										"', diengiai = N'" + this.dienGiai + 
										"', istudong = '" + _isTuDong + 
										"', loaitieuchi = '" + this.loaiTieuChi + 
										"', doituong = '" + this.doiTuong + 
										"', ngaysua = '" + this.ngaySua + 
										"', nguoisua = '" + this.userId + 
										"', trangthai= '" + this.trangThai + 
					  "' where pk_seq = '" + this.id + "'";
		} else {
			command ="update nhiemvu set tieuchi = N'" + this.tieuChi + 
										"', diengiai = N'" + this.dienGiai + 
										"', istudong = '" + _isTuDong + 
										"', loaitieuchi = NULL " + 
										", doituong = '" + this.doiTuong + 
										"', ngaysua = '" + this.ngaySua + 
										"', nguoisua = '" + this.nguoiSua + 
										"', trangthai= '" + this.trangThai + 
										"' where pk_seq = '" + this.id + "'";
		}
		
		if (!db.update(command)){
			this.msg = "Không thể cập nhật nhiệm vụ.";
			db.update("rollback");
			return false;
		}

		this.db.update("commit");
		
		return true; 
	}

	@Override
	public void closeDB() {
		if(this.db != null) {
			this.db.shutDown();
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String toString() {
		return "NhiemVu {id: "+this.id+", tieuchi: "+this.tieuChi+", diengiai: "+this.dienGiai+", istudong: "+this.isTuDong+", loaitieuchi: "+this.loaiTieuChi+", doituong: "+this.doiTuong+", ngaysua: "+this.ngaySua+", nguoisua: "+this.nguoiSua+", trangthai: "+this.trangThai+"}";
	}
}
