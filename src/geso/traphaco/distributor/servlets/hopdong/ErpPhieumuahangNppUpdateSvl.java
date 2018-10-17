package geso.traphaco.distributor.servlets.hopdong;

import geso.traphaco.distributor.beans.hopdong.IErpPhieumuahangNpp;
import geso.traphaco.distributor.beans.hopdong.IErpPhieumuahangNppList;
import geso.traphaco.distributor.beans.hopdong.imp.ErpPhieumuahangNpp;
import geso.traphaco.distributor.beans.hopdong.imp.ErpPhieumuahangNppList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhieumuahangNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpPhieumuahangNppUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpPhieumuahangNpp lsxBean = new ErpPhieumuahangNpp(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		if(querystring.contains("display"))
    		{
    			lsxBean.init();
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNppDisplay.jsp";
    		}
    		else
    		{
    			lsxBean.init();
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNppUpdate.jsp";
    		}
    		
	        session.setAttribute("lsxBean", lsxBean);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			
			IErpPhieumuahangNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpPhieumuahangNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpPhieumuahangNpp(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		    if(nppId == null)
		    	nppId = "";
		    lsxBean.setNppId(nppId);
		    
		    String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		    if(tungay == null || tungay.trim().length() <= 0 )
		    	tungay = "";
		    lsxBean.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		    if(denngay == null || denngay.trim().length() <= 0)
		    	denngay = "";
		    lsxBean.setDenngay(denngay);
		    	    
		    String maphieu = util.antiSQLInspection(request.getParameter("maphieu"));
		    if(maphieu == null)
		    	maphieu = "";
		    lsxBean.setMaphieu(maphieu);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String soluong = util.antiSQLInspection(request.getParameter("soluong"));
			if (soluong == null)
				soluong = "";				
			lsxBean.setSoluong(soluong);
			
			String giatri = util.antiSQLInspection(request.getParameter("giatri"));
			if (giatri == null)
				giatri = "";				
			lsxBean.setGiatri(giatri);

		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.create();
					if(!kq)
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNppNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhieumuahangNppList obj = new ErpPhieumuahangNppList();
						
						obj.setUserId(userId);
						obj.init("");  
						
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNpp.jsp";	
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = lsxBean.update();
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNppUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhieumuahangNppList obj = new ErpPhieumuahangNppList();

					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNppNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpPhieuMuaHangNppUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
	
}
