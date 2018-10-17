package geso.traphaco.erp.beans.nhomchiphi.imp;

import geso.traphaco.erp.beans.nhomchiphi.INhomchiphi;
import geso.traphaco.erp.beans.nhomchiphi.INhomchiphiList;
import geso.traphaco.erp.db.sql.*;
import geso.traphaco.center.util.*

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class NhomchiphiList implements INhomchiphiList {
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
	
	public NhomchiphiList()
	{
		this.diengiai = "";
		this.loai = "";
		this.trangthai = "";
		this.ctyId = "";
		this.dvttId = "";
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
			this.dvttlist =  this.db.get("SELECT PK_SEQ AS DVTTID, TEN FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = " + this.ctyId + " ");
		}
	}

	public void init(){
		String query = 	"SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPTEN, NHOMCHIPHI.DIENGIAI, " + 
						"NHOMCHIPHI.LOAI, NHOMCHIPHI.TRANGTHAI, NHOMCHIPHI.BOSS_FK, " +
						"CASE WHEN NHOMCHIPHI.LOAI = '1' THEN (CONVERT(VARCHAR(18),NHOMCHIPHI.PK_SEQ) + '-0') " +
						"ELSE (CONVERT(VARCHAR(18), NHOMCHIPHI.BOSS_FK) + '-' + CONVERT(VARCHAR(18), NHOMCHIPHI.PK_SEQ)) " +
						"END AS THUTU, " +
						"CONGTY.TEN AS CTYTEN, DVTH.TEN AS DVTHTEN " +
						"FROM ERP_NHOMCHIPHI NHOMCHIPHI " +
						"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK " +
						"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK " +	
						"WHERE NHOMCHIPHI.CONGTY_FK = " + this.ctyId + " " +
						"ORDER BY THUTU" ;
		System.out.println(query);
		
		this.ncplist = this.db.get(query);
		
		createCtyList();
		createDvttList();

	}
	
	public String getData(String userId){
		String html = "";
		int n = 0;
		return (init0(html, "" , n, this.db, userId));
	}
	
	private String init0(String html, String id, int m, dbutils db, String userId){
		String query = getSearchQuery(id);		
		 	
			
		ResultSet rs = this.db.get(query);
						
		String lightrow = "tblightrow";
		String darkrow = "tbdarkrow";
		try{
			if(rs != null){
				while (rs.next()){
					if (m % 2 != 0) {						
						html = html + "<TR class= '" + lightrow + "' >" ;
					}else{ 
						html = html + "<TR class= '" + darkrow + "' > " ;
					} 							
			
					html = html + "<TD align=center >" + rs.getString("BOSSID") + "  ";
					
					
					html = html + "<TD align=left >" + rs.getString("NCPID") + " - " + rs.getString("NCPTEN") + " - " + rs.getString("DIENGIAI");
					
					if(rs.getString("LOAI").equals("1")){ 
						html = html + "<TD align=center ><div align= center> Nhóm chi phí </div></TD> " ;
					}else {
						html = html + "<TD align=center><div align=center >Chi phí</div></TD>";
					} 
				
					if(rs.getString("TRANGTHAI").equals("1")) {
						html = html + "<TD align=center >Hoạt động</TD>" ;
					}else {
						html = html + "<TD align=center>Ngưng hoạt động</TD>";
					} 
					
					html = html + "<TD align=center>" +  rs.getString("DVTHTEN") + "</TD>";
					html = html + "<TD align=center>" ;
					html = html + "<TABLE>" ;
					html = html + "<TR><TD>";
					html = html + "<A href = ../../Erp_NhomchiphiUpdateSvl?userId=" + userId + "&update=" +rs.getString("NCPID") + "><img src='../images/Edit20.png' alt='Cap nhat' width='20' height='20' longdesc='Cap nhat' border = 0></A>";
					html = html + "</TD>" ;
					html = html + "<TD>&nbsp;</TD>" ;
					html = html + "<TD>" ;
					html = html + "<A href = ../../Erp_NhomchiphiSvl?userId=" +  userId + "&delete=" + rs.getString("NCPID") + "><img src='../images/Delete20.png' alt='Xoa' width='20' height='20' longdesc='Xoa' border=0 onclick= 'return confirm('Bạn có muốn xóa nhóm chi phí " + rs.getString("NCPTEN") + " này không?')' ></A></TD>" ;
					html = html + "</TR>" ;												
					html = html + "</TABLE>" ;									
					html = html + "</TD>" ;
					html = html + "</TR>" ;
					m++;
					html = init0(html, rs.getString("NCPID"), m, db, userId);
				}
			}
		}catch(java.sql.SQLException e){}
		return html;
	}

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		
		this.diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	 	
//		this.ctyId = util.antiSQLInspection(request.getParameter("ctyId"));
		
		this.dvttId = util.antiSQLInspection(request.getParameter("dvttId"));
		
		this.trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
 	
		this.loai = util.antiSQLInspection(request.getParameter("loai"));
		createCtyList();
		createDvttList();

	}
	

	private String getSearchQuery(String id){
//	    PrintWriter out = response.getWriter();
		String query ;
		if(id.length() == 0){
				query = 	"SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPTEN, NHOMCHIPHI.DIENGIAI, " +   
							"NHOMCHIPHI.LOAI, NHOMCHIPHI.TRANGTHAI, ISNULL(NHOMCHIPHI.BOSS_FK, 0) AS BOSSID, " + 			
							"CONGTY.TEN AS CTYTEN, DVTH.TEN AS DVTHTEN  " +
							"FROM ERP_NHOMCHIPHI NHOMCHIPHI  " +
							"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK " +  
							"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK " +  
							"WHERE BOSS_FK IS NULL  AND NHOMCHIPHI.CONGTY_FK = " + this.ctyId + " ";
				System.out.println(query);
						
		}else
				query = 	"SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPTEN, NHOMCHIPHI.DIENGIAI, " + 
							"NHOMCHIPHI.LOAI, NHOMCHIPHI.TRANGTHAI, NHOMCHIPHI.BOSS_FK AS BOSSID, " +
							"CONGTY.TEN AS CTYTEN, DVTH.TEN AS DVTHTEN " +
							"FROM ERP_NHOMCHIPHI NHOMCHIPHI " +
							"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK " +
							"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK " +
							"WHERE NHOMCHIPHI.BOSS_FK = " + id + " " ; 
				System.out.println(query);			
	    
		
 	if (this.diengiai.length()>0){
			query = query + " AND UPPER(NHOMCHIPHI.DIENGIAI) LIKE UPPER(N'%" + this.diengiai + "%')";
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
		return query;
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
}