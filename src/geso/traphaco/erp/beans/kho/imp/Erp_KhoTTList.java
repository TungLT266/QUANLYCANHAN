package geso.traphaco.erp.beans.kho.imp;

import java.sql.ResultSet;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.kho.IErp_KhoTTList;

public class Erp_KhoTTList implements IErp_KhoTTList {
	private String MSG,PK_SEQ,CONGTY_FK,TEN,DIACHI,QUANLYBIN,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,MA,TRANGTHAI;
	private ResultSet rsKhoList,rsCongty;
	private dbutils db;
	public Erp_KhoTTList()
	{
		this.PK_SEQ="";
		this.MSG="";
		this.MA="";
		this.TEN="";
		this.CONGTY_FK="";
		this.DIACHI="";
		this.NGAYSUA="";
		this.NGAYTAO="";
		this.NGUOISUA="";
		this.NGUOITAO="";
		this.QUANLYBIN="";
		this.TRANGTHAI="";
		this.db = new dbutils();
	}
	public void setMsg(String msg)
	{
		this.MSG=msg;
	}
	public String getMsg()
	{
		return this.MSG;
	}
	public void setPK_SEQ(String pk_seq) {
		this.PK_SEQ=pk_seq;
	}

	public String getPK_SEQ() {
		return this.PK_SEQ;
	}

	public void setCongty_fk(String congty_fk) {
		this.CONGTY_FK=congty_fk;
	}

	public String getCongty_fk() {
		return this.CONGTY_FK;
	}

	public void setTen(String ten) {
		this.TEN=ten;
	}

	public String getTen() {
		return this.TEN;
	}

	public void setDiachi(String diachi) {
		this.DIACHI=diachi;
	}

	public String getDiachi() {
		return this.DIACHI;
	}

	public void setQuanlybin(String quanlybin) {
		this.QUANLYBIN=quanlybin;
	}

	public String getQuanlybin() {
		return this.QUANLYBIN;
	}

	public void setNguoitao(String nguoitao) {
		this.NGUOITAO=nguoitao;
	}

	public String getNguoitao() {
		return this.NGUOITAO;
	}

	public void setNguoisua(String nguoisua) {
		this.NGUOISUA=nguoisua;
	}

	public String getNguoisua() {
		return this.NGUOISUA;
	}

	public void setNgaytao(String ngaytao) {
		this.NGAYTAO=ngaytao;
	}

	public String getNgaytao() {
		return this.NGAYTAO;
	}

	public void setNgaysua(String ngaysua) {
		this.NGAYSUA=ngaysua;
	}

	public String getNgaysua() {
		return this.NGAYSUA;
	}

	public void setMa(String ma) {
		this.MA=ma;
	}

	public String getMa() {
		return this.MA;
	}

	public void setTrangthai(String trangthai) {
		this.TRANGTHAI=trangthai;
	}

	public String getTrangthai() {
		return this.TRANGTHAI;
	}
	
	public void setRSKhoList(ResultSet rs) {
		this.rsKhoList=rs;
	}

	public ResultSet getRSKhoList() {
		return this.rsKhoList;
	}
	public void setRSCongty(ResultSet rs) 
	{
		this.rsCongty=rs;
	}
	public ResultSet getRSCongty() 
	{
		return this.rsCongty;
	}

