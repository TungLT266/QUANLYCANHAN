package geso.traphaco.center.servlets.loaicuahang;

import geso.traphaco.center.beans.loaicuahang.imp.*;
import geso.traphaco.center.beans.loaicuahang.*;
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

public class LoaicuahangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public LoaicuahangUpdateSvl() 
    {
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
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    ILoaicuahang lchBean = new Loaicuahang(id);
	    
        lchBean.setUserId(userId);
        session.setAttribute("lchBean", lchBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHangUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		ILoaicuahang lchBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	lchBean = new Loaicuahang("");
	    }else{
	    	lchBean = new Loaicuahang(id);
	    }
	        
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		lchBean.setUserId(userId);
	    
    	String loai = util.antiSQLInspection(request.getParameter("loaicuahang"));
		if (loai == null)
			loai = "";				
    	lchBean.setLoaicuahang(loai);
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		lchBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	lchBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	lchBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		lchBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (loai.trim().length()== 0){
			lchBean.setMessage("Vui Long Nhap Loai Cua Hang");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			lchBean.setMessage("Vui Long Nhap Dien Giai Loai Cua Hang");
			error = true;
		}
 		
 		String action = request.getParameter("action");
	    if(!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null){
	    			if (!(lchBean.CreateLch())){				
	    				session.setAttribute("lchBean", lchBean);
	    				lchBean.setUserId(userId);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHangNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				ILoaicuahangList obj = new LoaicuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(lchBean.UpdateLch())){			
	    				session.setAttribute("lchBean", lchBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				ILoaicuahangList obj = new LoaicuahangList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		lchBean.setUserId(userId);
	    		session.setAttribute("lchBean", lchBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHangNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHangUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		lchBean.setUserId(userId);
    		session.setAttribute("lchBean", lchBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHangNew.jsp";
    		}else{
    			nextJSP = "/TraphacoHYERP/pages/Center/LoaiCuaHangUpdate.jsp";   						
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
