package geso.traphaco.center.beans.sodudauky.imp;

import geso.traphaco.center.beans.sodudauky.ISoDuDauKyList;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SoDuDauKyList extends Phan_Trang implements ISoDuDauKyList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ngayBatDau;
	private String ngayKetThuc;
	private String soChungTu;
	private String ma;
	private String chiNhanhId;
	private String congTyId;
	private String userId;
	private String nppId;
	private String msg;
	
	private List<Erp_Item> chiNhanhList;
	private List<Erp_ListItem> list;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public SoDuDauKyList()
	{
		this.ngayBatDau = "2016-06-30";
		this.ngayKetThuc = Utility.getCurrentDate();
		this.soChungTu = "";
		this.ma = "";
		this.chiNhanhId = "";
		this.userId = "";
		this.nppId = "";
		this.msg = "";
		
		this.chiNhanhList = new ArrayList<Erp_Item>();
		this.list = new ArrayList<Erp_ListItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		this.list.clear();
		
		String query = 
			"select bt.PK_SEQ id, '' ma, isNull(npp.ma, '') + ' - ' + ISNULL(npp.ten, '') as ten\n" +
			", '' NGAYBUTTOAN, bt.TRANGTHAI, t.TEN as nguoiTao, s.TEN as nguoiSua\n" +
			"from ERP_CHUNGTUDAUKY bt\n" +
			"left join nhanvien t on t.PK_SEQ = bt.NGUOITAO\n" +
			"left join nhanvien s on s.PK_SEQ = bt.NGUOISUA\n" +
			"left join NHAPHANPHOI npp on npp.pk_seq = bt.npp_fk\n" +
			"where 1 = 1 \n";
		
		if (this.chiNhanhId.trim().length() > 0)
			query += "and bt.npp_fk = " + this.chiNhanhId + "\n";
		
		if (this.ngayBatDau.trim().length() > 0)
			query += "and bt.ngayTao >= '" + this.ngayBatDau + "'\n";
		
		if (this.ngayKetThuc.trim().length() > 0)
			query += "and bt.ngayTao <= '" + this.ngayKetThuc + "'\n";
			
		if (this.soChungTu.trim().length() > 0)
			query += "and bt.maChungTu like '" + this.soChungTu + "'\n";
		
//		String query_init = createSplittingData_ListNew(this.db, 25, 10, " id desc, ngayButToan desc", query) ;
		try {
//			ResultSet rs = db.get(query_init);
			ResultSet rs = createSplittingDataNew(this.db, 25, 10, " id desc, ngayButToan desc", query);
			if (rs != null)
			{
				while (rs.next())
				{
					Erp_ListItem item = new Erp_ListItem();
					String id = rs.getString("id");
					String ma = rs.getString("ma");
					String ten = rs.getString("ten");
					String ngayTao = rs.getString("ngayButToan");
					String trangThai = rs.getString("trangThai");
					String nguoiTao = rs.getString("nguoiTao");
					String nguoiSua = rs.getString("nguoiSua");
					
					item.setId(id);
					item.setMa(ma);
					item.setTen(ten);
					item.setNgayTao(ngayTao);
					item.setNguoiTao(nguoiTao);
					item.setNguoitSua(nguoiSua);
					item.setTrangThai(trangThai);
					this.list.add(item);
				}
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		initList();
	}
	
	private void initList() {
		String query = "select npp.pk_seq, npp.MA + ' - ' + npp.ten as ten from NHAPHANPHOI npp where npp.trangThai = 1 and npp.isKhachHang = 0";
		Erp_Item.getListFromQuery(db, query, this.chiNhanhList);
	}
	

	public void DBClose()
	{
		try
		{
		}
		catch (Exception er)
		{
			er.printStackTrace();
		}finally{
			if (db != null)
			{
				db.shutDown();
			}
		}
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

	public String getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(String ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public String getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(String ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getChiNhanhId() {
		return chiNhanhId;
	}

	public void setChiNhanhId(String chiNhanhId) {
		this.chiNhanhId = chiNhanhId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Erp_ListItem> getList() {
		return list;
	}

	public void setList(List<Erp_ListItem> list) {
		this.list = list;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setChiNhanhList(List<Erp_Item> chiNhanhList) {
		this.chiNhanhList = chiNhanhList;
	}

	public List<Erp_Item> getChiNhanhList() {
		return chiNhanhList;
	}
}