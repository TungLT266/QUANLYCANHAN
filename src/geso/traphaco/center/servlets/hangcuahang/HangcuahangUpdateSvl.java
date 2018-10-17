package geso.traphaco.center.servlets.hangcuahang;

import geso.traphaco.center.beans.hangcuahang.IHangcuahang;
import geso.traphaco.center.beans.hangcuahang.IHangcuahangList;
import geso.traphaco.center.beans.hangcuahang.imp.Hangcuahang;
import geso.traphaco.center.beans.hangcuahang.imp.HangcuahangList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class HangcuahangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   

	public HangcuahangUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		IHangcuahangList obj;
		   PrintWriter out;
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    //Utility util = new Utility();
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    IHangcuahang hchBean = new Hangcuahang(id);
	    
        hchBean.setUserId(userId);
        session.setAttribute("hchBean", hchBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHangUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IHangcuahangList obj;
		   PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IHangcuahang hchBean;
		out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	hchBean = new Hangcuahang("");
	    }else{
	    	hchBean = new Hangcuahang(id);
	    }
	        
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		hchBean.setUserId(userId);
	    
    	String hang = util.antiSQLInspection(request.getParameter("hangcuahang"));
		if (hang == null)
			hang = "";				
    	hchBean.setHangcuahang(hang);
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		hchBean.setDiengiai(diengiai);
		
		String doanhthu_tu = util.antiSQLInspection(request.getParameter("DT_Tu"));
		if (doanhthu_tu == null)
		{
			doanhthu_tu = "";
		}
		hchBean.setDoanhThu_Tu(doanhthu_tu);
		
		String doanhthu_den = util.antiSQLInspection(request.getParameter("DT_Den"));
		if(doanhthu_den == null)
		{
			doanhthu_den = "";
		}
		hchBean.setDoanhThu_Den(doanhthu_den);
		

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	hchBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	hchBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		hchBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (hang.trim().length()== 0){
			hchBean.setMessage("Vui Long Nhap Loai Cua Hang");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			hchBean.setMessage("Vui Long Nhap Dien Giai Loai Cua Hang");
			error = true;
		}
		
		if(doanhthu_tu.trim().length()==0){
			hchBean.setMessage("Vui Long Nhap Han Muc Doanh Thu Duoi");
			error = true;
		}
			
		if(doanhthu_den.trim().length()==0){
			hchBean.setMessage("Vui Long Nhap Han Muc Doanh Thu Tren");
			error = true;
		}
		//Kiem tra doanhthu_tu < doanhthu_den
		if(doanhthu_tu.trim().length()!=0 && doanhthu_den.trim().length()!=0)
		{
			int dt_tu = Integer.parseInt(doanhthu_tu);
			int dt_den = Integer.parseInt(doanhthu_den);
			if(dt_tu>=dt_den)
			{
				hchBean.setMessage("Doanh Thu Tren Phai Lon Hon Doanh Thu Duoi");
				error = true;
			}	
		}
		
		//Kiem tra cac doanhthu_tu va doanhthu_den co trung voi cap khac
		if(doanhthu_tu.trim().length()!=0 && doanhthu_den.trim().length()!=0)
		{
			if(hchBean.KiemTraHMDoanhThuTrung()==true)
			{
				hchBean.setMessage("Cap Doanh Thu Tren Va Duoi Da Ton Tai");
				error = true;
			}
		}

 		
 		String action = request.getParameter("action");
	    if (!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null){
	    			if (!(hchBean.CreateHch())){				
	    				session.setAttribute("hchBean", hchBean);
	    				hchBean.setUserId(userId);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHangNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				obj = new HangcuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(hchBean.UpdateHch())){			
	    				session.setAttribute("hchBean", hchBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				obj = new HangcuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		hchBean.setUserId(userId);
	    		session.setAttribute("hchBean", hchBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHangNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHangUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
	    	}
		}else{
    		hchBean.setUserId(userId);
    		session.setAttribute("hchBean", hchBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHangNew.jsp";
    		}else{
    			nextJSP = "/TraphacoHYERP/pages/Center/HangCuaHangUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}