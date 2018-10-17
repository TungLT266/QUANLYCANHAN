package geso.traphaco.erp.beans.danhgianhacc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.danhgianhacc.IErp_DanhgianccList;

import java.sql.ResultSet;

public class Erp_DanhgianccList extends Phan_Trang implements IErp_DanhgianccList{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String dvthId;
	ResultSet dvthRs;
	String spId;
	ResultSet spRs;
	String nccId;
	ResultSet nccRs;
	String trangthai;
	String msg;
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	ResultSet dgncclist;
	
	dbutils db;
	Utility util;

	public Erp_DanhgianccList()
	{
		this.congtyId = "";
		this.userId = "";
    	this.trangthai = "";
    	this.dvthId = "";
    	this.spId = "";
    	this.nccId = "";
		this.msg = "";
		this.soItems = 25;
		this.util=new Utility();
		this.db = new dbutils();
	}
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.PK_SEQ as id, a.NCC_FK, e.TEN as nccten, d.diengiai as spten, " +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, b.TEN as nguoitao, c.TEN as nguoisua, a.SP_FK, a.dvth_fk ";
			query = query + "from ERP_DANHGIANCC a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
					" inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
			query = query + "left join erp_congcudungcu d on a.SP_FK = d.PK_SEQ " +
					" inner join ERP_NHACUNGCAP e on a.NCC_FK = e.PK_SEQ " +
					" where a.CONGTY_FK = "+ this.congtyId +" ";			
			System.out.println("Query la: " + query + "\n");
			
			if(this.nccId.length() > 0)
				query += " and a.NCC_FK = '" + this.nccId + "'";
			
			if(spId.length() > 0)
				query += " and a.SP_FK = '" + spId + "'";

			System.out.println("Query la: " + query + "\n");
		}
		
	

	
		else
		{
			query = search;
		}
		
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "id desc", query);
		System.out.println("dgncclist " + query_init);
		this.dgncclist = db.get(query_init);
		
		createRs();
	}
	public void createRs() {
		// TODO Auto-generated method stub
		String query = "select PK_SEQ, ma + '-' + diengiai as ten from erp_congcudungcu where trangthai = 1";
		System.out.println("sp "+query);
		this.spRs = db.get(query);
		
		query = "select pk_seq, ten from erp_nhacungcap where trangthai = 1 and duyet = '1' and congty_fk = '"+this.congtyId+"'";
		this.nccRs = db.get(query);	
		
	}
	@Override
	public String getDvthId() {
		// TODO Auto-generated method stub
		return this.dvthId;
	}
	@Override
	public void setDvthId(String dvthId) {
		// TODO Auto-generated method stub
		this.dvthId = dvthId;
	}
	public ResultSet getDvthRs() {
		// TODO Auto-generated method stub
		return this.dvthRs;
	}

	public void setDvthRs(ResultSet dvthRs) {
		// TODO Auto-generated method stub
		this.dvthRs = dvthRs;
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
		ResultSet rs = db.get("select count(*) as c from ERP_TIMNCC");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	@Override
	public String getCongtyId() {
		// TODO Auto-generated method stub
		return this.congtyId;
	}
	@Override
	public void setCongtyId(String congtyId) {
		// TODO Auto-generated method stub
		this.congtyId = congtyId;
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}
	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.userId = userId;
	}
	@Override
	public ResultSet getDgNccList() {
		// TODO Auto-generated method stub
		return this.dgncclist;
	}
	@Override
	public void setDgNccList(ResultSet dgncclist) {
		// TODO Auto-generated method stub
		this.dgncclist = dgncclist;
	}
	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.trangthai = trangthai;
	}

	@Override
	public void setMsg(String Msg) {
		// TODO Auto-generated method stub
		this.msg = Msg;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.msg;
	}
	@Override
	public String getNccId() {
		// TODO Auto-generated method stub
		return this.nccId;
	}
	@Override
	public void setNccId(String NccId) {
		// TODO Auto-generated method stub
		this.nccId = NccId;
	}
	@Override
	public ResultSet getNccRs() {
		// TODO Auto-generated method stub
		return this.nccRs;
	}
	@Override
	public void setNccRs(ResultSet nccRs) {
		// TODO Auto-generated method stub
		this.nccRs = nccRs;
	}
	@Override
	public String getSpId() {
		// TODO Auto-generated method stub
		return this.spId;
	}
	@Override
	public void setSpId(String spId) {
		// TODO Auto-generated method stub
		this.spId = spId;
	}
	@Override
	public ResultSet getSpRs() {
		// TODO Auto-generated method stub
		return this.spRs;
	}
	@Override
	public void setSpRs(ResultSet spRs) {
		// TODO Auto-generated method stub
		this.spRs = spRs;
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
