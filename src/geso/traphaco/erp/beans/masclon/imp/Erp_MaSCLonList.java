package geso.traphaco.erp.beans.masclon.imp;

import geso.dms.center.util.PhanTrang;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.masclon.IErp_MaSCLonList;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_Item;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Erp_MaSCLonList extends Phan_Trang implements IErp_MaSCLonList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ngayBatDau;
	private String ngayKetThuc;
	private String soChungTu;
	private String ma;
	private String taiKhoanId;
	private String taiSanId;
	private String trangThai;
	private String congTyId;
	private String msg;
	
	private List<Erp_Item> taiKhoanList;
	private List<Erp_Item> taiSanCDList;
	private List<Erp_Item> dvkdList;
	private List<Erp_ListItem> list;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public Erp_MaSCLonList()
	{
		this.ngayBatDau = "";
		this.ngayKetThuc = "";
		this.soChungTu = "";
		this.ma = "";
		this.taiKhoanId = "";
		this.taiSanId = "";
		this.trangThai = "";
		this.msg = "";
		this.taiKhoanList = new ArrayList<Erp_Item>();
		this.taiSanCDList = new ArrayList<Erp_Item>();
		this.list = new ArrayList<Erp_ListItem>();
		
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
			"select msc.PK_SEQ, msc.MA, msc.TEN, msc.TAIKHOAN_FK, msc.TAISAN_FK\n" +
			",msc.TRANGTHAI, msc.NGAYTAO, msc.NGAYSUA, nvt.TEN as NGUOITAO\n" +
			", nvs.TEN as NGUOISUA, ts.ten as taiSan, tk.SOHIEUTAIKHOAN\n" +
			", case msc.trangThai when 0 then N'Chưa chốt'\n" +
			"when 1 then N'Đã chốt'\n" +
			"when 2 then N'Đã xóa' end as trangThaiText\n" +
			", isNull((	\n" +
			"	select count(*) \n" +
			"	from ERP_KHAUHAOTAISAN \n" +
			"	where TAISAN_FK = msc.TAISAN_FK and trangThai = 1 \n" +
			"		and (nam > " + namKSMax+ " or (nam = " + namKSMax + " and thang > " + thangKSMax + "))), 0) as thangDaPB\n" +

			"from ERP_MASCLON msc\n" +
			"inner join nhanvien nvt on nvt.PK_SEQ = msc.NGUOITAO\n" +
			"left join nhanvien nvs on nvs.PK_SEQ = msc.NGUOISUA\n" +
			"inner join ERP_TAIKHOANKT tk on tk.PK_SEQ = msc.TAIKHOAN_FK\n" +
			"inner join ERP_TAISANCODINH ts on ts.PK_SEQ = msc.TAISAN_FK\n" +
			"where 1 = 1\n" +
			"and msc.CONGTY_FK = " + this.congTyId;
			
		if (this.trangThai.trim().length() > 0)
			query += "and msc.TRANGTHAI = '" + this.trangThai + "' \n";
		
		if (this.taiKhoanId.trim().length() > 0)
			query += "and msc.TAIKHOAN_FK = '" + this.taiKhoanId + "' \n";
		
		if (this.taiSanId.trim().length() > 0)
			query += "and msc.TAISAN_FK = '" + this.taiSanId + "' \n";
		
		if (this.soChungTu.trim().length() > 0)
			query += " and msc.PK_SEQ = " + this.soChungTu + " \n";
		
		if (this.ma.trim().length() > 0)
			query += " and msc.ma like '" + this.ma + "' \n";
		
		if (this.ngayBatDau.trim().length() > 0)
			query += " and msc.ngayTao >= '" + this.ngayBatDau + "' \n";
		
		if (this.ngayKetThuc.trim().length() > 0)
			query += " and msc.ngayTao <= '" + this.ngayKetThuc + "' \n";
		try {
			ResultSet rs = createSplittingData(50, 10, "NGAYTAO desc, NGAYSUA desc, PK_SEQ desc", query);
			
			if (rs != null)
			{
				while (rs.next())
				{
					String id = rs.getString("PK_SEQ");
					String ma = rs.getString("MA");
					String ten = rs.getString("TEN");
					String trangThai = rs.getString("trangThai");
					String ngayTao = rs.getString("ngayTao");
					String nguoiTao = rs.getString("nguoiTao");
					String ngaySua = rs.getString("ngaySua");
					String nguoiSua = rs.getString("nguoiSua");
					Erp_ListItem item = new Erp_ListItem(id, ma, ten, ngayTao, ngaySua, nguoiTao, nguoiSua, trangThai);
					item.setField1(rs.getString("thangDaPB"));
					this.list.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		initList();
	}
	
	private void initList() {
		//Tài khoản kế toán
		String query = 
			"select tk.PK_SEQ, isNull(tk.SOHIEUTAIKHOAN, '') + ' -- ' + isNull(tk.TENTAIKHOAN, '') as ten\n" +
			"from ERP_TAIKHOANKT tk\n" +
			"where tk.TRANGTHAI = 1 and CONGTY_FK = " + this.congTyId + " and tk.soHieuTaiKhoan like '241%'\n" +
			"order by tk.SOHIEUTAIKHOAN";
		System.out.println("query lay tai khoan ke toan : \n " + query + "\n===================");

		Erp_Item.getListFromQuery(db, query, this.taiKhoanList);
		
		//Tài sản cố định
		this.taiSanCDList.clear();
		query = 
			"select ts.PK_SEQ, isNull(ts.ma, '') + ' -- ' + isNull(ts.TEN, '') as ten\n" +
			"from ERP_TAISANCODINH ts\n" +
			"where CONGTY_FK = " + this.congTyId + "\n" +
			"order by ts.pk_seq";
		System.out.println("cau lenh lay tai khoan co dinh: \n" + query + "\n----------------------------------------------");
		Erp_Item.getListFromQuery(db, query, this.taiSanCDList);
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

	public void setTaiKhoanId(String taiKhoanId) {
		this.taiKhoanId = taiKhoanId;
	}

	public String getTaiKhoanId() {
		return taiKhoanId;
	}

	public void setTaiSanId(String taiSanId) {
		this.taiSanId = taiSanId;
	}

	public String getTaiSanId() {
		return taiSanId;
	}

	public void setList(List<Erp_ListItem> list) {
		this.list = list;
	}

	public List<Erp_ListItem> getList() {
		return list;
	}

	public void setTaiKhoanList(List<Erp_Item> taiKhoanList) {
		this.taiKhoanList = taiKhoanList;
	}

	public List<Erp_Item> getTaiKhoanList() {
		return taiKhoanList;
	}

	public void setTaiSanCDList(List<Erp_Item> taiSanCDList) {
		this.taiSanCDList = taiSanCDList;
	}

	public List<Erp_Item> getTaiSanCDList() {
		return taiSanCDList;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getMa() {
		return ma;
	}

}