package geso.traphaco.erp.beans.danhmucduan;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Erp_DanhMucDuAnList  extends Phan_Trang implements IErp_DanhMucDuAnList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ngayBatDau;
	private String ngayKetThuc;
	private String soChungTu;
	private String ma;
	private String trangThai;
	private String congTyId;
	private String msg;
	
	private List<Erp_ListItem> xuatDungList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public Erp_DanhMucDuAnList()
	{
		this.ngayBatDau = "";
		this.ngayKetThuc = "";
		this.soChungTu = "";
		this.ma = "";
		this.trangThai = "";
		this.msg = "";
		
		this.xuatDungList = new ArrayList<Erp_ListItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		List<Integer> l = new ArrayList<Integer>();
		
		String query =
			"select dm.PK_SEQ, dm.MA, dm.TEN, nvt.TEN as nguoiTao\n" +
			", isNull(nvs.TEN, '') as nguoiSua, dm.NGAYTAO, isNull(dm.NGAYSUA, '') as ngaySua, dm.TRANGTHAI\n" +
			", case dm.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' when 2 then N'Đã xóa' end as tenTrangThai\n" +
			"from ERP_DANHMUCDUAN dm\n" +
			"left join nhanvien nvt on nvt.PK_SEQ = dm.NGUOITAO\n" +
			"left join nhanvien nvs on nvs.PK_SEQ = dm.NGUOISUA\n" +
			"where CONGTY_FK = " + this.congTyId;
			
		if (this.trangThai.trim().length() > 0)
			query += "and dm.TRANGTHAI = '" + this.trangThai + "' \n";
		if (this.ma.trim().length() > 0)
			query += "and dm.MA = '" + this.ma + "' \n";
		if (this.soChungTu.trim().length() > 0)
			query += " and dm.PK_SEQ = " + this.soChungTu + " \n";
		if (this.ngayBatDau.trim().length() > 0)
			query += " and dm.ngayTao >= '" + this.ngayBatDau + "' \n";
		if (this.ngayKetThuc.trim().length() > 0)
			query += " and dm.ngayTao <= '" + this.ngayKetThuc + "' \n";
		
		
		try {
			ResultSet rs = createSplittingData(50, 10, "NGAYTAO desc, NGAYSUA desc, PK_SEQ desc", query);
			
			if (rs != null)
			{
				while (rs.next())
				{
					String id = rs.getString("PK_SEQ");
					String ma = rs.getString("MA");
					String ten = rs.getString("ten");
					String trangThai = rs.getString("trangThai");
					String tenTrangThai = rs.getString("tenTrangThai");
					String ngayTao = rs.getString("ngayTao");
					String nguoiTao = rs.getString("nguoiTao");
					String ngaySua = rs.getString("ngaySua");
					String nguoiSua = rs.getString("nguoiSua");
					Erp_ListItem item = new Erp_ListItem(id, ma, ten, ngayTao, ngaySua, nguoiTao, nguoiSua, trangThai);
					item.setTenTrangThai(tenTrangThai);
					this.xuatDungList.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public List<Erp_ListItem> getXuatDungList() {
		return xuatDungList;
	}
	public void setXuatDungList(List<Erp_ListItem> xuatDungList) {
		this.xuatDungList = xuatDungList;
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
}