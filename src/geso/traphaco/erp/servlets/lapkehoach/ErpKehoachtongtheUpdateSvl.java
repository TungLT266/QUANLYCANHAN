package geso.erp.servlets.lapkehoach;

import geso.erp.beans.lapkehoach.IErpKehoachtongthe;
import geso.erp.beans.lapkehoach.IErpKehoachtongtheList;
import geso.erp.beans.lapkehoach.imp.ErpKehoachtongthe;
import geso.erp.beans.lapkehoach.imp.ErpKehoachtongtheList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpKehoachtongtheUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpKehoachtongtheUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKehoachtongthe khlBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    khlBean = new ErpKehoachtongthe(id);
	    khlBean.setCtyId(ctyId);
	    khlBean.setId(id);
	    khlBean.setUserId(userId);
	    
	    khlBean.init();
        session.setAttribute("khlBean", khlBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachTongTheDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");		
		IErpKehoachtongthe khlBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	khlBean = new ErpKehoachtongthe("");
	    }else{
	    	khlBean = new ErpKehoachtongthe(id);
	    }
	    	    
	    khlBean.setCtyId(ctyId);
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		khlBean.setUserId(userId);	       
    			
		String ngaylap = util.antiSQLInspection(request.getParameter("ngaylap"));
		if (ngaylap == null || ngaylap.trim().length() < 10)
			ngaylap = getDateTime();
		khlBean.setNgaykehoach(ngaylap);
		
		System.out.println("___Ngay ke hoach: " + ngaylap);
		
		String nhIds = util.antiSQLInspection(request.getParameter("nhIds"));
		if (nhIds == null)
			nhIds = "";
		khlBean.setNhIds(nhIds);
		
		String clIds = util.antiSQLInspection(request.getParameter("clIds"));
		if (clIds == null)
			clIds = "";
		khlBean.setClIds(clIds);
				
		String[] thang = request.getParameterValues("thang");
		khlBean.setThang(thang);
				
    	String[] spIds = request.getParameterValues("spIds");
    	if(spIds != null)
    	{
    		String str = "";
    		for(int i = 0; i < spIds.length; i++ )
    			str += spIds[i] + ",";
    		if(str.length() > 0)
    			str = str.substring(0, str.length() - 1);
    		khlBean.setSpIds(str);
    	}
   	
 		String action = request.getParameter("action");
 		if(action.equals("thuchien"))
 		{

   			if (!khlBean.createKehoach())
   			{
   		    	khlBean.setUserId(userId);
   		    	khlBean.createRs();
   		    	session.setAttribute("userId", userId);
   				session.setAttribute("khlBean", khlBean);
    				
   				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachTongTheNew.jsp";
   				response.sendRedirect(nextJSP);
   			}
   			else
   			{
   				IErpKehoachtongtheList obj = new ErpKehoachtongtheList();
   				obj.setCtyId(ctyId);
   				obj.setUserId(userId);
   				obj.init("");
    				
   				session.setAttribute("obj", obj);
   				session.setAttribute("userId", userId);
		    		
   				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachTongThe.jsp");	    	
    		}    		
	    }
		else
		{
				khlBean.createRs();
				session.setAttribute("userId", userId);
				session.setAttribute("khlBean", khlBean);
				
				String nextJSP;
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachTongTheNew.jsp";
				response.sendRedirect(nextJSP);
		}		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
