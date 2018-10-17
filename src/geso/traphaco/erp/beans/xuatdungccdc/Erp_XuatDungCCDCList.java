package geso.traphaco.erp.beans.xuatdungccdc;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Erp_XuatDungCCDCList  extends Phan_Trang implements IErp_XuatDungCCDCList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ngayBatDau;
	private String ngayKetThuc;
	private String soChungTu;
	private String maCCDC;
	private String trangThai;
	private String congTyId;
	private String msg;
	
	private List<Erp_XuatDungItem> xuatDungList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public Erp_XuatDungCCDCList()
	{
		this.ngayBatDau = "";
		this.ngayKetThuc = "";
		this.soChungTu = "";
		this.maCCDC = "";
		this.trangThai = "";
		this.msg = "";
		
		this.xuatDungList = new ArrayList<Erp_XuatDungItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		List<Integer> l = new ArrayList<Integer>();
		geso.traphaco.center.util.Utility.GetThangKhoaSoMax(this.db, l);
		
		int thangKSMax = l.get(0);
		int namKSMax = l.get(1); 
		
		String query =
			"select xd.PK_SEQ, cc.MA \n" +
			", case xd.TRANGTHAI when 0 then N'Chưa chốt' \n" +
			"when 1 then N'Đã chốt' \n" +
			"when 2 then N'Đã xóa' \n" +
			"when 3 then N'Đã áp giá sau cùng' end as trangThai \n" +
			", xd.NGAYTAO, nvt.TEN as nguoiTao, xd.NGAYSUA, nvs.TEN as nguoiSua \n" +
			//Lấy tháng đã khấu hao nhưng chưa khóa sổ
			", isNull((	\n" +
			"	select count(*) \n" +
			"	from ERP_KHAUHAOCCDC \n" +
			"	where CCDC_FK = xd.CCDC_FK and trangThai = 1 \n" +
			"		and (nam > " + namKSMax+ " or (nam = " + namKSMax + " and thang > " + thangKSMax + "))), 0) as thangDaPB\n" +
			"from ERP_XUATDUNG xd \n" +
			"inner join ERP_CONGCUDUNGCU cc on cc.PK_SEQ = xd.CCDC_FK \n" +
			"left join NHANVIEN nvt on nvt.PK_SEQ = xd.NGUOITAO \n" +
			"left join NHANVIEN nvs on nvs.PK_SEQ = xd.NGUOISUA \n" +
			"where 1 = 1 ";
			
		if (this.trangThai.trim().length() > 0)
			query += "and xd.TRANGTHAI = '" + this.trangThai + "' \n";
		if (this.maCCDC.trim().length() > 0)
			query += "and cc.MA = '" + this.maCCDC + "' \n";
		if (this.soChungTu.trim().length() > 0)
			query += " and xd.PK_SEQ = " + this.soChungTu + " \n";
		if (this.ngayBatDau.trim().length() > 0)
			query += " and xd.ngayTao >= '" + this.ngayBatDau + "' \n";
		if (this.ngayKetThuc.trim().length() > 0)
			query += " and xd.ngayTao <= '" + this.ngayKetThuc + "' \n";
		try {
			ResultSet rs = createSplittingData(50, 10, "NGAYTAO desc, NGAYSUA desc, PK_SEQ desc", query);
			
			if (rs != null)
			{
				while (rs.next())
				{
					String id = rs.getString("PK_SEQ");
					String ccdc = rs.getString("MA");
					String trangThai = rs.getString("trangThai");
					String ngayTao = rs.getString("ngayTao");
					String nguoiTao = rs.getString("nguoiTao");
					String ngaySua = rs.getString("ngaySua");
					String nguoiSua = rs.getString("nguoiSua");
					Integer thangDaPB = rs.getInt("thangDaPB");
					Erp_XuatDungItem item = new Erp_XuatDungItem(id, ccdc, ngayTao, ngaySua, nguoiTao, nguoiSua, trangThai);
					item.setThangDaPB(thangDaPB);
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
	public List<Erp_XuatDungItem> getXuatDungList() {
		return xuatDungList;
	}
	public void setXuatDungList(List<Erp_XuatDungItem> xuatDungList) {
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
