package geso.traphaco.erp.beans.nhanhangkhac.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.extentech.ExtenXLS.GetInfo;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhanhangkhac.*;

public class ErpNhanhangList_khac extends Phan_Trang implements IErpNhanhangList_khac
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	
	String trangthai;
	String nccTen;
	String soPO;
	String msg;
	
	String sonhanhang;
	String sohoadon;
	String mactSp = "";
	
	ResultSet nhRs;
	
	ResultSet nguoitaoRs;    
	String nguotaoIds;
	
	String loaimh = "";
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	Utility util;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	String nppTen;
	String sitecode;
	
	public ErpNhanhangList_khac()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.trangthai = "";
		this.soPO = "";
		this.sonhanhang = "";
		this.sohoadon = "";
		this.nccTen = "";
		this.msg = "";
		this.nguotaoIds = "";
		this.loaimh = "";
		
		this.soItems = 25;
		this.util=new Utility();
		currentPages = 1;
		num = 1;
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
		
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.sitecode = "";
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
		
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}

	public String getDvthId() 
	{
		return this.dvthId;
	}

	public void setDvthId(String dvthid) 
	{
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList() 
	{
		return this.dvthRs;
	}

	
	public void setDvthList(ResultSet dvthlist) 
	{
		this.dvthRs = dvthlist;
	}


	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getNhList() 
	{
		return this.nhRs;
	}

	public void setNhList(ResultSet nhlist) 
	{
		this.nhRs = nhlist;
	}

	private String LayDuLieu(String id) 
	{
		String layTK = "";
		
		try
		{
			
			
			if(layTK.trim().length() <= 0)
			{ 
				layTK = "select '' NO_CO, '' id, '' SOHIEUTAIKHOAN, '' as SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP "
						+ "from ERP_NHANHANGKHAC "
                        + "where PK_SEQ = "+ id +" ";
			}
					                             
			layTK += "ORDER BY id, STT, SAPXEP ";
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return layTK;
		
	}
	
	public void init(String search)
	{
		String query = "";
		 
		if(search.length() <= 0)
		{
			query = " SELECT   A.PK_SEQ AS NHID, yc.PK_SEQ as SOYEUCAU, "+
					" A.NGAYNHAN, B.TEN AS DVTHTEN,  "+
					" CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, "+ 
					" A.TRANGTHAI, A.NGAYSUA, A.NGAYTAO, D.TEN AS NGUOITAO, E.TEN AS NGUOISUA "+ 
					" FROM ERP_NHANHANG  A   "+
					" INNER JOIN ERP_DONVITHUCHIEN B ON A.DONVITHUCHIEN_FK = B.PK_SEQ "+ 
					" INNER JOIN ERP_YEUCAUNHAPHANG YC ON YC.PK_SEQ=A.YEUCAUNHAPHANG_FK  "+ 
					" INNER JOIN NHANVIEN D ON A.NGUOITAO = D.PK_SEQ   "+
					" INNER JOIN NHANVIEN E ON A.NGUOISUA = E.PK_SEQ    \n"+
					" WHERE   a.congty_fk = '" + this.congtyId + "'";
					 
		}
		else {
			query = search;
		}
		
		System.out.println(" query init :" + query);
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, " NHID desc ", query);
		
		this.nhRs = db.get(query_init);
		
		
		/*this.nhRs = createSplittingData(50, 10, "NGAYNHAN desc, trangthai asc, nhId desc ", query);*/
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where 1=1 ";
		this.dvthRs = db.get(query);
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_NHANHANGKHAC ) ";
		this.nguoitaoRs = db.get(query);
	}

	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getSoPO() 
	{
		return this.soPO;
	}

	public void setSoPO(String soPO) 
	{
		this.soPO = soPO;
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from ERP_NHANHANGKHAC");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getSoNhanhang() {
		
		return this.sonhanhang;
	}

	
	public void setSoNhanhang(String soNhanhang) {
		
		this.sonhanhang = soNhanhang;
	}

	
	public String getSoHoadon() {
		
		return this.sohoadon;
	}

	
	public void setSoHoadon(String soHoadon) {
		
		this.sohoadon = soHoadon;
	}
	
	public String getNCC() 
	{
		return this.nccTen;
	}

	public void setNCC(String ncc) 
	{
		this.nccTen = ncc;
	}
	
	public ResultSet getNguoitaoRs() {
		
		return this.nguoitaoRs;
	}

	
	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		
		this.nguoitaoRs = nguoitaoRs;
	}

	
	public void setNguoitaoIds(String nspIds) {
		
		this.nguotaoIds = nspIds;
	}

	
	public String getNguoitaoIds() {
		
		return this.nguotaoIds;
	}

	
	public void setMaCtSp(String mact) {
		this.mactSp = mact;
	}

	
	public String getMaCtSp() {
		return this.mactSp;
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}



	public void setLoaimh(String loaidmh) 
	{
		this.loaimh = loaidmh ;
	}

	public String getLoaimh() 
	{
		return this.loaimh;
	}
	@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}
	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}
	


}
