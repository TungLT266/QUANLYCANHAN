package geso.traphaco.erp.servlets.nhomchiphi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhomchiphi.INhomchiphi;
import geso.traphaco.erp.beans.nhomchiphi.INhomchiphiList;
import geso.traphaco.erp.beans.nhomchiphi.imp.Nhomchiphi;
import geso.traphaco.erp.beans.nhomchiphi.imp.NhomchiphiList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_NhomchiphiSvl extends HttpServlet {
	static final long serialVersionUID = 1L;
	
	public Erp_NhomchiphiSvl() {
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
	    
	    INhomchiphiList obj = (INhomchiphiList) new NhomchiphiList();
	    obj.setCtyId(ctyId);
	    
	    if (action.equals("delete")){	   		
		 	Delete(id, obj);
	    }
	    
	    obj.init();
	    session.setAttribute("obj", obj);
	    session.setAttribute("userId", userId);
			
	    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Nhomchiphi.jsp";
	    response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	   
	    INhomchiphiList obj = (INhomchiphiList) new NhomchiphiList();
	    String userId = request.getParameter("userId");
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCtyId(ctyId);
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (request.getParameter("action").equals("search")){
	    	//obj.getNcpBeanList(request);
	    	obj.getReqParam(request);
	    	// Saving data into session
			session.setAttribute("obj", obj);
	 		session.setAttribute("userId", userId);
	 		
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_Nhomchiphi.jsp");
	    	
	    }
	    
	    // Create a new Business Unit
	    if (request.getParameter("action").equals("new")){

	    	INhomchiphi ncpBean = new Nhomchiphi();
	    	ncpBean.setCtyId(ctyId);
	    	ncpBean.setUserId(userId);
	    	ncpBean.initNew();
	    	// Save Data into session
	    	session.setAttribute("ncpBean", ncpBean);
	    	session.setAttribute("userId", userId);
 		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_NhomchiphiNew.jsp";
	    	response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String id, INhomchiphiList obj){
		dbutils db = new dbutils();
					
	    String sql = "SELECT COUNT(PK_SEQ) AS NUM FROM ERP_NHOMCHIPHI WHERE BOSS_FK ='" + id + "'";

	    ResultSet rs = db.get(sql);
	    try{
	    	rs.next();
	    	if(rs.getString("NUM").equals("0")){	 
	    		db.update("DELETE FROM ERP_NHOMCHIPHI WHERE PK_SEQ= '" + id + "'");	    		
	    	}else{
	    		obj.setMsg("Không thể xóa nhóm chi phí");
	    	}
	    	rs.close();
	    }catch(java.sql.SQLException e){}
	    db.shutDown();		    	
	}
	    
	
}
