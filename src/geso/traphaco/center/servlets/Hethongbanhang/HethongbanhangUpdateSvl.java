package geso.traphaco.center.servlets.Hethongbanhang;

import geso.traphaco.center.beans.hethongbanhang.IHethongbanhang;
import geso.traphaco.center.beans.hethongbanhang.IHethongbanhangList;
import geso.traphaco.center.beans.hethongbanhang.imp.Hethongbanhang;
import geso.traphaco.center.beans.hethongbanhang.imp.HethongbanhangList;
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

public class HethongbanhangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public HethongbanhangUpdateSvl() 
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

	    IHethongbanhang htbhBean = new Hethongbanhang(id);	   
	    htbhBean.setUserId(userId);
        session.setAttribute("htbhBean", htbhBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHangUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IHethongbanhang htbhBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	htbhBean = new Hethongbanhang("");
	    }else{
	    	htbhBean = new Hethongbanhang(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		htbhBean.setUserId(userId);
	    
    	String hethongbanhang = util.antiSQLInspection(request.getParameter("hethongbanhang"));
		if (hethongbanhang == null)
			hethongbanhang = "";				
		htbhBean.setHethongbanhang(hethongbanhang);
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		htbhBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	htbhBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
		htbhBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		htbhBean.setNguoisua(nguoisua);
    		
		boolean error = false;
				
		if (hethongbanhang.trim().length()== 0){
			htbhBean.setMessage("Vui Long Nhap Ten Kenh Ban Hang");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			htbhBean.setMessage("Vui Long Nhap Dien Giai Kenh Cua Hang");
			error = true;
		}
 		
		String kbhId = "";
		String[] kbhIds = request.getParameterValues("kbhId");

		if (kbhIds != null)
		{
			for (int i = 0; i < kbhIds.length; i++)
			{
				kbhId += kbhIds[i] + ",";
			}
			System.out.println("_________"+kbhId);
			
			if (kbhId.length() > 0)
				kbhId = kbhId.substring(0, kbhId.length() - 1);
		}
		htbhBean.setKbhId(kbhId);
		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(htbhBean.CreateHtbh())){				
					session.setAttribute("htbhBean", htbhBean);
					htbhBean.setUserId(userId);
					htbhBean.createKbhRs();
					String nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHangNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IHethongbanhangList obj = new HethongbanhangList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				System.out.println("aaaaaaaaaaaa");
				if (!(htbhBean.UpdateHtbh())){			
					session.setAttribute("htbhBean", htbhBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IHethongbanhangList obj = new HethongbanhangList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
			htbhBean.setUserId(userId);
			htbhBean.createKbhRs();
			session.setAttribute("htbhBean", htbhBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHangNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Center/HeThongBanHangUpdate.jsp";   						
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
