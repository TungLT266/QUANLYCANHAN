package geso.traphaco.erp.beans.doituongkhac.imp;

import geso.dms.center.util.PhanTrang;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.doituongkhac.IErpDoiTuongKhacList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ErpDoiTuongKhacList extends Phan_Trang implements IErpDoiTuongKhacList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7165493842922886938L;
	private String id;
	private String userId;
	private String congTyId;
	private String maTenDoiTuong;
	private String nganHangId;
	private String chiNhanhId;
	private String nppId;
	
	private String msg;
	
	private List<Erp_Item> nppList;
	private List<Erp_Item> chiNhanhList;
	private List<Erp_Item> nganHangList;
	private List<Erp_ListItem> viewList;
	
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDoiTuongKhacList()
	{
		this.id = "";
		this.userId = "";
		this.congTyId = "";
		this.maTenDoiTuong = "";
		this.nganHangId = "";
		this.chiNhanhId = "";

		this.msg = "";
		
		this.nppList = new ArrayList<Erp_Item>();
		this.chiNhanhList = new ArrayList<Erp_Item>();
		this.nganHangList = new ArrayList<Erp_Item>();
		this.viewList = new ArrayList<Erp_ListItem>();
		
		this.currentPages = 1;
		this.num = 1;
		
		this.db = new dbutils();
	}

	public ErpDoiTuongKhacList(String id)
	{
		this.id = id;
		this.userId = "";
		this.congTyId = "";
		this.maTenDoiTuong = "";
		this.nganHangId = "";
		this.chiNhanhId = "";
		
		this.msg = "";
		
		this.nppList = new ArrayList<Erp_Item>();
		this.chiNhanhList = new ArrayList<Erp_Item>();
		this.nganHangList = new ArrayList<Erp_Item>();
		this.viewList = new ArrayList<Erp_ListItem>();
		
		this.currentPages = 1;
		this.num = 1;
		
		this.db = new dbutils();
	}

	public void init()
	{
		String query = 
			"select dtk.PK_SEQ id, dtk.MADOITUONG ma, dtk.TENDOITUONG ten, dtk.TRANGTHAI, npp.ten tenNpp, nvt.TEN NGUOITAO, nvs.TEN NGUOISUA, dtk.NGAYTAO, dtk.NGAYSUA \n" +
			"from ERP_DOITUONGKHAC dtk\n" +
			"left join NHANVIEN nvt on nvt.PK_SEQ = dtk.NGUOITAO\n" +
			"left join NHANVIEN nvs on nvs.PK_SEQ = dtk.NGUOISUA\n" +
			"left join NHAPHANPHOI npp on npp.PK_SEQ = dtk.NPP_FK\n" +
			"where 1 = 1\n";
		
		if (this.id != null && this.id.trim().length() > 0)
			query += "and dtk.pk_seq = " + this.id + "\n";

		if (this.maTenDoiTuong != null && this.maTenDoiTuong.trim().length() > 0)
			query += "and (dtk.tendoituong like N'%" + this.maTenDoiTuong + "%' or dtk.madoituong like N'%" + this.maTenDoiTuong + "%' )\n";

		if (this.nganHangId != null && this.nganHangId.trim().length() > 0)
			query += "and (dtk.nganHang_FK like N'%" + this.nganHangId + "%')\n";

		if (this.chiNhanhId != null && this.chiNhanhId.trim().length() > 0)
			query += "and (dtk.chiNhanh_FK like N'%" + this.chiNhanhId + "%')\n";

		if (this.nppId != null && this.nppId.trim().length() > 0)
			query += "and (dtk.npp_FK like N'%" + this.nppId + "%')\n";

		ResultSet rs = createSplittingDataNew(this.db, 50, 10, "NGAYTAO desc, NGAYSUA desc, id desc", query);
		System.out.println("Init ncc: \n" + query + "\n-----------------------");
		if (rs != null)
		{
			try
			{
				Erp_ListItem.getListFromQuery(rs, this.viewList);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}	
		}
		
		initList();
	}

	private void initList() {
		String query = "select PK_SEQ, ISNULL(ma, '') + ' - ' + ISNULL(MaFAST, '') + ' - ' + ISNULL(ten, '') ten from NHAPHANPHOI npp where isKHACHHANG = 1 ";
		Erp_Item.getListFromQuery(db, query, this.nppList);
		query = "select PK_SEQ, ISNULL(ma, '') + ' - ' + ISNULL(ten, '') ten from ERP_NGANHANG nh";
		Erp_Item.getListFromQuery(db, query, this.nganHangList);
		query = "select PK_SEQ, ISNULL(ma, '') + ' - ' + ISNULL(ten, '') ten from ERP_CHINHANH cn";
		Erp_Item.getListFromQuery(db, query, this.chiNhanhList);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getMaTenDoiTuong() {
		return maTenDoiTuong;
	}

	public void setMaTenDoiTuong(String maTenDoiTuong) {
		this.maTenDoiTuong = maTenDoiTuong;
	}

	public String getNganHangId() {
		return nganHangId;
	}

	public void setNganHangId(String nganHangId) {
		this.nganHangId = nganHangId;
	}

	public String getChiNhanhId() {
		return chiNhanhId;
	}

	public void setChiNhanhId(String chiNhanhId) {
		this.chiNhanhId = chiNhanhId;
	}

	public List<Erp_Item> getNppList() {
		return nppList;
	}

	public void setNppList(List<Erp_Item> nppList) {
		this.nppList = nppList;
	}

	public List<Erp_Item> getChiNhanhList() {
		return chiNhanhList;
	}

	public void setChiNhanhList(List<Erp_Item> chiNhanhList) {
		this.chiNhanhList = chiNhanhList;
	}

	public List<Erp_Item> getNganHangList() {
		return nganHangList;
	}

	public void setNganHangList(List<Erp_Item> nganHangList) {
		this.nganHangList = nganHangList;
	}

	public List<Erp_ListItem> getViewList() {
		return viewList;
	}

	public void setViewList(List<Erp_ListItem> viewList) {
		this.viewList = viewList;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
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
}