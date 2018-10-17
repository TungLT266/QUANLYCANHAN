package geso.traphaco.center.servlets.vung;

import geso.traphaco.center.beans.vung.imp.*;
import geso.traphaco.center.beans.vung.*;
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

public class VungmienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public VungmienUpdateSvl() 
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

	    IVungmien vmBean = new Vungmien(id);
	    
        vmBean.setUserId(userId);
        session.setAttribute("vmBean", vmBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/VungMienUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IVungmien vmBean;
		this.out = response.getWriter();
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	vmBean = new Vungmien("");
	    }else{
	    	vmBean = new Vungmien(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		vmBean.setUserId(userId);
	    
    	String vungmien = util.antiSQLInspection(request.getParameter("vungmien"));
		if (vungmien == null)
			vungmien = "";				
    	vmBean.setVungmien(vungmien);
    	String bm = util.antiSQLInspection(request.getParameter("bm"));
    	if(bm == null)
    		bm ="";
    	vmBean.setBm(bm);
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		vmBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	vmBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	vmBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		vmBean.setNguoisua(nguoisua);
    	
		String quocgia = request.getParameter("quocgia");
		System.out.println("quocgia "+quocgia );
		if(quocgia == null)
			quocgia = "";
		vmBean.setMaQuocgia(quocgia);
		boolean error = false;
				
		if (vungmien.trim().length()== 0){
			vmBean.setMessage("Vui Long Nhap Ten Vung Mien");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			vmBean.setMessage("Vui Long Nhap Dien Giai Vung Mien");
			error = true;
		}
 		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(vmBean.CreateVm())){				
					session.setAttribute("vmBean", vmBean);
					vmBean.setUserId(userId);
					String nextJSP = "/TraphacoHYERP/pages/Center/VungMienNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IVungmienList obj = new VungmienList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/VungMien.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(vmBean.UpdateVm())){			
					session.setAttribute("vmBean", vmBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/VungMienUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IVungmienList obj = new VungmienList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/VungMien.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
			vmBean.setUserId(userId);
			session.setAttribute("vmBean", vmBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Center/VungMienNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Center/VungMienUpdate.jsp";   						
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
