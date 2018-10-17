package geso.traphaco.center.servlets.nhiemvu;

import geso.traphaco.center.beans.nhiemvu.*;
import geso.traphaco.center.beans.nhiemvu.imp.*;
import geso.traphaco.center.db.sql.dbutils;
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

public class NhiemVuUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public NhiemVuUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring); 
	    
	    System.out.println("[NhiemVuUpdateSvl.doGet] id = " + id);

	    INhiemVu nvBean = new NhiemVu(id);
	    nvBean.setUserId(userId);
        nvBean.init();
        
        session.setAttribute("nvBean", nvBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVuUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INhiemVu bean;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	bean = new NhiemVu("");
	    } else {
	    	bean = new NhiemVu(id);
	    }
	    System.out.println("[NhiemVuUpdateSvl.doPost] id = " + bean.getId());
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bean.setUserId(userId);
    	System.out.println("[NhiemVuUpdateSvl.doPost] userId = " + bean.getUserId());
    	
    	String tieuchi = util.antiSQLInspection(request.getParameter("tieuchi"));
    	if (tieuchi == null) { tieuchi = ""; }    	
    	bean.setTieuChi(tieuchi.trim());
    	System.out.println("[NhiemVuUpdateSvl.doPost] tieuchi = " + bean.getTieuChi());
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    	if ( diengiai == null) { diengiai = ""; }
    	bean.setDienGiai(diengiai.trim());
    	System.out.println("[NhiemVuUpdateSvl.doPost] diengiai = " + bean.getDienGiai());
    	
    	String istudong = util.antiSQLInspection(request.getParameter("istudong"));
    	if (istudong == null) { istudong = ""; }
    	bean.setIsTuDong(istudong.equals("on"));
    	System.out.println("[NhiemVuUpdateSvl.doPost] istudong = " + bean.getIsTuDong());
    	
    	String doituong = util.antiSQLInspection(request.getParameter("doituong"));
    	if (doituong == null) { doituong = ""; }
    	bean.setDoiTuong(doituong.trim());
    	System.out.println("[NhiemVuUpdateSvl.doPost] doituong = " + bean.getDoiTuong());
    	
    	String loaitieuchi = util.antiSQLInspection(request.getParameter("loaitieuchi" + doituong));
    	if (loaitieuchi == null) { loaitieuchi = ""; }    	
    	bean.setLoaiTieuChi(loaitieuchi.trim());
    	System.out.println("[NhiemVuUpdateSvl.doPost] loaitieuchi = " + bean.getLoaiTieuChi());
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null) { trangthai = ""; }
    	bean.setTrangThai(trangthai.equals("on") ? "1" : "0");
    	System.out.println("[NhiemVuUpdateSvl.doPost] trangthai = " + bean.getTrangThai());
		
		String ngaysua = getDateTime();
		bean.setNgaySua(ngaysua);
		System.out.println("[NhiemVuUpdateSvl.doPost] ngaysua = " + bean.getNgaySua());
	
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
    		if (id == null || id.length() == 0 )
    		{ //Create New NhiemVu
    			if(!bean.create())
    			{
    		    	bean.setUserId(userId);
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nvBean", bean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVuNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else 
    			{
    				INhiemVuList obj = new NhiemVuList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/NhiemVu.jsp");	    	
    			}		
    		} 
    		else 
    		{
    			//Update NhiemVu
    			if ( !(bean.update()) )
    			{
    				//Update unsuccessfully
    		    	bean.setUserId(userId);
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nvBean", bean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVuUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else 
    			{
    				//Update successfully
    				INhiemVuList obj = new NhiemVuList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/NhiemVu.jsp");  	
    			}
    		}
	    }
		else
		{
			session.setAttribute("userId", userId);
			session.setAttribute("nvBean", bean);
			String nextJSP = id == null ?  "/TraphacoHYERP/pages/Center/NhiemVuNew.jsp" : "/TraphacoHYERP/pages/Center/NhiemVuUpdate.jsp";   						
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
