package geso.traphaco.center.servlets.khoasongay;

import geso.traphaco.center.beans.khoasongay.IKhoasotudong;
import geso.traphaco.center.beans.khoasongay.imp.Khoasotudong;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KhoasotudongSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
	
    public KhoasotudongSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IKhoasotudong obj;
		
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
	    
	    obj = new Khoasotudong();
	    obj.setUserId(userId);
	    obj.init();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoTuDong.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IKhoasotudong obj = new Khoasotudong();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		
		String[] vungIds = request.getParameterValues("vungIds");
		//obj.setVungIds(vungIds);
	
		String[] kvIds = request.getParameterValues("kvIds");
		//obj.setKvIds(kvIds);
		String[] nppIds = request.getParameterValues("nppIds");
		
		
		obj = new Khoasotudong();
	    obj.setUserId(userId);
	    	
	    obj.createRs();
	    obj.setVungIds(vungIds);
	    obj.setKvIds(kvIds);
	    obj.setNppIds(nppIds);
	    	    
	    String action = request.getParameter("action");
		if(!action.equals("save"))
		{ 
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoTuDong.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			try
			{
				if(obj.updateKhoaso())
					obj.setMsg("Thiet lap khoa so tu dong thanh cong");
				else
					obj.setMsg("Khong the thiet lap khoa so...");
			}
			catch (Exception e)
			{
				obj.setMsg("Khong the Cap nhat khoa so tu dong " + e.toString());
			}
			
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoTuDong.jsp";
			response.sendRedirect(nextJSP);
			
		}
		
	}

}
