package geso.traphaco.center.servlets.duyettrakhuyenmai;

import geso.traphaco.center.beans.duyettrakhuyenmai.*;
import geso.traphaco.center.beans.duyettrakhuyenmai.imp.*;
import geso.traphaco.center.util.Utility;
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

public class DuyettrakhuyenmaiUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public DuyettrakhuyenmaiUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		IDuyettrakhuyenmai tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new Duyettrakhuyenmai(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMaiDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDuyettrakhuyenmai tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	tctskuBean = new Duyettrakhuyenmai("");
	    }else{
	    	tctskuBean = new Duyettrakhuyenmai(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String scheme = util.antiSQLInspection(request.getParameter("ctkmId"));
		if (scheme == null)
			scheme = "";
		tctskuBean.setCtkmId(scheme);
		
		String ngayduyet = util.antiSQLInspection(request.getParameter("ngayduyet"));
		if (ngayduyet == null)
			ngayduyet = "";
		tctskuBean.setNgayduyet(ngayduyet);
		
		String[] nppId = request.getParameterValues("nppId");
		tctskuBean.setNppId(nppId);
		
		String[] nppTen = request.getParameterValues("nppTen");
		tctskuBean.setNppTen(nppTen);
		
		String[] khId = request.getParameterValues("khId");
		tctskuBean.setKhId(khId);
		
		String[] khTen = request.getParameterValues("khTen");
		tctskuBean.setKhTen(khTen);
		
		String[] doanhso = request.getParameterValues("doanhso");
		tctskuBean.setDoanhso(doanhso);
		
		String[] tongtien = request.getParameterValues("tongtien");
		tctskuBean.setTongtien(tongtien); 
		
		String[] donviThuong = request.getParameterValues("donviThuong");
		tctskuBean.setDonvithuong(donviThuong);

 		String action = request.getParameter("action");
 		System.out.println("Action la: " + action);
 		
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMaiNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IDuyettrakhuyenmaiList obj = new DuyettrakhuyenmaiList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMai.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					IDuyettrakhuyenmaiList obj = new DuyettrakhuyenmaiList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMai.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMaiNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraKhuyenMaiUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
