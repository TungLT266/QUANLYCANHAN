package geso.traphaco.erp.beans.sohoadon.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.sohoadon.ISohoadonList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
public class SohoadonList implements ISohoadonList {
	private static final long serialVersionUID = -9217977556733610214L;

	// Tieu chi tim kiem
	private String userId;
	private String tuso;
	private String denso;
	private String trangthai;		 
	
	private String ctyId;
	private ResultSet ctylist;
	
	private String khoId;
	private ResultSet kholist;
	
	private String loai;
	
	private ResultSet sohoadonlist;
	private String msg;
	private boolean search = false;
	private dbutils db;
	
	public SohoadonList()
	{
		this.tuso = "";
		this.denso = "";
		this.trangthai = "";
		this.ctyId = "";
		this.khoId = "";
		this.loai = "";
		this.db = new dbutils();
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getTuso(){
		return this.tuso;
	}
	
	public void setTuso(String tuso){
		this.tuso = tuso;
	}
	
	public String getDenso(){
		return this.denso;
	}
	
	public void setDenso(String denso){
		this.denso = denso;
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
	
	public void setKhoId(String khoId){
		this.khoId = khoId;
	}

	public String getKhoId(){
		return this.khoId;
	}
	
	public String getLoai(){
		return this.loai;
	}
	
	public void setLoai(String loai){
		this.loai = loai;
	}
	
	public ResultSet getSohoadonList(){
		return this.sohoadonlist;
	}
	
	public void setSohoadonList(ResultSet sohoadonlist){
		this.sohoadonlist = sohoadonlist;
	}
	
	public ResultSet getCtyList(){
		return this.ctylist;
	}

	public ResultSet getKhoList(){
		return this.kholist;
	}

	public boolean getSearch()
	{
		return this.search;
	}

	public void setSearch(boolean search)
	{
		this.search = search;
	}

	private void createKhoList(){		
		if(this.ctyId.length() > 0){

			String query = 	
							"SELECT PK_SEQ AS KHOID, MA + ' - ' + TEN AS TEN \n " +
							"FROM ERP_KHOVUNG WHERE CONGTY_FK = " + this.ctyId + "  \n " +
							" \n ";
			this.kholist = this.db.get(query);

		}
	}

	public void init(){
		String query = 	"SELECT SHD.PK_SEQ AS SOHOADONID, SHD.TUSO, SHD.DENSO, SHD.KYHIEUHD, SHD.TRANGTHAI, \n " +
						"CASE WHEN SHD.LOAI = 1 THEN N'Hóa đơn' ELSE N'Xuất kho' END AS LOAI,  \n " +
						"KHO.TEN AS KHOVUNG, \n " +
						"NV.TEN AS NGUOITAO, SHD.NGAYTAO \n " +
						"FROM ERP_SOHOADON SHD \n " +
						"INNER JOIN ERP_KHOVUNG KHO ON KHO.PK_SEQ = SHD.KHOVUNG_FK \n " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = SHD.NGUOITAO \n " +
						"WHERE SHD.CONGTY_FK = " + this.ctyId + " \n " +
						"ORDER BY SHD.LOAI, SHD.KHOVUNG_FK \n " ;
						

		System.out.println(query);
		
		this.sohoadonlist = this.db.get(query);
		
		createKhoList();

	}
	

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		
		this.trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
 	
		this.khoId = util.antiSQLInspection(request.getParameter("khoId"));
		
		this.loai = util.antiSQLInspection(request.getParameter("loai"));
		
		this.getSearchQuery();
		
		createKhoList();

	}
	
	public boolean isNotInUse(String Id){
		String query = "";
		query = "SELECT SUM(NUM) AS NUM \n " +
				"FROM ( \n " +
				"	SELECT COUNT(*) AS NUM  \n " +
				"	FROM ERP_TONHOADON WHERE PK_SEQ = " + Id + " \n " +

				"	UNION \n " +

				"	SELECT COUNT(*) AS NUM \n " +
				"	FROM ERP_HOADON  \n " +
				"	WHERE CONVERT(FLOAT, ISNULL(SOHOADON, '0')) >= (SELECT TUSO FROM ERP_SOHOADON WHERE PK_SEQ = " + Id + " ) \n " +
				"	AND CONVERT(FLOAT, ISNULL(SOHOADON, '0')) <= (SELECT TUSO FROM ERP_SOHOADON WHERE PK_SEQ = " + Id + " ) \n " +
				"	AND NGAYGHINHAN >= '2015-07-01' \n " +

				"	UNION  \n " +
				"	SELECT COUNT(*) AS NUM \n " +
				"	FROM ERP_HOADONPHELIEU \n " +
				"	WHERE CONVERT(FLOAT, ISNULL(SOHOADON, '0')) >= (SELECT TUSO FROM ERP_SOHOADON WHERE PK_SEQ = " + Id + ") \n " +
				"	AND CONVERT(FLOAT, ISNULL(SOHOADON, '0')) <= (SELECT TUSO FROM ERP_SOHOADON WHERE PK_SEQ = " + Id + ") \n " +
				"	AND NGAYGHINHAN >= '2015-07-01' \n " +
				
				"	UNION  \n " +
				"	SELECT COUNT(*) FROM ERP_TONHOADON  \n " +
				"	WHERE TUSO = (SELECT TUSO FROM ERP_SOHOADON WHERE PK_SEQ = " + Id + ")  \n " +
				"	AND DENSO = (SELECT DENSO FROM ERP_SOHOADON WHERE PK_SEQ = " + Id + ")  \n " +

				")DATA \n " ;

		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			if(rs.getString("NUM").equals("0")){
				return true;
			}
			rs.close();
		}catch(java.sql.SQLException e){}
		return false;
	}
	
	private void getSearchQuery(){
//	    PrintWriter out = response.getWriter();
		
		String query = 	"SELECT SHD.PK_SEQ AS SOHOADONID, SHD.TUSO, SHD.DENSO, SHD.KYHIEUHD, SHD.TRANGTHAI, \n " +
						"CASE WHEN SHD.LOAI = 1 THEN N'Hóa đơn' ELSE N'Xuất kho' END AS LOAI,  \n " +
						"KHO.TEN AS KHOVUNG, \n " +
						"NV.TEN AS NGUOITAO, SHD.NGAYTAO \n " +
						"FROM ERP_SOHOADON SHD \n " +
						"INNER JOIN ERP_KHOVUNG KHO ON KHO.PK_SEQ = SHD.KHOVUNG_FK \n " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = SHD.NGUOITAO \n " +
						"WHERE SHD.CONGTY_FK = " + this.ctyId + " \n " ;
		
		System.out.println(query);
						
	    if (khoId.length()>0){
			query = query + " AND SHD.KHOVUNG_FK = " + this.khoId + " ";
		}

		if(trangthai.length()>0){
			query = query + " AND SHD.TRANGTHAI = " + this.trangthai + " ";
		}

		if(loai.length()>0){
			query = query + " AND SHD.LOAI = " + this.loai + " ";
		}
		
		query = query + " ORDER BY SHD.LOAI, SHD.KHOVUNG_FK ";
		System.out.println(query);
		this.sohoadonlist = this.db.get(query);
	}		
	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.sohoadonlist != null) this.sohoadonlist.close();
			if(this.kholist != null) this.kholist.close();
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