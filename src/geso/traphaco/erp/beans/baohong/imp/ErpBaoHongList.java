package geso.traphaco.erp.beans.baohong.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.erp.beans.baohong.IErpBaoHongList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ErpBaoHongList  extends Phan_Trang implements IErpBaoHongList
{
	private static final long serialVersionUID = 1L;
	private String ngayBatDau;
	private String ngayKetThuc;
	private String soChungTu;
	private String maCCDC;
	private String trangThai;
	private String congTyId;
	private String msg;
	
	private List<Erp_ListItem> viewList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public ErpBaoHongList()
	{
		this.ngayBatDau = "";
		this.ngayKetThuc = "";
		this.soChungTu = "";
		this.maCCDC = "";
		this.trangThai = "";
		this.msg = "";
		
		this.viewList = new ArrayList<Erp_ListItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		String query =
			"select cc.PK_SEQ, cc.MA, cc.dienGiai, isNull(cc.ngayBaoHong, '') ngayBaoHong, cc.trangThai\n" +
			"from ERP_CONGCUDUNGCU cc\n" +
			"where isBaoHong is not null and trangThai != 2\n";
		
		if (this.ngayBatDau.trim().length() > 0)
			query += "and cc.ngayBaoHong >= '" + this.ngayBatDau + "'";
		
		if (this.ngayKetThuc.trim().length() > 0)
			query += "and cc.ngayBaoHong <= '" + this.ngayKetThuc + "'";
		
		if (this.maCCDC.trim().length() > 0)
			query += "and cc.ma like '" + this.maCCDC + "'";
		
		try {
			ResultSet rs = createSplittingData(50, 10, "ngayBaoHong desc, PK_SEQ desc", query);
			
			if (rs != null)
			{
				while (rs.next())
				{
					String id = rs.getString("PK_SEQ");
					String ma = rs.getString("MA");
					String ten = rs.getString("dienGiai");
					String trangThai = rs.getString("trangThai");
					String ngayBaoHong = rs.getString("ngayBaoHong");
					Erp_ListItem item = new Erp_ListItem();
					item.setId(id);
					item.setMa(ma);
					item.setTen(ten);
					item.setNgayTao(ngayBaoHong);
					item.setTrangThai(trangThai);
					this.viewList.add(item);
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
	public String getMaCCDC() {
		return maCCDC;
	}
	public void setMaCCDC(String maCCDC) {
		this.maCCDC = maCCDC;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public List<Erp_ListItem> getViewList() {
		return viewList;
	}
	public void setViewList(List<Erp_ListItem> viewList) {
		this.viewList = viewList;
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
