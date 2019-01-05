package qlcn.pages.taikhoan.beans.imp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import db.Dbutils;
import qlcn.center.util.Phan_Trang;
import qlcn.pages.taikhoan.beans.ITaiKhoanLog;

public class TaiKhoanLog extends Phan_Trang implements ITaiKhoanLog {
	private String userId;
	private String ID;
	private String tungay;
	private String denngay;
	private String msg;
	
	private List<TaiKhoanLogList> logList;
	
	private Dbutils db;
//	private Utility util;
	
	public TaiKhoanLog() {
		this.ID = "";
		this.tungay = "";
		this.denngay = "";
		this.msg = "";
		
		this.logList = new ArrayList<TaiKhoanLogList>();
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			
			String query = "select tk.ngay_log, tk.TEN, tk.SOTIEN, dv.ten as donvi, tk.nganhang, tk.hanmuc, tk.notindung,"
					+ " case when tk.istknganhang=1 then 'x' else '' end as istknganhang,"
					+ " case when tk.istktindung=1 then 'x' else '' end as istktindung,"
					+ " case when tk.TRANGTHAI=1 then N'Hoạt động' when tk.trangthai=0 then N'Ngưng hoạt động' else N'Đã xóa' end as TRANGTHAI"
					+ " from TAIKHOAN_LOG tk inner join DONVI dv on dv.ID=tk.DONVI_FK"
					+ " where tk.ID = " + this.ID;
			System.out.println(query);
			
			ResultSet rs = createSplittingDataNew(this.db, 50, 10, "ngay_log desc", query);
			TaiKhoanLogList log;
			
			while(rs.next()){
				log = new TaiKhoanLogList();
				
				log.setNgaylog(rs.getString("ngay_log"));
				log.setTen(rs.getString("TEN"));
				log.setSotien(formatter.format(rs.getDouble("SOTIEN")));
				log.setDonvi(rs.getString("donvi"));
				log.setNganhang(rs.getString("nganhang"));
				log.setIsTknganhang(rs.getString("istknganhang"));
				log.setIsTktindung(rs.getString("istktindung"));
				log.setHanmuc(formatter.format(rs.getDouble("hanmuc")));
				log.setNoTindung(formatter.format(rs.getDouble("notindung")));
				log.setTrangthai(rs.getString("TRANGTHAI"));
				
				this.logList.add(log);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<TaiKhoanLogList> getLogList() {
		return logList;
	}

	public void setLogList(List<TaiKhoanLogList> logList) {
		this.logList = logList;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
}
