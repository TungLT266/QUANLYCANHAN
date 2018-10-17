package geso.traphaco.erp.beans.mucchiphi.imp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.mucchiphi.IMucchiphiList;

public class MucchiphiList implements IMucchiphiList {
	private static final long serialVersionUID = -9217977546733610415L;
	
	String sotientu;
	String sotienden;
	
	String ctyId;
	ResultSet ctylist;
	
	ResultSet mcplist;
	String mcpId;

	ResultSet dvthlist;
	String dvthId;
	
	String tungay;
	String denngay;
	String trangthai;
	String Msg;
	dbutils db;
	private Utility util;
	
	String userId;
	String chixem;
	
	public MucchiphiList()
	{		
		
		this.ctyId = "";
		this.mcpId = "";
		this.sotientu = "";
		this.sotienden = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";	
		this.Msg ="";
		this.db = new dbutils();
		this.util=new Utility();
		this.chixem = "0";

	}

	public String getSotientu()
	{
		return this.sotientu;
	}

	public void setSotientu(String sotientu)
	{
		this.sotientu = sotientu;
	}
	
	public String getSotienden()
	{
		return this.sotienden;
	}

	public void setSotienden(String sotienden)
	{
		this.sotienden = sotienden;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getDvthId()
	{
		return this.dvthId;
	}

	public void setDvthId(String dvthId)
	{
		this.dvthId = dvthId;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	
	public void setCtyList(ResultSet ctylist)
	{
		this.ctylist = ctylist;
	}

	public ResultSet getCtyList()
	{
		return this.ctylist;
	}
		

	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthlist = dvthlist;
	}

	public ResultSet getDvthList()
	{
		return this.dvthlist;
	}

	public void setMcpList(ResultSet mcplist)
	{
		this.mcplist = mcplist;
	}

	public ResultSet getMcpList()
	{
		return this.mcplist;
	}

	private void createCtyList(){		
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY");
	}
	
	public void createDvthList(){
		if(this.ctyId.length() > 0){
			String sql="SELECT PK_SEQ AS DVTHID, MA + ' - ' + TEN AS DVTH FROM ERP_DONVITHUCHIEN "; // WHERE CONGTY_FK = '" + this.ctyId + "'";
			this.dvthlist = this.db.get(sql);
			//System.out.println(sql);
		}
	}
	
	public void createMcpList(){
		String sql= " SELECT KCP.PK_SEQ AS MCPID, KCP.TRANGTHAI, " +
					" KCP.SOTIENTU, KCP.SOTIENDEN, " +  
					" CTY.TEN AS CTY, CTY.PK_SEQ AS CTYID, " +
					" DVTH.TEN AS DVTH " +
					" FROM ERP_KHOANGCHIPHI KCP " +
					" INNER JOIN ERP_KHOANGCHIPHI_DONVITHUCHIEN KCP_DVTH  ON KCP_DVTH.KHOANGCHIPHI_FK = KCP.PK_SEQ AND KCP_DVTH.CHON = '1' " +
					" INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = KCP_DVTH.DONVITHUCHIEN_FK " +
					" INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = DVTH.CONGTY_FK " +
					" WHERE KCP.PK_SEQ > 0 " + // AND KCP.CONGTY_FK = " + this.ctyId + " " + //and dvth.pk_seq in  "+ this.util.quyen_donvithuchien(this.userId) +
					" ORDER BY KCP.SOTIENTU, KCP.SOTIENDEN " ;
		this.mcplist = this.db.get(sql);
		
		System.out.println("Lay muc Chi PhI: " + sql);
	}
	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.dvthlist != null) this.dvthlist.close();
			if(this.mcplist != null) this.mcplist.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}

	public void setMsg(String Msg) {
		this.Msg =Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}

	public void getSearchQuery(HttpServletRequest request)throws ServletException, IOException{
	    request.setCharacterEncoding("UTF-8");

		Utility util = new Utility();
		
		String sotientu = util.antiSQLInspection(request.getParameter("sotientu"));
    	if (sotientu == null) sotientu = "";
    	this.sotientu = sotientu;
    	
		String sotienden = util.antiSQLInspection(request.getParameter("sotienden"));
    	if (sotienden == null) sotienden = "";
    	this.sotienden = sotienden;

    	String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
    	if (dvthId == null) dvthId = "";
    	this.dvthId = dvthId;

    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null) trangthai = "";    	    	
    	this.trangthai = trangthai;
    	
    	String query = 	" SELECT KCP.PK_SEQ AS MCPID, KCP.TRANGTHAI, " +
						" KCP.SOTIENTU, KCP.SOTIENDEN, " +  
						" CTY.TEN AS CTY, CTY.PK_SEQ AS CTYID, " +
						" DVTH.TEN AS DVTH " +
						" FROM ERP_KHOANGCHIPHI KCP " +
						" INNER JOIN ERP_KHOANGCHIPHI_DONVITHUCHIEN KCP_DVTH  ON KCP_DVTH.KHOANGCHIPHI_FK = KCP.PK_SEQ AND KCP_DVTH.CHON = '1' " +
						" INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = KCP_DVTH.DONVITHUCHIEN_FK " +
						" INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = DVTH.CONGTY_FK " +
						" WHERE KCP.CONGTY_FK = " + this.ctyId + " and dvth.pk_seq in  "+ this.util.quyen_donvithuchien(this.userId);
    	
    	if (this.sotientu.length()>0){
			query = query + " AND KCP.SOTIENTU >= '" + this.sotientu.replaceAll(",", "") + "' ";
    	}
    	
    	if (this.sotienden.length()>0){
			query = query + " AND KCP.SOTIENDEN <= '" + this.sotienden.replaceAll(",", "") + "' ";
    	}

    	if (this.dvthId.length()>0){
			query = query + " AND DVTH.PK_SEQ = '" + this.dvthId + "'";
    	}

    	if(this.trangthai.length() > 0){
    		query = query + " AND KCP.TRANGTHAI = '" + this.trangthai + "'";
    	}
    	
    	query = query + " ORDER BY KCP.SOTIENTU, KCP.SOTIENDEN ";
    	
    	System.out.print("Lay muc CP search: " + query);
		this.mcplist = this.db.get(query);
		this.createDvthList();
	}		
	
	private void Delete(String id){
		dbutils db = new dbutils();
					
	    String sql = "SELECT COUNT(PK_SEQ) AS NUM FROM ERP_DUYETMUAHANG WHERE MUAHANG_FK ='" + id + "'";

	    ResultSet rs = db.get(sql);
	    try{
	    	rs.next();
	    	
	    	if(rs.getString("NUM").equals("0")){

	    	}else{
	    	}
	    	rs.close();
	    }catch(java.sql.SQLException e){}
	    db.shutDown();		    	
	}

	@Override
	public void setUserId(String Userid) {
		this.userId=Userid;
	}

	@Override
	public boolean delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = " DELETE ERP_CHINHSACHDUYET_CHIPHI where KHOANGCHIPHI_FK = " + id;
			if(!db.update(query)) {
				this.Msg = "Không thể xóa mức chi phí (" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE ERP_KHOANGCHIPHI_DONVITHUCHIEN WHERE KHOANGCHIPHI_FK = " + id;
			if(!db.update(query)) {
				this.Msg = "Không thể xóa mức chi phí (" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE ERP_KHOANGCHIPHI WHERE PK_SEQ = " + id;
			if(!db.update(query)) {
				this.Msg = "Không thể xóa mức chi phí (" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch(Exception e) {
			try { db.getConnection().rollback(); } catch (SQLException e1) { }
			this.Msg = "Không thể xóa mức chi phí (" + e.getMessage() + ")";
			return false;
		}
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
