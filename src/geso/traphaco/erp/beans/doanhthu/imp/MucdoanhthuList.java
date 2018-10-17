package geso.traphaco.erp.beans.doanhthu.imp;

import geso.traphaco.erp.beans.doanhthu.IMucdoanhthuList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class MucdoanhthuList implements IMucdoanhthuList {
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
	
	private ResultSet Dtlist;
	private String msg;
	private boolean search = false;
	private dbutils db;
	
	String chixem;
	
	public MucdoanhthuList()
	{
		this.diengiai = "";
		this.loai = "";
		this.trangthai = "";
		this.ctyId = "";
		this.dvttId = "";
		this.chixem = "0";
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
	
	public ResultSet getDtList(){
		return this.Dtlist;
	}
	
	public void setDtList(ResultSet Dtlist){
		this.Dtlist = Dtlist;
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
	
	private void createDtList(){		
		String query = 		"" ;
		System.out.println(query);
		
		this.Dtlist =  this.db.get(query);
	}

	private void createDvttList(){		
		if(this.ctyId.length() > 0){
			//this.dvttlist =  this.db.get("SELECT PK_SEQ AS DVTTID, TEN FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = " + this.ctyId + " ");
			this.dvttlist =  this.db.get("SELECT PK_SEQ AS DVTTID, TEN FROM ERP_DONVITHUCHIEN  ");
		}
	}

	public void init(){
		String query = 	"SELECT	DOANHTHU.PK_SEQ AS DTID, DOANHTHU.TEN AS DTTEN, DOANHTHU.DIENGIAI, " + 
						"DOANHTHU.LOAI, DOANHTHU.TRANGTHAI, " +
						"CONGTY.TEN AS CTYTEN, ISNULL(DVTH.TEN, '') AS DVTHTEN " +
						"FROM ERP_DOANHTHU DOANHTHU " +
						"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = DOANHTHU.CONGTY_FK " +
						"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DOANHTHU.DONVITHUCHIEN_FK " +	
						"WHERE DOANHTHU.PK_SEQ > 0 "; // AND DOANHTHU.CONGTY_FK = " + this.ctyId + " " ;

		System.out.println(query);
		
		this.Dtlist = this.db.get(query);
		
		createDvttList();

	}
	

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		
		this.diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	 	
		this.dvttId = util.antiSQLInspection(request.getParameter("dvttId"));
		
		this.trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
 	
		//this.loai = util.antiSQLInspection(request.getParameter("loai"));
		
		getSearchQuery();
		
		createDvttList();

	}
	

	private void getSearchQuery(){
		
		String query ;
		query = 	"SELECT	DOANHTHU.PK_SEQ AS DTID, DOANHTHU.TEN AS DTTEN, DOANHTHU.DIENGIAI, " +   
					" DOANHTHU.LOAI, DOANHTHU.TRANGTHAI, " + 			
					"CONGTY.TEN AS CTYTEN, ISNULL(DVTH.TEN, '') AS DVTHTEN  " +
					"FROM ERP_DOANHTHU DOANHTHU  " +
					"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = DOANHTHU.CONGTY_FK " + 
					"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DOANHTHU.DONVITHUCHIEN_FK " +  
					"WHERE DOANHTHU.CONGTY_FK = " + this.ctyId + " ";
		System.out.println(query);
						
		if (this.diengiai.length()>0){
			query = query + " AND ( UPPER(DOANHTHU.DIENGIAI) LIKE UPPER(N'%" + this.diengiai + "%') OR UPPER(DOANHTHU.TEN) LIKE UPPER (N'%"+this.diengiai+"%') )";
		}
 	
		if (dvttId.length()>0){
			query = query + " AND DVTH.PK_SEQ = '" + this.dvttId + "'";
		}

		if(trangthai.length()>0){
			query = query + " AND DOANHTHU.TRANGTHAI = '" + this.trangthai + "'";
		}

		query = query + " ORDER BY DTID ASC ";
		
		System.out.println("......"+query);
		this.Dtlist = this.db.get(query);
	}		
	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.Dtlist != null) this.Dtlist.close();
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