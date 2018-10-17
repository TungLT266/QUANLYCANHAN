package geso.traphaco.erp.beans.tailieuphachethuoc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.tailieuphachethuoc.IErpTaiLieuPhaCheThuoc;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpTaiLieuPhaCheThuoc implements IErpTaiLieuPhaCheThuoc {
	private String userId;
	private String congtyId;
	private String id;
	private String ma;
	private String noidung;
	private String thuocthu;
	private String congthuc;
	private String trangthai;
	private String msg;
	
	List<ErpTaiLieuPhaCheThuoc_ThongTin> thongtinList;
	ResultSet sanphamRs;
	dbutils db;
	
	public ErpTaiLieuPhaCheThuoc() {
		this.id = "";
		this.ma = "";
		this.noidung = "";
		this.thuocthu = "";
		this.congthuc = "";
		this.trangthai = "1";
		this.msg = "";
		
		thongtinList = new ArrayList<ErpTaiLieuPhaCheThuoc_ThongTin>();
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select ma,noidung,sanpham_fk,congthuc,trangthai from ERP_TAILIEUPHACHETHUOC where pk_seq = " + this.id;
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try {
				rs.next();
				this.ma = rs.getString("ma");
				this.noidung = rs.getString("noidung");
				this.thuocthu = rs.getString("sanpham_fk");
				this.congthuc = rs.getString("congthuc");
				this.trangthai = rs.getString("trangthai");
				
				rs.close();
			} catch (SQLException e) {}
		}
		
		query = "select sanpham_fk,soluong from ERP_TAILIEUPHACHETHUOC_THONGTIN where tailieuphachethuoc_fk = " + this.id;
		ErpTaiLieuPhaCheThuoc_ThongTin thongtin;
		rs = this.db.get(query);
		
		if(rs != null){
			try {
				while(rs.next()){
					thongtin = new ErpTaiLieuPhaCheThuoc_ThongTin();
					
					thongtin.setSanphamid(rs.getString("sanpham_fk"));
					thongtin.setSoluong(rs.getString("soluong"));
					
					this.thongtinList.add(thongtin);
				}
				
				rs.close();
			} catch (SQLException e) {}
		}
		
		createRs();
	}
	
	public void createRs(){
		String query = "select pk_seq, ma + ' - ' + ten as ten from ERP_SANPHAM";
		
		this.sanphamRs = this.db.getScrol(query);
	}
	
	public boolean CheckUnique() {
		try{
			String query = "select count(*) as count from ERP_TAILIEUPHACHETHUOC where MA='" + this.ma + "'";
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
			
			String query = "insert into ERP_TAILIEUPHACHETHUOC(ma,noidung,sanpham_fk,congthuc,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
					+ " values('"+this.ma+"',N'"+this.noidung+"','"+this.thuocthu+"',N'"+this.congthuc+"','"+this.trangthai+"','"+this.userId+"',"
					+ "'"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_TAILIEUPHACHETHUOC: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = db.get("select SCOPE_IDENTITY() as ID ");
			rs.next();
			this.id = rs.getString("ID");
			rs.close();
			
			ErpTaiLieuPhaCheThuoc_ThongTin thongtin;
			for(int i = 0; i < this.thongtinList.size(); i++){
				thongtin = this.thongtinList.get(i);
				if(thongtin.getSanphamid().trim().length() > 0){
					query = "insert into ERP_TAILIEUPHACHETHUOC_THONGTIN(tailieuphachethuoc_fk,sanpham_fk,soluong)"
							+ " values('"+this.id+"','"+thongtin.getSanphamid()+"','"+thongtin.getSoluong()+"')";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_TAILIEUPHACHETHUOC_THONGTIN: " + query;
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
			
			String query = "update ERP_TAILIEUPHACHETHUOC set ma='"+this.ma+"',noidung=N'"+this.noidung+"',sanpham_fk='"+this.thuocthu+"',congthuc=N'"+this.congthuc+"',"
					+ "trangthai='"+this.trangthai+"',nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"' where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_TAILIEUPHACHETHUOC: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_TAILIEUPHACHETHUOC_THONGTIN where tailieuphachethuoc_fk = " + this.id;
			if(!db.update(query)){
				this.msg = "Không thể xóa cập nhật ERP_TAILIEUPHACHETHUOC_THONGTIN " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ErpTaiLieuPhaCheThuoc_ThongTin thongtin;
			for(int i = 0; i < this.thongtinList.size(); i++){
				thongtin = this.thongtinList.get(i);
				if(thongtin.getSanphamid().trim().length() > 0){
					query = "insert into ERP_TAILIEUPHACHETHUOC_THONGTIN(tailieuphachethuoc_fk,sanpham_fk,soluong)"
							+ " values('"+this.id+"','"+thongtin.getSanphamid()+"','"+thongtin.getSoluong()+"')";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_TAILIEUPHACHETHUOC_THONGTIN: " + query;
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

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public String getThuocthu() {
		return thuocthu;
	}

	public void setThuocthu(String thuocthu) {
		this.thuocthu = thuocthu;
	}

	public String getCongthuc() {
		return congthuc;
	}

	public void setCongthuc(String congthuc) {
		this.congthuc = congthuc;
	}

	public ResultSet getSanphamRs() {
		return sanphamRs;
	}

	public void setSanphamRs(ResultSet sanphamRs) {
		this.sanphamRs = sanphamRs;
	}

	public List<ErpTaiLieuPhaCheThuoc_ThongTin> getThongtinList() {
		return thongtinList;
	}

	public void setThongtinList(List<ErpTaiLieuPhaCheThuoc_ThongTin> thongtinList) {
		this.thongtinList = thongtinList;
	}
}
