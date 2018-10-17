package geso.erp.servlets.lapkehoach;

import geso.erp.beans.lapkehoach.IErpDaychuyensanxuat;
import geso.erp.beans.lapkehoach.IErpDaychuyensanxuatList;
import geso.erp.beans.lapkehoach.imp.ErpDaychuyensanxuat;
import geso.erp.beans.lapkehoach.imp.ErpDaychuyensanxuatList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDaychuyensanxuatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpDaychuyensanxuatUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpDaychuyensanxuat dcsxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    dcsxBean = new ErpDaychuyensanxuat(id);
	    dcsxBean.setCtyId(ctyId);
	    dcsxBean.setId(id);
	    dcsxBean.setUserId(userId);
	    
	    dcsxBean.init();
        session.setAttribute("dcsxBean", dcsxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuatUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuatDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpDaychuyensanxuat dcsxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    String ctyId = (String)session.getAttribute("congtyId");
	    if(id == null){  	
	    	dcsxBean = new ErpDaychuyensanxuat();
	    }else{
	    	dcsxBean = new ErpDaychuyensanxuat(id);
	    }
	   
	    dcsxBean.setCtyId(ctyId);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		dcsxBean.setUserId(userId);	       
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		dcsxBean.setMa(ma);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		dcsxBean.setDiengiai(diengiai);
		
		String spIds = util.antiSQLInspection(request.getParameter("spIds"));
		if (spIds == null)
			spIds = "";
		dcsxBean.setSpIds(spIds);
		
		String soluongchuan = util.antiSQLInspection(request.getParameter("soluongchuan"));
		if (soluongchuan == null)
			soluongchuan = "";
		dcsxBean.setSoluongchuan(soluongchuan.replace(",", ""));
		
		String[] cumIds = request.getParameterValues("cumIds");
		String cumId="";
		if(cumIds!=null)
		{
			for(int i=0;i<cumIds.length;i++)
			{
				cumId+=cumIds[i]+",";
			}
			if(cumId.length()>0)
				cumId=cumId.substring(0,cumId.length()-1);
		}
		dcsxBean.setCumsxIds(cumId);
		
		String thoigian = util.antiSQLInspection(request.getParameter("thoigian"));
		if (thoigian == null)
			thoigian = "";
		dcsxBean.setThoigian(thoigian);
		

 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(dcsxBean.createDaychuyensanxuat()))
				{
	 				dcsxBean.createRs();
	 				session.setAttribute("dcsxBean", dcsxBean);  	
	 	    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuatNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpDaychuyensanxuatList obj = new ErpDaychuyensanxuatList();
					obj.setCtyId(ctyId);
					obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(dcsxBean.updateDaychuyensanxuat()))
				{
 					dcsxBean.createRs();
	 				session.setAttribute("dcsxBean", dcsxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuatUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpDaychuyensanxuatList obj = new ErpDaychuyensanxuatList();
					obj.setCtyId(ctyId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			dcsxBean.createRs();
			
			session.setAttribute("userId", userId);
			session.setAttribute("dcsxBean", dcsxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuatNew.jsp";
			
			if(id != null)
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuatUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
