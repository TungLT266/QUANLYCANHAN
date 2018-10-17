package geso.traphaco.erp.beans.khaibaomau.imp;

import geso.dms.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.TitleItem;
import geso.traphaco.center.util.ViewItem;
import geso.traphaco.erp.beans.khaibaomau.IErpKhaiBaoMauList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ErpKhaiBaoMauList extends Phan_Trang implements IErpKhaiBaoMauList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ngayBatDau;
	private String ngayKetThuc;
	private String soChungTu;
	private String ma;
	private String ten;
	private String trangThai;
	private String congTyId;
	private String msg;
	
	private static final List<TitleItem> titleList = initTitleList();
	private List<List<ViewItem>> viewList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public ErpKhaiBaoMauList()
	{
		this.ngayBatDau = "";
		this.ngayKetThuc = "";
		this.soChungTu = "";
		this.ma = "";
		this.ten = "";
		this.trangThai = "";
		this.msg = "";
		
		this.viewList = new ArrayList<List<ViewItem>>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	private static List<TitleItem> initTitleList() {
		List<TitleItem> titleList = new ArrayList<TitleItem>();
		
		TitleItem item = new TitleItem("STT", "left", 10);
		titleList.add(item);
		item = new TitleItem("Mã", "left", 10);
		titleList.add(item);
		item = new TitleItem("Tên", "left", 60);
		titleList.add(item);
		item = new TitleItem("Tác vụ", "left", 25);
		titleList.add(item);
		return titleList;
	}

	public void init()
	{
		String query = 
			"select m.MASO, m.TENCHITIEU, 'update, delete' as tacVu\n" +
			"from ERP_MAUCDKT m\n" +
			"where 1 = 1 \n";
		
		if (this.ma.trim().length() > 0)
			query += "m.maSo like '%" + this.ma + "%'\n";
		
		if (this.ten.trim().length() > 0)
			query += "m.tenTieuChi like '%" + this.ten + "%'\n";
		
		try {
			ResultSet rs;
			rs = createSplittingData(50, 10, "MASO", query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int rowNumber = rsmd.getColumnCount();
			
			if (rs != null)
			{
				while (rs.next())
				{	
					List<ViewItem> row = new ArrayList<ViewItem>();
					this.viewList.add(row);
					for (int i = 0; i < rowNumber; i++)
					{
						ViewItem item = new ViewItem(rs.getString(i + 1), ((i == rowNumber - 1) ? "center" : "left"), "black");
						row.add(item);
					}
				}
				rs.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void DBClose()
	{
		if (db != null)
		{
			db.shutDown();
		}
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
	
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
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
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}


	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getMa() {
		return ma;
	}

	public List<TitleItem> getTitlelist() {
		return titleList;
	}

	public void setViewList(List<List<ViewItem>> viewList) {
		this.viewList = viewList;
	}

	public List<List<ViewItem>> getViewList() {
		return viewList;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}
}