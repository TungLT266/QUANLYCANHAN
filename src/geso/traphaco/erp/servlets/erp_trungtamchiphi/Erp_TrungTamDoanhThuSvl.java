package geso.traphaco.erp.servlets.erp_trungtamchiphi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthu;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthuList;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamdoanhthu;
import geso.traphaco.erp.beans.erp_trungtamchiphi.imp.Erp_trungtamdoanhthuList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_TrungTamDoanhThuSvl extends HttpServlet {
	static final long serialVersionUID = 1L;
		
	
	public Erp_TrungTamDoanhThuSvl() {
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
	    
	    IErp_trungtamdoanhthuList obj = (IErp_trungtamdoanhthuList) new Erp_trungtamdoanhthuList();
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
			System.out.println("xem chi ti:="+chixem);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThu.jsp";
	    response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    IErp_trungtamdoanhthuList obj = (IErp_trungtamdoanhthuList) new Erp_trungtamdoanhthuList();
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
	 		
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThu.jsp");
	    	
	    }
	    
	    // Create a new Business Unit
	    if (request.getParameter("action").equals("new")){

	    	IErp_trungtamdoanhthu TTDTBean = new Erp_trungtamdoanhthu();
	    	TTDTBean.setUserId(userId);
	    	TTDTBean.setCtyId(ctyId);
	    	TTDTBean.setTrangthai("1");
	    	// Save Data into session
	    	session.setAttribute("TTDTBean", TTDTBean);
	    	session.setAttribute("userId", userId);
 		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TrungTamDoanhThuNew.jsp";
	    	response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String id, IErp_trungtamdoanhthuList obj){
		dbutils db = new dbutils();
		
	    String sql = 	"SELECT ISNULL(SUM(THUCTHU.THUCTHU), 0) AS SUM " + 
	    				"FROM ERP_TRUNGTAMDOANHTHU_THUCTHU THUCTHU " +
	    				"WHERE THUCTHU.TTDT_FK = '" + id + "' ";
	    
	    System.out.println(sql);
	    ResultSet rs = db.get(sql);
	    try{
	    	rs.next();
	    	if(rs.getString("SUM").equals("0")){	     		
	    		
	    		db.update("DELETE FROM ERP_TRUNGTAMDOANHTHU_PHANBO WHERE  TTDTCHO_FK = '" + id + "' OR TTDTNHAN_FK = '" + id + "'");	    		
	    		db.update("DELETE FROM ERP_TRUNGTAMDOANHTHU_THUCTHU WHERE TTDT_FK = '" + id + "'");
	    		db.update("DELETE FROM ERP_TRUNGTAMDOANHTHU WHERE PK_SEQ = '" + id + "'");

	    		// Cap nhat lai NHANPHANBO cho dung
				 sql = 	"UPDATE ERP_TRUNGTAMDOANHTHU SET NHANPHANBO = 0 WHERE PK_SEQ IN ( " +
						"SELECT PK_SEQ " +
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"WHERE NHANPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTDTNHAN_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) )" ;
				
				 db.update(sql);
				
				 sql = 	"UPDATE ERP_TRUNGTAMDOANHTHU SET CHOPHANBO = 0 WHERE PK_SEQ IN ( " +
							"SELECT PK_SEQ " +
							"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
							"WHERE CHOPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTDTCHO_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) )" ;
				db.update(sql);
	    	}else{
	    		
	    		obj.setMsg("Không thể xóa trung tâm chi phí vì đã chứa dữ liệu");
	    	}
	    	rs.close();
	    }catch(java.sql.SQLException e){}
	    db.shutDown();		    	
	}
	    
	
}
