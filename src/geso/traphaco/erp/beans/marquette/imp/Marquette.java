package geso.traphaco.erp.beans.marquette.imp;

import geso.traphaco.erp.beans.marquette.IMarquette;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

import java.sql.ResultSet;

public class Marquette extends Phan_Trang implements IMarquette {
	private static final long serialVersionUID = -9217977546733690415L;
	String ctyId;
	String dvkdId;
	String userId;
	String id;
	String diengiai;
	String masp;
	String tensp;
	String loaispid;
	
	String chungloaiid;
	String trangthai;
	String denngay;
	String spIds;
	ResultSet mkList;
	ResultSet loaispList;
	ResultSet nhanhangspList;
	ResultSet chungloaiList;
	
	ResultSet dvkd;
	String nhId;
	String msg;
	String market;
	private String query;
//	int[] listPages;
	dbutils db;
	//int currentPages;
	
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	public Marquette (String[] param)
	{
		this.tensp = param[0];
		this.dvkdId = param[1];
		this.nhId = param[2];		 
		this.chungloaiid = param[3];
		this.trangthai = param[4];
		this.masp = param[5];
		this.loaispid=param[6];
		this.query = "";
		this.ctyId = "";
		this.market="";
		//currentPages = 1;
	//	listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		this.soItems = 30;
		currentPages = 1;
		num = 1;
		
		this.db = new dbutils();
		
	}
	public Marquette ()
	{
		this.tensp = "";
		this.dvkdId ="";
		this.nhId = "";		 
		this.chungloaiid ="";
		this.trangthai ="";
		this.masp = "";
		this.market="";
		this.query = "";
		this.ctyId = "";
		this.loaispid="";
	/*	currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		*/
		this.soItems = 30;
		currentPages = 1;
		num = 1;
		
		this.db = new dbutils();
		
	}
	public String getQuery(){
		return this.query;
	}
	
	public void setQuery(String query){
		this.query = query;
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.userId=userId;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.diengiai;
	}

	@Override
	public void setDiengiai(String diengiai) {
		// TODO Auto-generated method stub
		this.diengiai=diengiai;
	}

	@Override
	public String getMasp() {
		// TODO Auto-generated method stub
		return this.masp;
	}

	@Override
	public void setMasp(String masp) {
		// TODO Auto-generated method stub
		this.masp=masp;
	}

	@Override
	public String getTensp() {
		// TODO Auto-generated method stub
		return this.tensp;
	}

	@Override
	public void setTensp(String tensp) {
		// TODO Auto-generated method stub
		this.tensp=tensp;
	}

	@Override
	public String getLoaispId() {
		// TODO Auto-generated method stub
		return this.loaispid;
	}

	@Override
	public void setLoaispId(String loaispid) {
		// TODO Auto-generated method stub
		this.loaispid=loaispid;
	}

	@Override
	public String getNhanhangId() {
		// TODO Auto-generated method stub
		return this.nhId;
	}

	@Override
	public void setNhanhangId(String nhanhangid) {
		// TODO Auto-generated method stub
		this.nhId=nhanhangid;
	}

	@Override
	public String getChungloaiId() {
		// TODO Auto-generated method stub
		return this.chungloaiid;
	}

	@Override
	public void setChungloaiId(String chungloaiid) {
		// TODO Auto-generated method stub
		this.chungloaiid=chungloaiid;
	}

	@Override
	public String getDenngay() {
		// TODO Auto-generated method stub
		return this.denngay;
	}

	@Override
	public void setDenngay(String denngay) {
		// TODO Auto-generated method stub
		this.denngay=denngay;
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.trangthai=trangthai;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg=msg;
	}

	@Override
	public String getSpIds() {
		// TODO Auto-generated method stub
		return this.spIds;
	}

	@Override
	public void setSpIds(String spIds) {
		// TODO Auto-generated method stub
		this.spIds=spIds;
	}

	@Override
	public ResultSet getMkList() {
		// TODO Auto-generated method stub
		return this.mkList;
	}

	@Override
	public void setMkList(ResultSet mkList) {
		// TODO Auto-generated method stub
		this.mkList=mkList;
	}

	@Override
	public ResultSet getNhanhangIdRs() {
		// TODO Auto-generated method stub
		return this.nhanhangspList;
	}

	@Override
	public void setNhanhangIdRs(ResultSet nhanhangidrs) {
		// TODO Auto-generated method stub
		this.nhanhangspList=nhanhangidrs;
	}

