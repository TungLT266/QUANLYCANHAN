package geso.traphaco.erp.beans.erp_trungtamchiphi.imp;

import java.io.IOException;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.*;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthuList;

public class Erp_trungtamdoanhthuList extends Phan_Trang implements IErp_trungtamdoanhthuList
{
	private static final long serialVersionUID = -9217977556733610214L;

	// Tieu chi tim kiem
	private String userId;
	private String diengiai;
	private String trangthai;		 
	private String ctyId;
	private ResultSet TTDTlist;
	private String msg;
	private boolean search = false;
	private dbutils db;
	
	String chixem;
	
	public Erp_trungtamdoanhthuList()
	{
		this.diengiai = "";	
		this.trangthai = "";
		this.ctyId = "";
		
		this.chixem = "0";
		
		this.db = new dbutils();
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	
	
	public ResultSet getTTDTList(){
		return this.TTDTlist;
	}
	
	public void setTTDTList(ResultSet TTDTlist){
		this.TTDTlist = TTDTlist;
	}
	

	public boolean getSearch()
	{
		return this.search;
	}

	public void setSearch(boolean search)
	{
		this.search = search;
	}

	
	public void init(){
		String query = 	"SELECT	TTDT.PK_SEQ AS TTDTID, TTDT.MA, TTDT.DIENGIAI,	 " +
						"TTDT.NHANPHANBO,TTDT.CHOPHANBO, TTDT.TRANGTHAI, " +
						"CASE  WHEN TTDT.NHANPHANBO = 1 THEN NHANPHANBO.TTDTID_CPB ELSE NULL END AS TTDTID_CPB, " +				
						"CASE  WHEN  TTDT.NHANPHANBO = 1 THEN NHANPHANBO.MA_CPB ELSE NULL END AS MA_CPB, " +				
						"CASE WHEN TTDT.NHANPHANBO = 1 THEN NHANPHANBO.DIENGIAI_CPB ELSE NULL END AS DIENGIAI_CPB, " +
						"CASE WHEN TTDT.NHANPHANBO = 1 THEN NHANPHANBO.PHANTRAM_CPB ELSE NULL END AS PHANTRAM_CPB,	" +	
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.TTDTID_NPB ELSE NULL END AS TTDTID_NPB, " +				
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.MA_NPB ELSE NULL END AS MA_NPB, " +				
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.DIENGIAI_NPB ELSE NULL END AS DIENGIAI_NPB, " +				
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.PHANTRAM_NPB ELSE NULL END AS PHANTRAM_NPB " +				
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTDT.PK_SEQ AS TTDTID_NPB, PB.TTDTCHO_FK AS TTDTID_CPB, " + 
						"	TTDT_CPB.MA AS MA_CPB, TTDT_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"	FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTNHAN_FK = TTDT.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_CPB ON TTDT_CPB.PK_SEQ = PB.TTDTCHO_FK " +			
						")NHANPHANBO ON NHANPHANBO.TTDTID_NPB = TTDT.PK_SEQ " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTDT.PK_SEQ AS TTDTID_CPB, PB.TTDTNHAN_FK AS TTDTID_NPB, " + 
						"	TTDT_NPB.MA AS MA_NPB, TTDT_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"	FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTCHO_FK = TTDT.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_NPB ON TTDT_NPB.PK_SEQ = PB.TTDTNHAN_FK " +			
						")PHANBO ON PHANBO.TTDTID_CPB = TTDT.PK_SEQ ORDER BY MA";

		System.out.println(query);
		
		this.TTDTlist = this.db.get(query);
		

	}
	
	public ResultSet getNhanphanbo(String TTDTId){
		String query = 	"SELECT	TTDT.PK_SEQ AS TTDTID_NPB, PB.TTDTCHO_FK AS TTDTID_CPB, " + 
						"TTDT_CPB.MA AS MA_CPB, TTDT_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTNHAN_FK = TTDT.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_CPB ON TTDT_CPB.PK_SEQ = PB.TTDTCHO_FK " +
						"WHERE TTDT.PK_SEQ = " + TTDTId + " ";
		return this.db.get(query);
	}
	

	public ResultSet getChophanbo(String TTDTId){
		String query = 	"SELECT	TTDT.PK_SEQ AS TTDTID_CPB, PB.TTDTNHAN_FK AS TTDTID_NPB, " +
						"TTDT_NPB.MA AS MA_NPB, TTDT_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTCHO_FK = TTDT.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_NPB ON TTDT_NPB.PK_SEQ = PB.TTDTNHAN_FK " +
						"WHERE TTDT.PK_SEQ = " + TTDTId + " ";
		return this.db.get(query);
	}
	
	public String getData(String userId){
		String html = "";
		int n = 0;
		return (init0(html, "" , n, this.db, userId));
	}
	
	private String init0(String html, String id, int m, dbutils db, String userId){
		
		String query = ""; 	
		query = getSearchQuery(id);
			
		System.out.println(query);
		
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
					
					if(!rs.getString("BOSSID").equals("0")){
			
						html = html + "<TD align=center >" + rs.getString("BOSSID") + "  ";
					
					}else{
						html = html + "<TD align=center >&nbsp; ";
						
					}
					
					html = html + "<TD align=left >" + rs.getString("TTDTID") + " - " + rs.getString("TTDTTEN") + " - " + rs.getString("DIENGIAI");
					
					if(rs.getString("LOAI").equals("1")){ 
						html = html + "<TD align=center ><div align= center> Nhóm trung tâm chi phí </div></TD> " ;
					}else {
						html = html + "<TD align=center><div align=center >Trung tâm chi phí</div></TD>";
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
					html = html + "<A href = ../../Erp_TrungTamDoanhThuUpdateSvl?userId=" + userId + "&update=" +rs.getString("TTDTID") + "><img src='../images/Edit20.png' alt='Cap nhat' width='20' height='20' longdesc='Cap nhat' border = 0></A>";
					html = html + "</TD>" ;
					html = html + "<TD>&nbsp;</TD>" ;
					html = html + "<TD>" ;
					html = html + "<A href = ../../Erp_TrungTamDoanhThuSvl?userId=" +  userId + "&delete=" + rs.getString("TTDTID") + "><img src='../images/Delete20.png' alt='Xoa' width='20' height='20' longdesc='Xoa' border=0 onclick='if(!confirm('Bạn có muốn xóa trung tâm chi phí " + rs.getString("TTDTTEN") + "này không?')) return false;></A></TD>" ;
					html = html + "</TR>" ;												
					html = html + "</TABLE>" ;									
					html = html + "</TD>" ;
					html = html + "</TR>" ;
					m++;
					html = init0(html, rs.getString("TTDTID"), m, db, userId);
				}
			}
		}catch(java.sql.SQLException e){}
		return html;
	}
	

	public void getTTDTBeanList(HttpServletRequest request) {
		String query = this.getSearchQuery("");
		this.TTDTlist =  this.db.get(query);
		
	}

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		
		this.diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	 	
		this.trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 
		
		this.getSearchQuery("");

	}
	
	
	
	private String getSearchQuery(String id) {
//	    PrintWriter out = response.getWriter();
	    
		String query = 	"SELECT	TTDT.PK_SEQ AS TTDTID, TTDT.MA, TTDT.DIENGIAI,	" + 
						"TTDT.NHANPHANBO,TTDT.CHOPHANBO, TTDT.TRANGTHAI, " +
						"CASE  WHEN TTDT.NHANPHANBO = 1 THEN NHANPHANBO.TTDTID_CPB ELSE NULL END AS TTDTID_CPB, " +				
						"CASE  WHEN  TTDT.NHANPHANBO = 1 THEN NHANPHANBO.MA_CPB ELSE NULL END AS MA_CPB, " +				
						"CASE WHEN TTDT.NHANPHANBO = 1 THEN NHANPHANBO.DIENGIAI_CPB ELSE NULL END AS DIENGIAI_CPB, " +
						"CASE WHEN TTDT.NHANPHANBO = 1 THEN NHANPHANBO.PHANTRAM_CPB ELSE NULL END AS PHANTRAM_CPB,	" +	
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.TTDTID_NPB ELSE NULL END AS TTDTID_NPB, " +				
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.MA_NPB ELSE NULL END AS MA_NPB, " +				
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.DIENGIAI_NPB ELSE NULL END AS DIENGIAI_NPB, " +				
						"CASE WHEN TTDT.CHOPHANBO = 1 THEN PHANBO.PHANTRAM_NPB ELSE NULL END AS PHANTRAM_NPB " +				
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTDT.PK_SEQ AS TTDTID_NPB, PB.TTDTCHO_FK AS TTDTID_CPB, " + 
						"	TTDT_CPB.MA AS MA_CPB, TTDT_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"	FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTNHAN_FK = TTDT.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_CPB ON TTDT_CPB.PK_SEQ = PB.TTDTCHO_FK " +			
						")NHANPHANBO ON NHANPHANBO.TTDTID_NPB = TTDT.PK_SEQ " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTDT.PK_SEQ AS TTDTID_CPB, PB.TTDTNHAN_FK AS TTDTID_NPB, " + 
						"	TTDT_NPB.MA AS MA_NPB, TTDT_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"	FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTCHO_FK = TTDT.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_NPB ON TTDT_NPB.PK_SEQ = PB.TTDTNHAN_FK " +			
						")PHANBO ON PHANBO.TTDTID_CPB = TTDT.PK_SEQ WHERE 1 = 1 ";
;
		
		if(id.length() > 0){
			
			query = query + "AND TTDT.PK_SEQ = " + id + " ";
			
		}
		
		if (this.diengiai.length()>0){
			query = query + " AND (UPPER(TTDT.MA) LIKE UPPER(N'%" + this.diengiai + "%') OR UPPER(TTDT.DIENGIAI) LIKE UPPER(N'%" + this.diengiai + "%')) ";
		}
 	

		if(trangthai.length() > 0){
			query = query + " AND TTDT.TRANGTHAI = '" + this.trangthai + "'";
		}

		query = query + " ORDER BY MA";
		
		System.out.print("Search: " + query);
		this.TTDTlist = this.db.get(query);
		
		return query;
	}		
	
	public void DBClose(){
		try{
			if(this.TTDTlist != null) this.TTDTlist.close();
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