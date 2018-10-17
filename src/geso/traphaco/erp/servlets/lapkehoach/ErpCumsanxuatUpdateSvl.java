package geso.erp.servlets.lapkehoach;

import geso.erp.beans.lapkehoach.IErpCumsanxuat;
import geso.erp.beans.lapkehoach.IErpCumsanxuatList;
import geso.erp.beans.lapkehoach.imp.ErpCumsanxuat;
import geso.erp.beans.lapkehoach.imp.ErpCumsanxuatList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCumsanxuatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpCumsanxuatUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpCumsanxuat csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new ErpCumsanxuat(id);
	    csxBean.setCtyId(ctyId);
	    
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuatUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuatDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpCumsanxuat csxBean;		
		
		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new ErpCumsanxuat();
	    }else{
	    	csxBean = new ErpCumsanxuat(id);
	    }
	    	    
	    csxBean.setCtyId(ctyId);
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		csxBean.setUserId(userId);	       
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		csxBean.setMa(ma);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String sonhancong = util.antiSQLInspection(request.getParameter("sonhancong"));
		if (sonhancong == null)
			sonhancong = "";
		csxBean.setSonhancong(sonhancong);
		
		String nmIds = util.antiSQLInspection(request.getParameter("nmIds"));
		if (nmIds == null)
			nmIds = "";
		csxBean.setNhamayIds(nmIds);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		csxBean.setTrangthai(trangthai);
		
		String[] tbIds = request.getParameterValues("tbIds");
		if (tbIds != null)
		{
			String str = "";
			for(int i = 0; i < tbIds.length; i++ )
			{
				str += tbIds[i] + ",";
			}
			if(str.trim().length() > 0)
			{
				str = str.substring(0, str.length() - 1);
				csxBean.setTbIds(str);
			}
		}
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(csxBean.createCumsanxuat()))
				{
	 				csxBean.createRs();
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuatNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpCumsanxuatList obj = new ErpCumsanxuatList();
					obj.setCtyId(ctyId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.updateCumsanxuat()))
				{
 					csxBean.createRs();
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuatUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpCumsanxuatList obj = new ErpCumsanxuatList();
					obj.setCtyId(ctyId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			csxBean.createRs();
			
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuatNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpCumSanXuatUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