	@Override
	public ResultSet getLoaiSpRs() {
		// TODO Auto-generated method stub
		return this.loaispList;
	}

	@Override
	public void setLoaiSpRs(ResultSet loaisprs) {
		// TODO Auto-generated method stub
		this.loaispList=loaisprs;
	}

	@Override
	public ResultSet getChungloaiIdRs() {
		// TODO Auto-generated method stub
		return this.chungloaiList;
	}

	@Override
	public void setChungloaiIdRs(ResultSet chungloaiid) {
		// TODO Auto-generated method stub
		this.chungloaiList=chungloaiid;
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		if(this.query.length()==0){
	        query = " select ma.pk_seq,ma.ma,ma.DIENGIAI,ma.TRANGTHAI,ma.DENNGAY "+
	                " from marquette ma left join erp_sanpham a on ma.sanpham_fk=a.PK_SEQ "+
	                " left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq "+
	                " left join erp_loaisanpham lsp on lsp.pk_seq = a.loaisanpham_fk  left join chungloai c on a.chungloai_fk = c.pk_seq "+  
	                " left join donvidoluong d on a.dvdl_fk = d.pk_seq  left join nhanhang e on a.nhanhang_fk = e.pk_seq ";

		}
	//	this.mkList =super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "denngay ASC", query);
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "denngay ASC", query);
		this.mkList = db.get(query_init);
		createRs();
		
	}
private ResultSet createNhRS(){
		
		ResultSet nhRS;
		if (dvkdId.length()>0){
			nhRS =  this.db.get("select pk_seq  , ten from nhanhang where trangthai='1' and dvkd_fk='" + this.dvkdId + "'");
		}else{
			nhRS =  this.db.get("select pk_seq  , ten from nhanhang where trangthai='1' ");
		}
		
		return nhRS;
		
	}

	private ResultSet createClRS() {  	
		
		String query = "select distinct a.pk_seq, a.ten from chungloai a, nhanhang_chungloai b where a.trangthai='1' and a.pk_seq = b.cl_fk ";
		
		if (this.nhId.length()>0){
			query = query + "  and b.nh_fk = '" + this.nhId + "'";
		}
		 
		return this.db.get(query);
			
	}

	private ResultSet createLspRS() {  	
		
		String query = "select distinct a.pk_seq, a.ma + ', ' + a.ten as ten from erp_loaisanpham a where a.trangthai='1' ";
		return this.db.get(query);
			
	}
	private ResultSet createDvkdRS(){
		
		ResultSet dvkdRS =  this.db.get("select pk_seq, donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1'");
		return dvkdRS;
	}

	@Override
	public void createRs() {
		// TODO Auto-generated method stub
		this.dvkd = createDvkdRS();
		this.nhanhangspList = createNhRS();
		this.chungloaiList= createClRS();
		this.loaispList= createLspRS();
		
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		try
		{				
			if(this.chungloaiList != null) this.chungloaiList.close();
			if(this.nhanhangspList != null) this.nhanhangspList.close();
			if(this.loaispList != null) this.loaispList.close();
			if(this.mkList != null) this.mkList.close();
			if(this.dvkd != null) this.dvkd.close();
		
		
		}
		catch(java.sql.SQLException e){
			
			e.printStackTrace();
		}
		finally
		{
			if(this.db!=null )
				this.db.shutDown();
		}
	}
	@Override
	public String getCtyId()
	{
		return this.ctyId;
	}
	@Override
	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}
	public ResultSet getDvkd(){
		return this.dvkd;
	}
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}
	@Override
	public String getMarket() {
		// TODO Auto-generated method stub
		return this.market;
	}
	@Override
	public void setMarket(String market) {
		// TODO Auto-generated method stub
		this.market=market;
	}
	public ResultSet getThongtinmarketList(){
		return this.mkList;
	}
	
	public void setThongtinmarketList(ResultSet mklist){
		this.mkList = mklist;
	}
	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		try {
			 
			db.getConnection().setAutoCommit(false);

			
//			String query = "delete FROM MARQUETTE WHERE PK_SEQ=" + this.id;
			String query = "update MARQUETTE set trangthai=0 WHERE PK_SEQ=" + this.id;
			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
				this.msg = "Không thể cập nhật dòng lệnh: " + query;
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return true;
		} catch (Exception er) {
			db.update("rollback");
			er.printStackTrace();
			return false;
		}
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}
	public int getSoItems() {
		return soItems;
	}
	public void setSoItems(int soItems) {
		this.soItems = soItems;
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
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
}
