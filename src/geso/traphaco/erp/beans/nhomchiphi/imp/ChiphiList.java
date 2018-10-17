package geso.traphaco.erp.beans.nhomchiphi.imp;

import geso.traphaco.erp.beans.nhomchiphi.IChiphi;
import geso.traphaco.erp.beans.nhomchiphi.IChiphiList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ChiphiList implements IChiphiList {
	private static final long serialVersionUID = -9217977556733610214L;

	// Tieu chi tim kiem
	private String userId;
	private String diengiai;
	private String loai;
	private String trangthai;		 
	
	private String ctyId;
	private ResultSet ctylist;
	
	private String dvttId;
	private ResultSet dvttlist;
	
	private ResultSet ncplist;
	private String msg;
	private boolean search = false;
	private dbutils db;
	
	String chixem;
	
	public ChiphiList()
	{
		this.diengiai = "";
		this.loai = "";
		this.trangthai = "";
		this.ctyId = "";
		this.dvttId = "";
		this.chixem = "0";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getLoai(){
		return this.loai;
	}
	
	public void setLoai(String loai){
		this.loai = loai;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	
	public String getCtyId(){
		return this.ctyId;
	}

	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public void setDvttId(String dvttId){
		this.dvttId = dvttId;
	}

	public String getDvttId(){
		return this.dvttId;
	}
	
	public ResultSet getNcpList(){
		return this.ncplist;
	}
	
	public void setNcpList(ResultSet ncplist){
		this.ncplist = ncplist;
	}
	
	public ResultSet getCtyList(){
		return this.ctylist;
	}

	public ResultSet getDvttList(){
		return this.dvttlist;
	}

	public boolean getSearch()
	{
		return this.search;
	}

	public void setSearch(boolean search)
	{
		this.search = search;
	}

	private void createCtyList(){		
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY");
	}
	
	private void createNcpList(){		
		String query = 		"" ;
		System.out.println(query);
		
		this.ncplist =  this.db.get(query);
	}

	private void createDvttList(){		
		if(this.ctyId.length() > 0){
			//this.dvttlist =  this.db.get("SELECT PK_SEQ AS DVTTID, TEN FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = " + this.ctyId + " ");
			this.dvttlist =  this.db.get("SELECT PK_SEQ AS DVTTID, TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' ");
		}
	}

	public void init(){
		String query = 	"SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPTEN, NHOMCHIPHI.DIENGIAI, " + 
						"NHOMCHIPHI.TRANGTHAI, " +
						"CONGTY.TEN AS CTYTEN, ISNULL(DVTH.TEN, '') AS DVTHTEN, ISNULL(KMCHIPHIDACBIET, 0) AS KMCHIPHIDACBIET " +
						"FROM ERP_NHOMCHIPHI NHOMCHIPHI " +
						"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK " +
						"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK " +	
						"WHERE NHOMCHIPHI.pk_seq > 0 "; // AND NHOMCHIPHI.CONGTY_FK = " + this.ctyId + " " ;

		System.out.println(query);
		
		this.ncplist = this.db.get(query);
		
		createDvttList();

	}
	

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		
		this.diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	 	
		this.dvttId = util.antiSQLInspection(request.getParameter("dvttId"));
		
		this.trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
 	
		this.loai = util.antiSQLInspection(request.getParameter("loai"));
		
		this.getSearchQuery();
		
		createDvttList();

	}
	

	private void getSearchQuery(){
//	    PrintWriter out = response.getWriter();
		String query ;
		query = 	"SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPTEN, NHOMCHIPHI.DIENGIAI,isnull(NHOMCHIPHI.LOAI,'1'), "+
					" NHOMCHIPHI.TRANGTHAI, CONGTY.TEN AS CTYTEN, ISNULL(DVTH.TEN, '') AS DVTHTEN, ISNULL(KMCHIPHIDACBIET, 0) AS KMCHIPHIDACBIET "+
					" FROM ERP_NHOMCHIPHI NHOMCHIPHI  "+
					" INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK "+
					" LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK  "+
					" WHERE NHOMCHIPHI.PK_SEQ > 0" ; // NHOMCHIPHI.CONGTY_FK = " + this.ctyId + " ";
		System.out.println(query);
						
		if (this.diengiai.length()>0){
			query = query + " AND ( UPPER(NHOMCHIPHI.DIENGIAI) LIKE UPPER(N'%" + this.diengiai + "%') OR UPPER(NHOMCHIPHI.TEN) LIKE UPPER(N'%" + this.diengiai + "%')) ";
		}
 	
		if (dvttId.length()>0){
			query = query + " AND DVTH.PK_SEQ = '" + this.dvttId + "'";
		}

		if(trangthai.length()>0){
			query = query + " AND NHOMCHIPHI.TRANGTHAI = '" + this.trangthai + "'";
		}

		if (loai.length()>0){
			query = query + " AND NHOMCHIPHI.LOAI = '" + this.loai + "'";
		}
 	
		query = query + " ORDER BY NCPID ASC ";
		System.out.println(query);
		this.ncplist = this.db.get(query);
	}		
	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.ncplist != null) this.ncplist.close();
			if(this.dvttlist != null) this.dvttlist.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}

	public void setMsg(String msg) {
		this.msg =msg;
		
	}

	
	public String getMsg() {
		return this.msg;
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}