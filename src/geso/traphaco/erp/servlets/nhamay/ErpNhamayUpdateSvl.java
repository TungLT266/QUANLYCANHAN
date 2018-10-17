package geso.traphaco.erp.servlets.nhamay;

import geso.traphaco.erp.beans.nhamay.IErpNhamay;
import geso.traphaco.erp.beans.nhamay.IErpNhamayList;
import geso.traphaco.erp.beans.nhamay.imp.ErpNhamay;
import geso.traphaco.erp.beans.nhamay.imp.ErpNhamayList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhamayUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpNhamayUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpNhamay csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new ErpNhamay(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    csxBean.setCongtyId(ctyId);
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
	    csxBean.creaters();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMayUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMayDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpNhamay csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new ErpNhamay();
	    }else{
	    	csxBean = new ErpNhamay(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		csxBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		csxBean.setCongtyId(ctyId);
		
		String ma = util.antiSQLInspection(request.getParameter("manhamay"));
		if (ma == null)
			ma = "";
		csxBean.setMa(ma);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";
		csxBean.setDiachi(diachi);
		
		String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
		if (dvthId == null)
			dvthId = "";
		csxBean.setDvthId(dvthId);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		csxBean.setTrangthai(trangthai);
		
		String nhamayid = util.antiSQLInspection(request.getParameter("nhamayid"));
		if (nhamayid == null)
			nhamayid = "0";
		csxBean.setNhamayId(nhamayid);
		
		csxBean.creaters();
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(csxBean.createNhamay()))
				{
	 				csxBean.creaters();
	 				
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMayNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpNhamayList obj = new ErpNhamayList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					csxBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMay.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.updateNhamay()))
				{
 					csxBean.creaters();
 					
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMayUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpNhamayList obj = new ErpNhamayList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					csxBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMay.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMayNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaMayUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
