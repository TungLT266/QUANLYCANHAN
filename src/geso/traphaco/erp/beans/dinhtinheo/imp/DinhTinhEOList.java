package geso.traphaco.erp.beans.dinhtinheo.imp;

import java.sql.ResultSet;

import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.dinhtinheo.IDinhTinhEOList;
import geso.traphaco.erp.db.sql.dbutils;

public class DinhTinhEOList  extends Phan_Trang implements IDinhTinhEOList
{
	private static final long serialVersionUID = 1L;
	
	String userId;
	String msg;
	String sophieu;
	String trangthai;
	String loai;
	String khoId;
	String solo="";
	
	public String getSolo() {
		return solo;
	}

	public void setSolo(String solo) {
		this.solo = solo;
	}
	ResultSet rsKho;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	private ResultSet rsDinhTinhEO;
	
	dbutils db;

	public DinhTinhEOList() {
		super();
		this.userId = "";
		this.msg = "";
		this.sophieu = "";
		this.trangthai = "";
		this.loai = "";
		this.khoId = "";
		this.num = 1;
		this.currentPages = 1;
		this.db = new dbutils();
	}
	
	public DinhTinhEOList(String userId, String msg, String sophieu, String trangthai, String loai, String khoId, ResultSet rsKho, int num,
			int[] listPages, int currentPages, ResultSet rsDinhTinhEO, dbutils db) {
		super();
		this.userId = userId;
		this.msg = msg;
		this.sophieu = sophieu;
		this.trangthai = trangthai;
		this.loai = loai;
		this.khoId = khoId;
		this.rsKho = rsKho;
		this.num = num;
		this.listPages = listPages;
		this.currentPages = currentPages;
		this.rsDinhTinhEO = rsDinhTinhEO;
		this.db = db;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSophieu() {
		return sophieu;
	}

	public void setSophieu(String sophieu) {
		this.sophieu = sophieu;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getKhoId() {
		return khoId;
	}

	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}

	public ResultSet getRsKho() {
		return rsKho;
	}

	public void setRsKho(ResultSet rsKho) {
		this.rsKho = rsKho;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int[] getListPages() {
		return listPages;
	}

	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	public int getCurrentPages() {
		return currentPages;
	}

	public void setCurrentPages(int currentPages) {
		this.currentPages = currentPages;
	}

	public ResultSet getRsDinhTinhEO() {
		return rsDinhTinhEO;
	}

	public void setRsDinhTinhEO(ResultSet rsDinhTinhEO) {
		this.rsDinhTinhEO = rsDinhTinhEO;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}
	
	private void createKhoRs (){
		String sql = " select  PK_SEQ, TEN from ERP_KHOTT where trangthai= '1'";
		this.rsKho = this.db.get(sql);
	}
	
	public void init(String query)
	{
		String sql = " select a.PK_SEQ, DIENGIAI, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, a.LOAI, " +
					 " kho.TEN as TENKHO, nv1.TEN as NGUOITAO, nv2.TEN as NGUOISUA " +
					 " from ERP_DINHTINH_EO a " +
					 " inner join NHANVIEN nv1 on nv1.PK_SEQ = a.NGUOITAO " +
					 " inner join NHANVIEN nv2 on nv2.PK_SEQ = a.NGUOISUA " +
					 " left join ERP_KHOTT kho on kho.PK_SEQ = a.KHO_FK where 1=1 ";
		
		if(this.loai.trim().length() >0){
			sql += " AND a.LOAI =" + this.loai;
		}
		
		if(this.khoId.trim().length() >0){
			sql += " AND a.KHO_FK =" + this.khoId;
		}
		
		if(this.sophieu.trim().length() >0){
			sql += " AND a.PK_SEQ like  '%" + this.sophieu +"%'";
		}
		
		if(this.trangthai.trim().length() >0){
			sql += " AND a.TRANGTHAI = '" + this.trangthai +"'";
		}
		
		if(this.solo.trim().length()>0){
			sql += " AND a.pk_seq in (select DINHTINH_FK  from ERP_DINHTINH_EO_DETAILS where solo like N'%"+ this.solo.trim()+"%') ";
		}
		
		System.out.println(":: LAY PHIEU DINH TINH: " + sql );
		sql += "  order by a.pk_seq desc ";
		/*sql += " AND a.trangthai != 2 order by a.pk_seq desc ";*/
		this.rsDinhTinhEO = this.db.get(sql);
		
		this.createKhoRs();
		
	}
	public void DBclose(){
		this.db.shutDown();
	}
}