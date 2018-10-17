package geso.traphaco.erp.beans.nhapkhonhamay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkhonhamay.IErpNhapkhonhamayList;

public class ErpNhapkhonhamayList extends Phan_Trang implements IErpNhapkhonhamayList
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	String id;
	String diengiai;
	String trangthai;
	String soPO;
	String msg;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String ngaynhan;
	String mactSp = "";
	
	ResultSet nhRs;
	
	ResultSet nguoitaoRs;    
	String nguotaoIds;
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	Utility util;
	String nppId;
	
	
	public ErpNhapkhonhamayList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.id = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaynhan = "";
		this.soPO = "";
		this.msg = "";
		this.nguotaoIds = "";
		this.util=new Utility();
		currentPages = 1;
		num = 1;
		this.soItems = 25;
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.nppId=util.getIdNhapp(userId);
		
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
	
	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			query = " select a.PK_SEQ, a.DIENGIAI, a.NGAYTAO, a.NGAYSUA, e.ten as NGUOISUA, d.ten as NGUOITAO, a.TRANGTHAI, a.ngaynhan, c.SOPO from ERP_NHAPKHONHAMAY a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ"+
					" inner join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ"+
					" where a.congty_fk = '" + this.congtyId + "' AND  A.NHAPHANPHOI_FK= "+this.nppId;
							//"  and b.pk_seq in  " + this.util.quyen_donvithuchien(this.userId) + " ";
		}
		else {
			query = search;
		}
		
		System.out.println(" query init :" + query);
		System.out.println("---------------------------------------");
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "trangthai asc, NGAYNHAN desc, PK_SEQ desc ", query);
		
		this.nhRs = db.get(query_init);
		
		
		/*this.nhRs = createSplittingData(50, 10, "NGAYNHAN desc, trangthai asc, nhId desc ", query);*/
		query="select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = 1";// and pk_seq in  "+ this.util.quyen_donvithuchien(this.userId);
		this.dvthRs = db.get(query);
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_NHAPKHONHAMAY ) ";
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
		ResultSet rs = db.get("select count(*) as c from ERP_NHAPKHONHAMAY");
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


	@Override
	public String getNppId() {
		
		return this.nppId;
	}

	@Override
	public String getId() {
		
		return this.id;
	}

	@Override
	public void setId(String id) {
		
		this.id = id;
	}

	@Override
	public String getDiengiai() {
		
		return this.diengiai;
	}

	@Override
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}

	@Override
	public String getNgaytao() {
		
		return this.ngaytao;
	}

	@Override
	public void setNgaytao(String ngaytao) {
		
		this.ngaytao = ngaytao;
	}

	@Override
	public String getNgaysua() {
		
		return this.ngaysua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		
		this.ngaysua = ngaysua;
	}

	@Override
	public String getNguoitao() {
		
		return this.nguoitao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		
		this.nguoitao = nguoitao;
	}

	@Override
	public String getNguoisua() {
		
		return this.nguoisua;
	}

	@Override
	public void setNguoisua(String nguoisua) {
		
		this.nguoisua = nguoisua;
	}

	@Override
	public String getNgaynhan() {
		
		return this.ngaynhan;
	}

	@Override
	public void setNgaynhan(String ngaynhan) {
		
		this.ngaynhan = ngaynhan;
	}
	
	@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}
	
}
