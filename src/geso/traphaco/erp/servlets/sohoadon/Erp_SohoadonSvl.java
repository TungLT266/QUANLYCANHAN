package geso.traphaco.erp.servlets.sohoadon;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.sohoadon.ISohoadon;
import geso.traphaco.erp.beans.sohoadon.ISohoadonList;
import geso.traphaco.erp.beans.sohoadon.imp.Sohoadon;
import geso.traphaco.erp.beans.sohoadon.imp.SohoadonList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_SohoadonSvl extends HttpServlet {
	static final long serialVersionUID = 1L;
	
	public Erp_SohoadonSvl() {
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
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    	    
	    String id = util.getId(querystring).split(";")[0];
	  
	    out.println(id);
	    
	    ISohoadonList obj = (ISohoadonList) new SohoadonList();
	    obj.setCtyId(ctyId);
	    
	    if (action.equals("delete")){	   		
		 	Delete(id, obj);
	    }
	    
	    obj.init();
	    session.setAttribute("obj", obj);
	    session.setAttribute("userId", userId);
			
	    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Sohoadon.jsp";
	    response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	   
	    ISohoadonList obj = (ISohoadonList) new SohoadonList();
	    String userId = request.getParameter("userId");
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCtyId(ctyId);
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (request.getParameter("action").equals("search")){
	    	obj.getReqParam(request);
			session.setAttribute("obj", obj);
	 		session.setAttribute("userId", userId);
	 		
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_Sohoadon.jsp");
	    	
	    }
	    
	    // Create a new Business Unit
	    if (request.getParameter("action").equals("new")){

	    	ISohoadon shdBean = new Sohoadon();
	    	shdBean.setCtyId(ctyId);
	    	shdBean.setUserId(userId);
	    	shdBean.initNew();
	    	// Save Data into session
	    	session.setAttribute("shdBean", shdBean);
	    	session.setAttribute("userId", userId);
 		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_SohoadonNew.jsp";
	    	response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String id, ISohoadonList obj){
		dbutils db = new dbutils();
					
	    String sql = 	"SELECT DENSO, TUSO, LOAI " +
						"FROM ERP_SOHOADON  \n " +
						"WHERE PK_SEQ = " + id + "";
	    
	    ResultSet rs = db.get(sql);
	    try{
	    	rs.next();
	    	double tuso = rs.getDouble("TUSO");
	    	double denso = rs.getDouble("DENSO");
	    	String loai = rs.getString("LOAI");
	    	rs.close();
	    	
	    	if(loai.equals("1")){
	    		sql = 	"SELECT SUM(NUM) FROM( \n " +
	    				"SELECT COUNT(*) AS NUM \n " +
	    				"FROM ERP_HOADON \n " +
	    				"WHERE SOHOADON >= '" + tuso + "' AND SOHOADON <= '" + denso + "' \n " +
	    				"UNION  \n " +
	    				"SELECT COUNT(*) AS NUM \n " +
	    				"FROM ERP_HOADONPHELIEU \n " +
	    				"WHERE SOHOADON >= '" + tuso + "' AND SOHOADON <= '" + denso + "' \n " +
	    				")DATA \n " ;

	    	}else{
	    		sql = 	"SELECT COUNT(*) AS NUM \n " +
	    				"FROM ERP_CHUYENKHO \n " +
	    				"WHERE SOCHUNGTU >= '" + tuso + "' AND SOCHUNGTU <= '" + denso + "' \n " ;

	    	}
	    	
	    	rs = db.get(sql);
	    	rs.next();
	    	
	    	if(rs.getString("NUM").equals("0")){	 
	    		db.update("DELETE FROM ERP_SOHOADON WHERE PK_SEQ = '" + id + "'");	    		
	    	}else{
	    		obj.setMsg("Không thể xóa Số hóa đơn này");
	    	}
	    	
	    	rs.close();
	    }catch(java.sql.SQLException e){}
	    db.shutDown();		    	
	}
	
}