	public boolean init() {
		String query="SELECT k.PK_SEQ ,k.TEN AS TEN,k.DIACHI AS DIACHI ,k.QUANLYBIN AS QUANLYBIN,c.TEN AS CONGTY,nv.TEN AS NGUOITAO," +
					 "nv.TEN AS NGUOISUA,k.NGAYTAO AS NGAYTAO,k.NGAYSUA AS NGAYSUA,k.MA AS MA,k.TRANGTHAI AS TRANGTHAI " +
					 "FROM ERP_KHOTT k INNER JOIN ERP_CONGTY c ON k.CONGTY_FK=c.PK_SEQ INNER JOIN NHANVIEN nv ON k.NGUOITAO=nv.PK_SEQ " +
					 "AND c.PK_SEQ = " + this.CONGTY_FK + " " +
					 "ORDER BY k.MA ";
		CreateRS();
		try{
			this.rsKhoList=this.db.get(query);
			return true;
		}
		catch (Exception e) {
			System.out.println("Sai "+ query);
			return false;
		}
	}
	public void CreateRS()
	{
		String query = "SELECT * from ERP_CONGTY";
		this.rsCongty = this.db.get(query);
	}
	public boolean Search()
	{
		Utility util = new Utility();
		
		String query =  "SELECT k.PK_SEQ ,k.TEN AS TEN,k.DIACHI AS DIACHI ,k.QUANLYBIN AS QUANLYBIN,c.TEN AS CONGTY,nv.TEN AS NGUOITAO," +
						"nv.TEN AS NGUOISUA,k.NGAYTAO AS NGAYTAO,k.NGAYSUA AS NGAYSUA, k.MA AS MA, " +
						"k.TRANGTHAI AS TRANGTHAI FROM ERP_KHOTT k " +
						"INNER JOIN ERP_CONGTY c ON k.CONGTY_FK = c.PK_SEQ AND c.PK_SEQ = " + this.CONGTY_FK + " " +
						"INNER JOIN NHANVIEN nv ON k.NGUOITAO=nv.PK_SEQ WHERE 1=1 ";
		
		if(this.MA.length()>0) query += " AND k.MA LIKE N'%" + this.MA + "%'";
		if(this.TEN.length()>0) query += " AND dbo.ftBoDau(k.TEN) LIKE N'%" + util.replaceAEIOU(this.TEN) + "%'";
		if(this.CONGTY_FK.length()>0) query += " AND k.CONGTY_FK LIKE '%" + this.CONGTY_FK + "%'";
		if(this.TRANGTHAI.length()>0) query += " AND k.trangthai LIKE '%" + this.TRANGTHAI + "%'";
		query = query + " ORDER BY k.MA " ; 
		System.out.println(query);
		
		this.rsKhoList=this.db.get(query);
		return true;
	}
	public String Delete() {
		try {
			String query = "";
			
			query = "SELECT COUNT(*) AS NUM FROM ERP_KHOTT_SANPHAM WHERE KHOTT_FK = " + this.PK_SEQ + " AND SOLUONG > 0";
			ResultSet rs = db.get(query);
			if (rs != null)
			{
				try {
					rs.next();
					if(rs.getInt("NUM") > 0) {
						this.MSG = "Không thể xóa kho trung tâm (kho đã có tồn kho)!";
						return this.MSG;
					}
				} catch(Exception e) {
					this.MSG = "Xảy ra lỗi khi kiểm tra dữ liệu phát sinh của kho trung tâm (" + e.getMessage() + ")";
					return this.MSG;
				}finally{
					try {
						rs.close();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}			
			query = "SELECT COUNT(*) AS NUM FROM ERP_MUAHANG_SP WHERE KHOTT_FK = " + this.PK_SEQ + "";
			rs = db.get(query);
			try {
				rs.next();
				if(rs.getInt("NUM") > 0) {
					this.MSG = "Không thể xóa kho trung tâm (đã phát sinh dữ liệu mua hàng)!";
					return this.MSG;
				}
			} catch(Exception e) {
				this.MSG = "Xảy ra lỗi khi kiểm tra dữ liệu phát sinh của kho trung tâm (" + e.getMessage() + ")";
				return this.MSG;
			}
			
			query = "SELECT COUNT(*) AS NUM FROM ERP_DONDATHANG WHERE KHOTT_FK = " + this.PK_SEQ + "";
			rs = db.get(query);
			try {
				rs.next();
				if(rs.getInt("NUM") > 0) {
					this.MSG = "Không thể xóa kho trung tâm (đã phát sinh dữ liệu bán hàng)!";
					return this.MSG;
				}
			} catch(Exception e) {
				this.MSG = "Xảy ra lỗi khi kiểm tra dữ liệu phát sinh của kho trung tâm (" + e.getMessage() + ")";
				return this.MSG;
			}
			
			
			db.getConnection().setAutoCommit(false);
			
			query = " DELETE ERP_KHOTT_SANPHAM WHERE KHOTT_FK = " + this.PK_SEQ;
			if(!this.db.update(query))
			{
				db.getConnection().rollback();
				this.MSG = "Xảy ra lỗi khi xóa kho trung tâm (" + query + ")";
				return this.MSG;
			}
			
			query = "DELETE ERP_KHOTT_LOAISANPHAM WHERE KHOTT_FK = " + this.PK_SEQ;
			if(!this.db.update(query))
			{
				db.getConnection().rollback();
				this.MSG = "Xảy ra lỗi khi xóa kho trung tâm (" + query + ")";
				return this.MSG;
			}
			
			query = "DELETE ERP_KHOTT WHERE PK_SEQ = " + this.PK_SEQ;
			if(!this.db.update(query))
			{
				db.getConnection().rollback();
				this.MSG = "Xảy ra lỗi khi xóa kho trung tâm (" + query + ")";
				return this.MSG;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch(Exception e) {
			return "Xảy ra lỗi khi xóa kho trung tâm (Đã phát sinh dữ liệu liên quan đến kho - " + e.getMessage() + ")";
		}
		return "";
	}
	
	public void DBClose(){
		try{
			if (this.rsCongty != null) this.rsCongty.close();
			if (this.rsKhoList != null) this.rsKhoList.close();
			if (this.db != null) this.db.shutDown();
		}catch (java.sql.SQLException e){}
	}
	
}
