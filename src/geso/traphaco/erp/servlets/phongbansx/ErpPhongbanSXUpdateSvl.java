package geso.traphaco.erp.servlets.phongbansx;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phongbansx.IErpPhongbanSX;
import geso.traphaco.erp.beans.phongbansx.IErpPhongbanSXList;
import geso.traphaco.erp.beans.phongbansx.imp.ErpPhongbanSX;
import geso.traphaco.erp.beans.phongbansx.imp.ErpPhongbanSXList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhongbanSXUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpPhongbanSXUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpPhongbanSX csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new ErpPhongbanSX(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    csxBean.setCongtyId(ctyId);
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
	    csxBean.creaters();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSXNew.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSXDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpPhongbanSX csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new ErpPhongbanSX();
	    }else{
	    	csxBean = new ErpPhongbanSX(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		csxBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		csxBean.setCongtyId(ctyId);
		
		String ischangema = util.antiSQLInspection(request.getParameter("ischangema"));
		if (ischangema == null)
			ischangema = "";
		csxBean.setIsChangeMa(ischangema);
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		csxBean.setMa(ma);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		csxBean.setTrangthai(trangthai);
		
		String[] spDiengiai = request.getParameterValues("spDiengiai");
		csxBean.setSpDiengiai(spDiengiai);
		
		String[] spDinhmuctu = request.getParameterValues("spDinhmuctu");
		csxBean.setSpDinhmuctu(spDinhmuctu);
		
		String[] spDinhmucden = request.getParameterValues("spDinhmucden");
		csxBean.setSpDinhmucden(spDinhmucden);
		
		String[] spDonvi = request.getParameterValues("spDonvi");
		csxBean.setSpDonvi(spDonvi);
		
		csxBean.creaters();
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null || id.trim().equals(""))
 			{
	 			if (!(csxBean.create()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSXNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpPhongbanSXList obj = new ErpPhongbanSXList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					csxBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSX.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.update()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSXNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpPhongbanSXList obj = new ErpPhongbanSXList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					csxBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSX.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSXNew.jsp";
			
			response.sendRedirect(nextJSP);
		}		
	}
}
