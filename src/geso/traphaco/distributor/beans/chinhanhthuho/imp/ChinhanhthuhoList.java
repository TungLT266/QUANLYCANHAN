package geso.traphaco.distributor.beans.chinhanhthuho.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.distributor.beans.chinhanhthuho.IChinhanhthuhoList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Erp_ListItem;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ChinhanhthuhoList extends Phan_Trang implements IChinhanhthuhoList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String tenKH;
	String chiNhanhId;
	ResultSet chiNhanhRs;
	String msg;
	dbutils db;
	ResultSet cnthRs;
	String congtyId;
	
	private List<Erp_ListItem> viewList;

	
	public ChinhanhthuhoList()
	{
		this.tungay = "";
		this.denngay = "";
		this.tenKH="";
		this.msg = "";
		this.congtyId="";
		this.chiNhanhId= "";
		this.db = new dbutils();
		this.viewList = new ArrayList<Erp_ListItem>();
	}


	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	public void setChiNhanhRs(ResultSet chiNhanhRs){
		this.chiNhanhRs = chiNhanhRs;
	}
	public ResultSet getChiNhanhRs(){
		return this.chiNhanhRs;
	}
	public void setChiNhanhId(String chiNhanhId){
		this.chiNhanhId = chiNhanhId;
	}
	public String getChiNhanhId(){
		return this.chiNhanhId;
	}

	

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from HOADON ");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}


	
	public void init() {


		String query = "";
       Utility util = new Utility();
	
			System.out.println("vao day ne___________");
			query = "select CN.pk_seq as pk_seq ,isnull(kh.mafast,'') as mafast ,KH.TEN AS KHACHHANG_FK,NPP.TEN AS NPP_FK,CN.SOTIEN,CN.NGAYTAO,CN.NGAYSUA,NV.TEN AS NGUOITAO,NV.TEN AS NGUOISUA,CN.TRANGTHAI,cn.NGAYGHINHAN " +
					"from ERP_CHINHANHTHUHO CN "+
					"LEFT JOIN " +geso.traphaco.center.util.Utility.prefixDMS+ "KHACHHANG KH on CN.KHACHHANG_FK = KH.PK_SEQ"+
					" LEFT JOIN "+geso.traphaco.center.util.Utility.prefixDMS+"NHAPHANPHOI NPP on CN.NPP_FK=NPP.PK_SEQ"+
					" LEFT JOIN NHANVIEN NV ON CN.NGUOITAO=NV.PK_SEQ"+
					" where CN.TRANGTHAI = 1";

			if(this.tungay.length()>0)
			{
				query +=  " and CN.NGAYTAO >= '"+ this.tungay +"' ";
			}
			if(this.denngay.length()>0)
			{
				query += " and CN.NGAYTAO <= '"+ this.denngay +"' ";
			}
			

			if(this.tenKH.length()>0)
			{ 
				
				query += " and  KH.TEN like N'%" +this.tenKH+ "%'";
			}
			if(this.chiNhanhId.length() >0)
			{
				query += " and cn.npp_fk in ("+this.chiNhanhId+ ")";
			}
			
				
			System.out.println("Cau query là :"+query);
			cnthRs = createSplittingData(25, 10, " NGAYTAO desc", query) ;
			this.createRs();
	
	}
	public void createRs(){
		String query = "select distinct CN.NPP_fk as pk_Seq,NPP.TEN " +
				"from ERP_CHINHANHTHUHO CN "+
				" LEFT JOIN "+geso.traphaco.center.util.Utility.prefixDMS+"NHAPHANPHOI NPP on CN.NPP_FK=NPP.PK_SEQ";
		this.chiNhanhRs = db.get(query);
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}


	public String getTenKH() {
		return tenKH;
	}


	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	
	public List<Erp_ListItem> getViewList() {
		return viewList;
	}

	public void setViewList(List<Erp_ListItem> viewList) {
		this.viewList = viewList;
	}


	@Override
	public int[] getListPages() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setListPages(int[] listPages) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getCurrentPages() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setCurrentPages(int currentPages) {
		// TODO Auto-generated method stub
		
	}


	public ResultSet getCnthRs() {
		return cnthRs;
	}


	public void setCnthRs(ResultSet cnthRs) {
		this.cnthRs = cnthRs;
	}


	
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}


	public String getCongtyId() {
		return congtyId;
	}


	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
	


	
	
}
