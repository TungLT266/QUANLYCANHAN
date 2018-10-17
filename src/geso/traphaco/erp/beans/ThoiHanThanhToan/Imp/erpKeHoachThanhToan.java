package geso.traphaco.erp.beans.ThoiHanThanhToan.Imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.ThoiHanThanhToan.IerpKeHoachThanhToan;
import geso.traphaco.erp.db.sql.dbutils;

public class erpKeHoachThanhToan implements IerpKeHoachThanhToan {
	String userId;
	String congTyId;
	String soPO;
	String ncc;
	String tuNgay;
	String denNgay;
	String msg;
	ResultSet keHoachRs;
	ResultSet nccRs;
	dbutils db;
	private String getDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy--mm--dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public erpKeHoachThanhToan(){
		this.userId = "";
		this.congTyId = "";
		this.soPO = "";
		this.ncc = "";
		this.tuNgay = "";
		this.denNgay = "";
		this.msg ="";
		this.db = new dbutils();
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.userId = userId;
	}

	@Override
	public String getCongTyId() {
		// TODO Auto-generated method stub
		return this.congTyId;
	}

	@Override
	public void setCongTyId(String congTyId) {
		// TODO Auto-generated method stub
		this.congTyId = congTyId;
	}

	@Override
	public String getSoPO() {
		// TODO Auto-generated method stub
		return this.soPO;
	}

	@Override
	public void setSoPO(String soPO) {
		// TODO Auto-generated method stub
		this.soPO = soPO;
	}

	@Override
	public String getNCC() {
		// TODO Auto-generated method stub
		return this.ncc;
	}

	@Override
	public void setNCC(String ncc) {
		// TODO Auto-generated method stub
		this.ncc = ncc;
	}

	@Override
	public String getTuNgay() {
		// TODO Auto-generated method stub
		return this.tuNgay;
	}

	@Override
	public void setTuNgay(String tuNgay) {
		// TODO Auto-generated method stub
		this.tuNgay = tuNgay;
	}

	@Override
	public String getDenNgay() {
		// TODO Auto-generated method stub
		return this.denNgay;
	}

	@Override
	public void setDenNgay(String denNgay) {
		// TODO Auto-generated method stub
		this.denNgay = denNgay;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg = msg;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String query ="select mh.sopo,ncc.ten , thtt.ngaythanhtoan,thtt.sotien from erp_thoihanthanhtoan thtt "
				+" left join erp_muahang mh on mh.pk_seq = thtt.muahang_fk "
				+" left join erp_nhacungcap ncc on ncc.pk_seq  = mh.nhacungcap_fk "
				+" where (mh.trangthai = 1 or mh.trangthai =2) ";
		if(this.tuNgay.trim().length()>0)
			query += " and thtt.ngaythanhtoan >= '"+this.tuNgay+"' ";
		if(this.denNgay.trim().length()>0)
			query += " and thtt.ngaythanhtoan <= '"+this.denNgay+"' ";
		/*if(this.getNCC().trim().length()>0)
			query += String.format(" and ncc.ten like N'%%%s%%'", this.ncc );*/
		if(this.getNCC().trim().length()>0)
			query += String.format(" and ncc.pk_seq =" + this.ncc );
		if(this.getSoPO().trim().length()>0)
			query += String.format(" and mh.sopo like N'%%%s%%'", this.soPO );
		query +=" order by thtt.ngaythanhtoan asc,mh.sopo asc ";
		System.out.println("query:" +query);
		this.keHoachRs = db.get(query);
	}

	@Override
	public void dbClose() {
		if(this.keHoachRs != null)
			try {
				this.keHoachRs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.db.shutDown();
	}
	@Override
	public ResultSet getKeHoachRs() {
		// TODO Auto-generated method stub
		return this.keHoachRs;
	}
	@Override
	public void setKeHoachRs(ResultSet keHoachRs) {
		// TODO Auto-generated method stub
		this.keHoachRs = keHoachRs;
	}
	@Override
	public ResultSet getNccRs() {
		// TODO Auto-generated method stub
		return this.nccRs;
	}
	@Override
	public void setNccRs(ResultSet nccRs) {
		// TODO Auto-generated method stub
		this.nccRs= nccRs;
	}
	@Override
	public void createNccRs(){
		String query = "select pk_seq,ten from erp_nhacungcap";
		this.nccRs=db.get(query);
	}
	
}
