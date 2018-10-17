package geso.traphaco.erp.servlets.mucchiphi;

import geso.traphaco.erp.beans.mucchiphi.IMucchiphi;
import geso.traphaco.erp.beans.mucchiphi.imp.Mucchiphi;
import geso.traphaco.erp.beans.mucchiphi.IMucchiphiList;
import geso.traphaco.erp.beans.mucchiphi.imp.MucchiphiList;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_MucchiphiUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_MucchiphiUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    System.out.println("UserId: " + userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    
	    String Id = util.getId(querystring).split(";")[0];
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    System.out.println("Id: " + Id);
		
   	    IMucchiphi mcpBean = new Mucchiphi();
   	    
   	    mcpBean.setUserId(userId);
   	    mcpBean.setId(Id);
   	    mcpBean.setCtyId(ctyId);
   	    mcpBean.init();
   	    
		// Data is saved into session
		session.setAttribute("mcpBean", mcpBean);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucchiphiUpdate.jsp";
		if(querystring.contains("display"))
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucchiphiDisplay.jsp";
   		response.sendRedirect(nextJSP);


	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String action = request.getParameter("action");
		String ctyId = (String)session.getAttribute("congtyId");
		
		IMucchiphi mcpBean = new Mucchiphi();	
		mcpBean.setCtyId(ctyId);
		
    	String Id = util.antiSQLInspection(request.getParameter("Id"));
    	mcpBean.setId(Id);

		String sotientu = util.antiSQLInspection(request.getParameter("sotientu"));
		mcpBean.setSotientu(sotientu);

		String sotienden = util.antiSQLInspection(request.getParameter("sotienden"));
		mcpBean.setSotienden(sotienden);
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		mcpBean.setUserId(userId);
		
    	String trangthai;
    	if(request.getParameter("trangthai")!= null)
			trangthai = "1";
		else
			trangthai = "0";
    	mcpBean.setTrangthai(trangthai);
		
    	String[] dvthIds = request.getParameterValues("dvthIds");
    	mcpBean.setDvthIds(dvthIds);

		if (action.equals("search")){
			mcpBean.init();
			session.setAttribute("userId", userId);
			session.setAttribute("mcpBean", mcpBean);   		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucchiphiUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", userId);
		
			//if there is any error when saving Business Unit
			if(Id.length() == 0){
				if (!mcpBean.saveNewMucchiphi()){
					session.setAttribute("mcpBean", mcpBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucchiphiNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else{				
					IMucchiphiList obj = new MucchiphiList();
					obj.setUserId(userId);
					obj.setCtyId(ctyId);
					   obj.setCtyId(ctyId);
					    obj.createDvthList();
					    obj.createMcpList();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Mucchiphi.jsp";
					response.sendRedirect(nextJSP);
				}
				
			}else{
				if (!mcpBean.UpdateMucchiphi()){
					session.setAttribute("mcpBean", mcpBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MucchiphiUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{				
					IMucchiphiList obj = new MucchiphiList();
					obj.setUserId(userId);
					obj.setCtyId(ctyId);
				
					    obj.createDvthList();
					    obj.createMcpList();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Mucchiphi.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			
		}
		
	}   	  	 

}
