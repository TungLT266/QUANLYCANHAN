package geso.traphaco.erp.servlets.nhomchiphi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhomchiphi.IChiphi;
import geso.traphaco.erp.beans.nhomchiphi.IChiphiList;
import geso.traphaco.erp.beans.nhomchiphi.imp.Chiphi;
import geso.traphaco.erp.beans.nhomchiphi.imp.ChiphiList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_ChiphiSvl extends HttpServlet {
	static final long serialVersionUID = 1L;
	
	public Erp_ChiphiSvl() {
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
	    
	    IChiphiList obj = (IChiphiList) new ChiphiList();
	    obj.setCtyId(ctyId);
	    
	    String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);
	    
	    if (action.equals("delete")){	   		
		 	Delete(id, obj);
	    }
	    
	    obj.init();
	    session.setAttribute("obj", obj);
	    session.setAttribute("userId", userId);
			
	    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Chiphi.jsp";
	    response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	   
	    IChiphiList obj = (IChiphiList) new ChiphiList();
	    String userId = request.getParameter("userId");
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCtyId(ctyId);
	    
	    String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (request.getParameter("action").equals("search")){
	    	//obj.getNcpBeanList(request);
	    	obj.getReqParam(request);
	    	// Saving data into session
			session.setAttribute("obj", obj);
	 		session.setAttribute("userId", userId);
	 		
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_Chiphi.jsp");
	    	
	    }
	    
	    // Create a new Business Unit
	    if (request.getParameter("action").equals("new")){

	    	IChiphi ncpBean = new Chiphi();
	    	ncpBean.setCtyId(ctyId);
	    	ncpBean.setUserId(userId);
	    	ncpBean.initNew();
	    	// Save Data into session
	    	session.setAttribute("ncpBean", ncpBean);
	    	session.setAttribute("userId", userId);
 		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiphiNew.jsp";
	    	response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String id, IChiphiList obj){
		dbutils db = new dbutils();
		System.out.println("DELETE "+ id);
					
	    String sql = 	"SELECT COUNT(*) AS NUM \n " +
	    				"FROM ERP_NHOMCHIPHI NCP \n " +   				
	    				"WHERE NCP.PK_SEQ = " + id + " AND TRANGTHAI = 1 \n " + 

	    				"UNION ALL \n " +
	    				"SELECT COUNT(*) AS NUM \n " + 
	    				"FROM ERP_PHATSINHKETOAN WHERE KHOANMUCCHIPHI_FK =  " + id + "  \n " +

	    				"UNION ALL \n " +
	    				"SELECT COUNT(*) AS NUM FROM ERP_MUAHANG_SP WHERE CHIPHI_FK =   " + id + " ";
	    System.out.println("sql :" + sql);
	    ResultSet rs = db.get(sql);
	    try{
	    	rs.next();
	    	
	    	if(rs.getString("NUM").equals("0")){	 
	    		db.update("DELETE FROM ERP_NHOMCHIPHI WHERE PK_SEQ = '" + id + "'");	    		
	    	}else{
	    		obj.setMsg("Vui lòng kiểm tra: KM chi phí đã có phát sinh hoặc chưa ngưng hoạt động");
	    	}
	    	
	    	rs.close();
	    }catch(java.sql.SQLException e){e.printStackTrace();}
	    db.shutDown();		    	
	}
	
}
