package geso.traphaco.erp.servlets.erp_trungtamchiphi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphi;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphiList;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamchiphi;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamchiphiList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_TrungTamChiPhiSvl extends HttpServlet {
	static final long serialVersionUID = 1L;
		
	
	public Erp_TrungTamChiPhiSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    Utility util = new Utility();    
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	  
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    	    
	    String id = util.getId(querystring).split(";")[0];
	      
	    String ctyId = (String)session.getAttribute("congtyId"); 
	    
	    IErp_trungtamchiphiList obj = (IErp_trungtamchiphiList) new Erp_trungtamchiphiList();
	    obj.setUserId(userId);
	    obj.setCtyId(ctyId);
	    
	    String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);
	    
	    if (action.equals("delete")){	   		
		 	Delete(id, obj);
	    }
	  
	    obj.init();
	    session.setAttribute("obj", obj);
	    session.setAttribute("userId", userId);
			
	    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhi.jsp";
	    response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    IErp_trungtamchiphiList obj = (IErp_trungtamchiphiList) new Erp_trungtamchiphiList();
	    String userId = request.getParameter("userId");
	    obj.setUserId(userId);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (request.getParameter("action").equals("search")){
	    	obj.setUserId(userId);
	    	obj.setCtyId(ctyId);
	    	obj.getReqParam(request);
	    	// Saving data into session
			session.setAttribute("obj", obj);
	 		session.setAttribute("userId", userId);
	 		
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhi.jsp");
	    	
	    }
	    
	    // Create a new Business Unit
	    if (request.getParameter("action").equals("new")){

	    	IErp_trungtamchiphi ttcpBean = new Erp_trungtamchiphi();
	    	ttcpBean.setUserId(userId);
	    	ttcpBean.setCtyId(ctyId);
	    	ttcpBean.setTrangthai("1");
	    	// Save Data into session
	    	session.setAttribute("ttcpBean", ttcpBean);
	    	session.setAttribute("userId", userId);
 		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamChiPhiNew.jsp";
	    	response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String id, IErp_trungtamchiphiList obj){
		dbutils db = new dbutils();
		
	    String sql = 	"SELECT ISNULL(SUM(THUCCHI.THUCCHI) + SUM(NGANSACH.NGANSACH) , 0) AS SUM " + 
	    				"FROM ERP_TRUNGTAMCHIPHI_THUCCHI THUCCHI " +
	    				"LEFT JOIN ERP_TRUNGTAMCHIPHI_NGANSACH NGANSACH ON NGANSACH.TTCP_FK = THUCCHI.TTCP_FK " +
	    				"WHERE THUCCHI.TTCP_FK = '" + id + "' AND NGANSACH.TTCP_FK = '" + id + "'";
	    
	    System.out.println(sql);
	    ResultSet rs = db.get(sql);
	    try{
	    	rs.next();
	    	if(rs.getString("SUM").equals("0")){	     		
	    		
	    		db.update("DELETE FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE  TTCPCHO_FK = '" + id + "' OR TTCPNHAN_FK = '" + id + "'");	
	    		db.update("DELETE FROM ERP_TRUNGTAMCHIPHI_NGANSACH WHERE TTCP_FK = '" + id + "'");
	    		db.update("DELETE FROM ERP_TRUNGTAMCHIPHI_THUCCHI WHERE TTCP_FK = '" + id + "'");
	    		db.update("DELETE FROM ERP_TRUNGTAMCHIPHI WHERE PK_SEQ = '" + id + "'");

	    		// Cap nhat lai NHANPHANBO cho dung
				 sql = 	"UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 0 WHERE PK_SEQ IN ( " +
						"SELECT PK_SEQ " +
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"WHERE NHANPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO) )" ;
				
				 db.update(sql);
				
				 sql = 	"UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 0 WHERE PK_SEQ IN ( " +
							"SELECT PK_SEQ " +
							"FROM ERP_TRUNGTAMCHIPHI TTCP " +
							"WHERE CHOPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO) )" ;
				db.update(sql);
	    	}else{
	    		
	    		obj.setMsg("Không thể xóa trung tâm chi phí vì đã chứa dữ liệu");
	    	}
	    	rs.close();
	    }catch(java.sql.SQLException e){}
	    db.shutDown();		    	
	}
	    
	
}
