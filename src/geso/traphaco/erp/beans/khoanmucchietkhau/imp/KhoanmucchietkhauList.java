package geso.traphaco.erp.beans.khoanmucchietkhau.imp;

import geso.traphaco.erp.beans.khoanmucchietkhau.IKhoanmucchietkhauList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.*;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class KhoanmucchietkhauList implements IKhoanmucchietkhauList {
	private String MSG, PK_SEQ, CONGTY_FK, TEN, DIACHI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, MA, TRANGTHAI;
	private ResultSet rsHMCK;
	private dbutils db;

	String chixem;

	public KhoanmucchietkhauList() {
		this.PK_SEQ = "";
		this.MSG = "";
		this.MA = "";
		this.TEN = "";
		this.CONGTY_FK = "";
		this.DIACHI = "";
		this.NGAYSUA = "";
		this.NGAYTAO = "";
		this.NGUOISUA = "";
		this.NGUOITAO = "";
		this.TRANGTHAI = "";
		this.chixem = "0";
		this.db = new dbutils();
	}

	public void setMsg(String msg) {
		this.MSG = msg;
	}

	public String getMsg() {
		return this.MSG;
	}

	public void setPK_SEQ(String pk_seq) {
		this.PK_SEQ = pk_seq;
	}

	public String getPK_SEQ() {
		return this.PK_SEQ;
	}

	public void setCongty_fk(String congty_fk) {
		this.CONGTY_FK = congty_fk;
	}

	public String getCongty_fk() {
		return this.CONGTY_FK;
	}

	public void setTen(String ten) {
		this.TEN = ten;
	}

	public String getTen() {
		return this.TEN;
	}

	public void setDiachi(String diachi) {
		this.DIACHI = diachi;
	}

	public String getDiachi() {
		return this.DIACHI;
	}

	public void setNguoitao(String nguoitao) {
		this.NGUOITAO = nguoitao;
	}

	public String getNguoitao() {
		return this.NGUOITAO;
	}

	public void setNguoisua(String nguoisua) {
		this.NGUOISUA = nguoisua;
	}

	public String getNguoisua() {
		return this.NGUOISUA;
	}

	public void setNgaytao(String ngaytao) {
		this.NGAYTAO = ngaytao;
	}

	public String getNgaytao() {
		return this.NGAYTAO;
	}

	public void setNgaysua(String ngaysua) {
		this.NGAYSUA = ngaysua;
	}

	public String getNgaysua() {
		return this.NGAYSUA;
	}

	public void setMa(String ma) {
		this.MA = ma;
	}

	public String getMa() {
		return this.MA;
	}

	public void setTrangthai(String trangthai) {
		this.TRANGTHAI = trangthai;
	}

	public String getTrangthai() {
		return this.TRANGTHAI;
	}

	public void setRsKMCK(ResultSet rs) {
		this.rsHMCK = rs;
	}

	public ResultSet getRsKMCK() {
		return this.rsHMCK;
	}

	public boolean init() {
		Utility util = new Utility();
		String query = "select isnull(kmck.pk_seq,'') as pk_seq , isnull(kmck.MAKMCK,'') as ma ,isnull(kmck.TENKMCK,'') as ten,isnull(kmck.NGAYTAO,'') as ngaytao,"
				+ " isnull(nvtao.ten,'') as nguoitao , isnull(kmck.ngaysua,'') as ngaysua , isnull(nvsua.ten,'') as nguoisua ,kmck.trangthai as trangthai"
				+ " from ERP_KHOANMUCCHIETKHAU kmck" + " left join NHANVIEN nvtao on nvtao.PK_SEQ = kmck.NGUOITAO"
				+ " left join NHANVIEN nvsua on nvsua.PK_SEQ = kmck.NGUOISUA where 1=1";
		if (this.MA.length() > 0)
			query += " AND kmck.MAKMCK LIKE N'%" + this.MA + "%'";
		if (this.TEN.length() > 0)
			query += " AND dbo.ftBoDau(kmck.TENKMCK) LIKE N'%" + util.replaceAEIOU(this.TEN) + "%'";
		if (this.TRANGTHAI.length() > 0)
			query += " AND kmck.trangthai = " + this.TRANGTHAI + "";
		System.out.println(this.TRANGTHAI);
		try {
			this.rsHMCK = this.db.get(query);
			return true;
		} catch (Exception e) {
			System.out.println("Lỗi " + query);
			return false;
		}
	}

	public String Delete() {
		try {
			/*db.getConnection().setAutoCommit(false);
			String query = "";
			query = " DELETE ERP_KHOANMUCCHIETKHAU_KBH WHERE KMCK_FK = " + this.PK_SEQ;
			if (!this.db.update(query)) {
				db.getConnection().rollback();
				this.MSG = "Không thể xóa ERP_KHOANMUCCHIETKHAU_KBH ";
				return this.MSG;
			}*/

			String query = "UPDATE ERP_KHOANMUCCHIETKHAU SET TRANGTHAI=0 WHERE PK_SEQ = " + this.PK_SEQ;
			if (!this.db.update(query)) {
				db.getConnection().rollback();
				this.MSG = "Không thể cập nhật trạng thái ERP_KHOANMUCCHIETKHAU";
				return this.MSG;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			return "Xảy ra lỗi khi xóa khoản mục chiết khấu";
		}
		return "";
	}

	public void DBClose() {
		try {
			if (this.rsHMCK != null)
				this.rsHMCK.close();
			if (this.db != null)
				this.db.shutDown();
		} catch (java.sql.SQLException e) {
		}
	}

	public void setChixem(String chixem) {

		this.chixem = chixem;
	}

	public String getChixem() {

		return this.chixem;
	}
}