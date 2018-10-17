package geso.traphaco.erp.servlets.daytruyensanxuat;

import geso.traphaco.erp.beans.daytruyensanxuat.IErpDaytruyensanxuat;
import geso.traphaco.erp.beans.daytruyensanxuat.IErpDaytruyensanxuatList;
import geso.traphaco.erp.beans.daytruyensanxuat.imp.ErpDaytruyensanxuat;
import geso.traphaco.erp.beans.daytruyensanxuat.imp.ErpDaytruyensanxuatList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDaytruyensanxuatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpDaytruyensanxuatUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpDaytruyensanxuat dtsxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    dtsxBean = new ErpDaytruyensanxuat(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    dtsxBean.setCongtyId(ctyId);
	    dtsxBean.setId(id);
	    dtsxBean.setUserId(userId);
	    
	    dtsxBean.init();
        session.setAttribute("dtsxBean", dtsxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuatUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuatDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpDaytruyensanxuat dtsxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	dtsxBean = new ErpDaytruyensanxuat();
	    }else{
	    	dtsxBean = new ErpDaytruyensanxuat(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		dtsxBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		dtsxBean.setCongtyId(ctyId);

		String ma = util.antiSQLInspection(request.getParameter("madaytruyensanxuat"));
		if (ma == null)
			ma = "";
		dtsxBean.setMa(ma);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		dtsxBean.setDiengiai(diengiai);
		
		String nmId = util.antiSQLInspection(request.getParameter("nmId"));
		if (nmId == null)
			nmId = "";
		dtsxBean.setNhamayId(nmId);
		
		String tugio = util.antiSQLInspection(request.getParameter("tugio"));
		if (tugio == null)
			tugio = "0";
		dtsxBean.setTugio(tugio);

		String dengio = util.antiSQLInspection(request.getParameter("dengio"));
		if (dengio == null)
			dengio = "0";
		dtsxBean.setDengio(dengio);

		String nghitruatu = util.antiSQLInspection(request.getParameter("nghitruatu"));
		if (nghitruatu == null)
			nghitruatu = "0";
		dtsxBean.setNghitruaTu(nghitruatu);

		String nghitruaden = util.antiSQLInspection(request.getParameter("nghitruaden"));
		if (nghitruaden == null)
			nghitruaden = "0";
		dtsxBean.setNghitruaDen(nghitruaden);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		dtsxBean.setTrangthai(trangthai);
		
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(dtsxBean.createDaytruyensanxuat()))
				{
	 				dtsxBean.createRs();
	 				session.setAttribute("dtsxBean", dtsxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuatNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpDaytruyensanxuatList obj = new ErpDaytruyensanxuatList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					dtsxBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(dtsxBean.updateDaytruyensanxuat()))
				{
 					dtsxBean.createRs();
	 				session.setAttribute("dtsxBean", dtsxBean);  	
	 	    		session.setAttribute("userId", userId);
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuatUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpDaytruyensanxuatList obj = new ErpDaytruyensanxuatList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					dtsxBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			dtsxBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("dtsxBean", dtsxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuatNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpDaytruyensanxuatUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
