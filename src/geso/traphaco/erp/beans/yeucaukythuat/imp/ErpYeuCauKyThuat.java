package geso.traphaco.erp.beans.yeucaukythuat.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.yeucaukythuat.IErpYeuCauKyThuat;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpYeuCauKyThuat implements IErpYeuCauKyThuat {
	private String userId;
	private String congtyId;
	private String id;
	private String ma;
	private String ten;
	private String diengiai;
	private String thongsoTu;
	private String thongsoDen;
	private String dvt;
	private String gioihan;
	private String trangthai;
	private String msg;
	
	private List<ErpYeuCauKyThuat_HoaChat> hoachatList;
	
	ResultSet dvtRs;
	ResultSet sanphamRs;
	dbutils db;
	
	public ErpYeuCauKyThuat() {
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.diengiai = "";
		this.thongsoTu = "";
		this.thongsoDen = "";
		this.dvt = "";
		this.gioihan = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.hoachatList = new ArrayList<ErpYeuCauKyThuat_HoaChat>();
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select ma,ten,diengiai,trangthai,thongsoyeucautu,thongsoyeucauden,dvdl_fk,gioihanchophep from ERP_YEUCAUKYTHUAT where pk_seq = " + this.id;
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try {
				rs.next();
				this.ma = rs.getString("ma");
				this.ten = rs.getString("ten");
				this.diengiai = rs.getString("diengiai");
				this.trangthai = rs.getString("trangthai");
				this.thongsoTu = rs.getString("thongsoyeucautu");
				this.thongsoDen = rs.getString("thongsoyeucauden");
				this.dvt = rs.getString("dvdl_fk");
				this.gioihan = rs.getString("gioihanchophep");
				
				rs.close();
			} catch (SQLException e) {}
		}
		
		query = "select sanpham_fk,soluong,maso,cachpha from ERP_YEUCAUKYTHUAT_CHITIET where yeucaukythuat_fk="+this.id+" order by stt";
		rs = this.db.get(query);
		if(rs != null){
			try {
				ErpYeuCauKyThuat_HoaChat hoachat;
				while(rs.next()){
					hoachat = new ErpYeuCauKyThuat_HoaChat();
					
					hoachat.setHoachat(rs.getString("sanpham_fk"));
					hoachat.setSoluong(rs.getString("soluong"));
					hoachat.setMaso(rs.getString("maso"));
					hoachat.setCachpha(rs.getString("cachpha"));
					
					this.hoachatList.add(hoachat);
				}
				rs.close();
			} catch (SQLException e) {}
		}
		
		createRs();
	}
	
	public void createRs(){
		String query = "select pk_seq, donvi from DONVIDOLUONG";
		this.dvtRs = this.db.get(query);
		
		query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_SANPHAM";
		this.sanphamRs = this.db.getScrol(query);
	}
	
	public boolean CheckUnique() {
		try{
			String query = "select count(*) as count from ERP_YEUCAUKYTHUAT where MA='" + this.ma + "'";
			if (this.id.length() > 0)
				query += " and PK_SEQ !='" + this.id + "'";
			
			ResultSet rs = this.db.get(query);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			if (count > 0)
				return false;
			
			return true;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean create() {
		try {
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into ERP_YEUCAUKYTHUAT(ma,ten,diengiai,thongsoyeucautu,thongsoyeucauden,dvdl_fk,gioihanchophep,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
					+ " values('"+this.ma+"',N'"+this.ten+"',N'"+this.diengiai+"','"+this.thongsoTu+"','"+this.thongsoDen+"',case when '"+this.dvt.length()+"' > 0 then '"+this.dvt+"' else null end,"
					+ "'"+this.gioihan+"','"+this.trangthai+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_YEUCAUKYTHUAT: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = db.get("select SCOPE_IDENTITY() as ID ");
			rs.next();
			this.id = rs.getString("ID");
			rs.close();
			
			ErpYeuCauKyThuat_HoaChat hoachat;
			for(int i = 0; i < this.hoachatList.size(); i++){
				hoachat = this.hoachatList.get(i);
				
				if(hoachat.getHoachat().length() > 0){
					query = "insert into ERP_YEUCAUKYTHUAT_CHITIET(yeucaukythuat_fk,stt,sanpham_fk,soluong,maso,cachpha)"
							+ " values('"+this.id+"','"+(i+1)+"','"+hoachat.getHoachat()+"','"+hoachat.getSoluong()+"','"+hoachat.getMaso()+"',N'"+hoachat.getCachpha()+"')";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_YEUCAUKYTHUAT_CHITIET: " + query;
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
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_YEUCAUKYTHUAT set ma='"+this.ma+"',ten=N'"+this.ten+"',diengiai=N'"+this.diengiai+"',thongsoyeucautu='"+this.thongsoTu+"',"
					+ "thongsoyeucauden='"+this.thongsoDen+"',dvdl_fk=case when '"+this.dvt.length()+"' > 0 then '"+this.dvt+"' else null end,gioihanchophep='"+this.gioihan+"',"
					+ "trangthai='"+this.trangthai+"',nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"' where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_YEUCAUKYTHUAT: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YEUCAUKYTHUAT_CHITIET where yeucaukythuat_fk = " + this.id;
			if(!db.update(query)){
				this.msg = "Không thể xóa cập nhật ERP_YEUCAUKYTHUAT_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ErpYeuCauKyThuat_HoaChat hoachat;
			for(int i = 0; i < this.hoachatList.size(); i++){
				hoachat = this.hoachatList.get(i);
				
				if(hoachat.getHoachat().length() > 0){
					query = "insert into ERP_YEUCAUKYTHUAT_CHITIET(yeucaukythuat_fk,stt,sanpham_fk,soluong,maso,cachpha)"
							+ " values('"+this.id+"','"+(i+1)+"','"+hoachat.getHoachat()+"','"+hoachat.getSoluong()+"','"+hoachat.getMaso()+"',N'"+hoachat.getCachpha()+"')";
					
					if(!db.update(query)) {
						this.msg = "Không thể cập nhật ERP_YEUCAUKYTHUAT_CHITIET: " + query;
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
			if(this.sanphamRs != null)
				this.sanphamRs.close();
			if(this.dvtRs != null)
				this.dvtRs.close();
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

	public ResultSet getDvtRs() {
		return dvtRs;
	}
	public void setDvtRs(ResultSet dvtRs) {
		this.dvtRs = dvtRs;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getThongsoTu() {
		return thongsoTu;
	}

	public void setThongsoTu(String thongsoTu) {
		this.thongsoTu = thongsoTu;
	}

	public String getThongsoDen() {
		return thongsoDen;
	}

	public void setThongsoDen(String thongsoDen) {
		this.thongsoDen = thongsoDen;
	}

	public String getDvt() {
		return dvt;
	}

	public void setDvt(String dvt) {
		this.dvt = dvt;
	}

	public String getGioihan() {
		return gioihan;
	}

	public void setGioihan(String gioihan) {
		this.gioihan = gioihan;
	}

	public ResultSet getSanphamRs() {
		return sanphamRs;
	}

	public void setSanphamRs(ResultSet sanphamRs) {
		this.sanphamRs = sanphamRs;
	}

	public List<ErpYeuCauKyThuat_HoaChat> getHoachatList() {
		return hoachatList;
	}

	public void setHoachatList(List<ErpYeuCauKyThuat_HoaChat> hoachatList) {
		this.hoachatList = hoachatList;
	}
}
