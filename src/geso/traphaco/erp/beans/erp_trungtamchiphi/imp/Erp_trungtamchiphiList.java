package geso.traphaco.erp.beans.erp_trungtamchiphi.imp;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphiList;

public class Erp_trungtamchiphiList extends Phan_Trang implements IErp_trungtamchiphiList
{
	private static final long serialVersionUID = -9217977556733610214L;

	// Tieu chi tim kiem
	private String userId;
	private String diengiai;
	private String trangthai;		 
	private String ctyId;
	private ResultSet ttcplist;
	private String msg;
	private boolean search = false;
	private dbutils db;
	private Utility util;
	
	String chixem;
	
	public Erp_trungtamchiphiList()
	{
		this.diengiai = "";	
		this.trangthai = "";
		this.ctyId = "";
		
		this.chixem = "0";
		
		this.db = new dbutils();
		this.util=new Utility();
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
	
	
	public ResultSet getTtcpList(){
		return this.ttcplist;
	}
	
	public void setTtcpList(ResultSet ttcplist){
		this.ttcplist = ttcplist;
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
		String query = 	"SELECT	TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI,	ISNULL(TTCP.CONGANSACH,0) AS CONGANSACH, " +
						" ISNULL(TTCP.NHANPHANBO,'') AS NHANPHANBO ,ISNULL( TTCP.CHOPHANBO,'') AS CHOPHANBO, TTCP.TRANGTHAI, " +
						"CASE  WHEN TTCP.NHANPHANBO = 1 THEN NHANPHANBO.TTCPID_CPB ELSE NULL END AS TTCPID_CPB, " +				
						"CASE  WHEN  TTCP.NHANPHANBO = 1 THEN NHANPHANBO.MA_CPB ELSE NULL END AS MA_CPB, " +				
						"CASE WHEN TTCP.NHANPHANBO = 1 THEN NHANPHANBO.DIENGIAI_CPB ELSE NULL END AS DIENGIAI_CPB, " +
						"CASE WHEN TTCP.NHANPHANBO = 1 THEN NHANPHANBO.PHANTRAM_CPB ELSE NULL END AS PHANTRAM_CPB,	" +	
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.TTCPID_NPB ELSE NULL END AS TTCPID_NPB, " +				
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.MA_NPB ELSE NULL END AS MA_NPB, " +				
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.DIENGIAI_NPB ELSE NULL END AS DIENGIAI_NPB, " +				
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.PHANTRAM_NPB ELSE NULL END AS PHANTRAM_NPB " +				
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTCP.PK_SEQ AS TTCPID_NPB, PB.TTCPCHO_FK AS TTCPID_CPB, " + 
						"	TTCP_CPB.MA AS MA_CPB, TTCP_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"	FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPNHAN_FK = TTCP.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_CPB ON TTCP_CPB.PK_SEQ = PB.TTCPCHO_FK " +			
						")NHANPHANBO ON NHANPHANBO.TTCPID_NPB = TTCP.PK_SEQ " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTCP.PK_SEQ AS TTCPID_CPB, PB.TTCPNHAN_FK AS TTCPID_NPB, " + 
						"	TTCP_NPB.MA AS MA_NPB, TTCP_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"	FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPCHO_FK = TTCP.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_NPB ON TTCP_NPB.PK_SEQ = PB.TTCPNHAN_FK " +			
						")PHANBO ON PHANBO.TTCPID_CPB = TTCP.PK_SEQ ORDER BY MA";

		System.out.println(query);
		
		this.ttcplist = this.db.get(query);
		

	}
	
	public ResultSet getNhanphanbo(String ttcpId){
		String query = 	"SELECT	TTCP.PK_SEQ AS TTCPID_NPB, PB.TTCPCHO_FK AS TTCPID_CPB, " + 
						"TTCP_CPB.MA AS MA_CPB, TTCP_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPNHAN_FK = TTCP.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_CPB ON TTCP_CPB.PK_SEQ = PB.TTCPCHO_FK " +
						"WHERE TTCP.PK_SEQ = " + ttcpId + " " +
						"UNION " +
						"SELECT PB.TTCP_FK AS TTCPID_NPB, PB.LNSTAISAN_FK AS TTCPID_CPB, " +
						"LNS.DIENGIAI AS MA_CPB, LNS_TS.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM " +
						"FROM ERP_LAPNGANSACH_TAISAN_PHANBO PB " +
						"INNER JOIN ERP_LAPNGANSACH_TAISAN LNS_TS ON LNS_TS.PK_SEQ = PB.LNSTAISAN_FK " +
						"INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_TS.LAPNGANSACH_FK " +
						"WHERE PB.TTCP_FK = " + ttcpId + "  ";
		
		return this.db.get(query);
	}
	

	public ResultSet getChophanbo(String ttcpId){
		String query = 	"SELECT	TTCP.PK_SEQ AS TTCPID_CPB, PB.TTCPNHAN_FK AS TTCPID_NPB, " +
						"TTCP_NPB.MA AS MA_NPB, TTCP_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPCHO_FK = TTCP.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_NPB ON TTCP_NPB.PK_SEQ = PB.TTCPNHAN_FK " +
						"WHERE TTCP.PK_SEQ = " + ttcpId + " ";
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
					
					html = html + "<TD align=left >" + rs.getString("TTCPID") + " - " + rs.getString("TTCPTEN") + " - " + rs.getString("DIENGIAI");
					
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
					html = html + "<A href = ../../Erp_TrungTamChiPhiUpdateSvl?userId=" + userId + "&update=" +rs.getString("TTCPID") + "><img src='../images/Edit20.png' alt='Cap nhat' width='20' height='20' longdesc='Cap nhat' border = 0></A>";
					html = html + "</TD>" ;
					html = html + "<TD>&nbsp;</TD>" ;
					html = html + "<TD>" ;
					html = html + "<A href = ../../Erp_TrungTamChiPhiSvl?userId=" +  userId + "&delete=" + rs.getString("TTCPID") + "><img src='../images/Delete20.png' alt='Xoa' width='20' height='20' longdesc='Xoa' border=0 onclick='if(!confirm('Bạn có muốn xóa trung tâm chi phí " + rs.getString("TTCPTEN") + "này không?')) return false;></A></TD>" ;
					html = html + "</TR>" ;												
					html = html + "</TABLE>" ;									
					html = html + "</TD>" ;
					html = html + "</TR>" ;
					m++;
					html = init0(html, rs.getString("TTCPID"), m, db, userId);
				}
			}
		}catch(java.sql.SQLException e){}
		return html;
	}
	

	public void getTtcpBeanList(HttpServletRequest request) {
		String query = this.getSearchQuery("");
		this.ttcplist =  this.db.get(query);
		
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
	    
		String query = 	"SELECT	TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI,	ISNULL(TTCP.CONGANSACH,0) AS CONGANSACH," + 
						" ISNULL(TTCP.NHANPHANBO,'') AS NHANPHANBO , ISNULL(TTCP.CHOPHANBO,'') AS CHOPHANBO, TTCP.TRANGTHAI, " +
						"CASE  WHEN TTCP.NHANPHANBO = 1 THEN NHANPHANBO.TTCPID_CPB ELSE NULL END AS TTCPID_CPB, " +				
						"CASE  WHEN  TTCP.NHANPHANBO = 1 THEN NHANPHANBO.MA_CPB ELSE NULL END AS MA_CPB, " +				
						"CASE WHEN TTCP.NHANPHANBO = 1 THEN NHANPHANBO.DIENGIAI_CPB ELSE NULL END AS DIENGIAI_CPB, " +
						"CASE WHEN TTCP.NHANPHANBO = 1 THEN NHANPHANBO.PHANTRAM_CPB ELSE NULL END AS PHANTRAM_CPB,	" +	
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.TTCPID_NPB ELSE NULL END AS TTCPID_NPB, " +				
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.MA_NPB ELSE NULL END AS MA_NPB, " +				
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.DIENGIAI_NPB ELSE NULL END AS DIENGIAI_NPB, " +				
						"CASE WHEN TTCP.CHOPHANBO = 1 THEN PHANBO.PHANTRAM_NPB ELSE NULL END AS PHANTRAM_NPB " +				
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTCP.PK_SEQ AS TTCPID_NPB, PB.TTCPCHO_FK AS TTCPID_CPB, " + 
						"	TTCP_CPB.MA AS MA_CPB, TTCP_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"	FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPNHAN_FK = TTCP.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_CPB ON TTCP_CPB.PK_SEQ = PB.TTCPCHO_FK " +			
						")NHANPHANBO ON NHANPHANBO.TTCPID_NPB = TTCP.PK_SEQ " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	TTCP.PK_SEQ AS TTCPID_CPB, PB.TTCPNHAN_FK AS TTCPID_NPB, " + 
						"	TTCP_NPB.MA AS MA_NPB, TTCP_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"	FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPCHO_FK = TTCP.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_NPB ON TTCP_NPB.PK_SEQ = PB.TTCPNHAN_FK " +			
						")PHANBO ON PHANBO.TTCPID_CPB = TTCP.PK_SEQ WHERE 1 = 1 ";
;
		
		if(id.length() > 0){
			
			query = query + "AND TTCP.PK_SEQ = " + id + " ";
			
		}
		
		if (this.diengiai.length()>0){
			query = query + " AND (UPPER(TTCP.MA) LIKE UPPER(N'%" + this.diengiai + "%') OR UPPER(TTCP.DIENGIAI) LIKE UPPER(N'%" + this.diengiai + "%')) ";
		}
 	

		if(trangthai.length() > 0){
			query = query + " AND TTCP.TRANGTHAI = '" + this.trangthai + "'";
		}

		query = query + " ORDER BY MA";
		
		System.out.print("Search: " + query);
		this.ttcplist = this.db.get(query);
		
		return query;
	}		
	
	public void DBClose(){
		try{
			if(this.ttcplist != null) this.ttcplist.close();
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