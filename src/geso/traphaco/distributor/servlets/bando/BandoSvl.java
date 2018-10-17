package geso.traphaco.distributor.servlets.bando;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.bando.IBando;
import geso.traphaco.distributor.beans.bando.imp.Bando;

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


public class BandoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IBando obj;
	PrintWriter out; 
       
    public BandoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    
	    obj = new Bando();
	    obj.setUserId(userId);
	    
	    if(view.equals("dophu"))
	    {
	    	obj.initBCDoPhu();
	    	session.setAttribute("obj", obj);
		    
		    String nextJSP = "/TraphacoHYERP/pages/Distributor/DoPhuNganhHang.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else if(view.equals("dophuTT"))
		{
			obj.initBCDoPhuTT();
	    	session.setAttribute("obj", obj);
		    
		    String nextJSP = "/TraphacoHYERP/pages/Center/DoPhuNganhHang.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
		    obj.init();
			session.setAttribute("obj", obj);
		    
		    String nextJSP = "/TraphacoHYERP/pages/Distributor/BanDo.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    obj = new Bando();
	    obj.setUserId(userId);
	    
	    String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String ngay = util.antiSQLInspection(request.getParameter("ngay"));
		if (ngay == null)
			ngay = getDateTime();
		obj.setDate(ngay);
		
	    String ddkdId = util.antiSQLInspection(request.getParameter("ddkd"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String tbhId = util.antiSQLInspection(request.getParameter("tbh"));
		if (tbhId == null)
			tbhId = "";
		obj.setTbhId(tbhId);
		
		String address = util.antiSQLInspection(request.getParameter("address"));
		if (address == null)
			address = "";
		obj.setAddress(address);
		
		String clId = util.antiSQLInspection(request.getParameter("chungloai"));
		if (clId == null)
			clId = "";
		obj.setChungloaiId(clId);
		
		String trungtam = util.antiSQLInspection(request.getParameter("trungtam"));
		if (trungtam == null)
			trungtam = "";
		obj.setTrungtam(trungtam);
		System.out.println("Trung tam la: " + trungtam);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
	        
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);
		
		String kvId = util.antiSQLInspection(request.getParameter("kvId"));
		if (kvId == null)
			kvId = "";
		obj.setKhuvucId(kvId);
		
		String[] nppIds = request.getParameterValues("nppLocIds");
    	if(nppIds != null)
    	{
    		String str = "";
    		for(int i = 0; i < nppIds.length; i++ )
    			str += nppIds[i] + ",";
    		if(str.length() > 0)
    			str = str.substring(0, str.length() - 1);
    		obj.setNppLocId(str);
    	}
		System.out.println("----NPP LOC: " + obj.getNppLocId() );
		
		String[] spIds = request.getParameterValues("spIds");
    	if(spIds != null)
    	{
    		String str = "";
    		for(int i = 0; i < spIds.length; i++ )
    			str += spIds[i] + ",";
    		if(str.length() > 0)
    			str = str.substring(0, str.length() - 1);
    		System.out.println("[BandoSvl.doPost] spIds = " + str);
    		obj.setSanphamId(str);
    	}
		
		String view = request.getParameter("view");
		if(view == null)
			view = "";
		
		if(view.equals("dophu"))
		{
			obj.initBCDoPhu();
	    	session.setAttribute("obj", obj);
		    
		    String nextJSP = "/TraphacoHYERP/pages/Distributor/DoPhuNganhHang.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			if(view.equals("dophuTT"))
			{
				obj.initBCDoPhuTT();
		    	session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Center/DoPhuNganhHang.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
			    obj.init();
				session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Distributor/BanDo.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
