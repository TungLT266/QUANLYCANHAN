package geso.traphaco.center.servlets.khuvuc;

import geso.traphaco.center.beans.khuvuc.imp.*;
import geso.traphaco.center.beans.khuvuc.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhuvucUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public KhuvucUpdateSvl() 
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

	    IKhuvuc kvBean = new Khuvuc(id);
	    
        kvBean.setUserId(userId);
        session.setAttribute("kvBean", kvBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/KhuVucUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhuvuc kvBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	kvBean = new Khuvuc("");
	    }else{
	    	kvBean = new Khuvuc(id);
	    }
	    
	    dbutils db = new dbutils();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kvBean.setUserId(userId);
	    
		String khuvuc = util.antiSQLInspection(request.getParameter("khuvuc"));
		if (khuvuc == null)
			khuvuc = "";				
    	kvBean.setTen(khuvuc);
    	
    	String vung = util.antiSQLInspection(request.getParameter("vungmien"));
		if (vung == null)
			vung = "";				
    	kvBean.setVmId(vung);
    	if(vung.length() > 0)
    	{
    		String sql = "Select tenqg from quocgia,vung where quocgia.pk_seq = vung.quocgia_fk and vung.pk_seq = "+vung;
    	System.out.println("truy van "+sql);
    	ResultSet rs = db.get(sql);
    	try {
			while (rs.next())
			{
				kvBean.setTenqg(rs.getString("tenqg"));
			}
		} catch (SQLException e)
    	{
			
			e.printStackTrace();
		}
    	}
    	String asm = util.antiSQLInspection(request.getParameter("asm"));
    	if(asm == null)
    		asm="";
    	kvBean.setAsm(asm);
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		kvBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	kvBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	kvBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		kvBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (khuvuc.trim().length()== 0){
			kvBean.setMessage("Vui Long Nhap Ten Khu Vuc");
			error = true;
		}
		
		if (vung.trim().length()== 0){
			kvBean.setMessage("Vui Long Chon Vung Mien");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			kvBean.setMessage("Vui Long Nhap Dien Giai Khu Vuc");
			error = true;
		}
 		
 		String action = request.getParameter("action");
	    if (!error){
	    	if(action.equals("save"))
	    	{
	    		if ( id == null){
	    			if (!(kvBean.CreateKv())){				
	    				session.setAttribute("kvBean", kvBean);
	    				kvBean.setUserId(userId);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhuVucNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IKhuvucList obj = new KhuvucList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhuVuc.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(kvBean.UpdateKv())){			
	    				session.setAttribute("kvBean", kvBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhuVucUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				IKhuvucList obj = new KhuvucList();
	    				obj.setUserId(userId);
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhuVuc.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}else{
	    		kvBean.setUserId(userId);
	    		session.setAttribute("kvBean", kvBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/TraphacoHYERP/pages/Center/KhuVucNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/KhuVucUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		kvBean.setUserId(userId);
    		session.setAttribute("kvBean", kvBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/TraphacoHYERP/pages/Center/KhuVucNew.jsp";
    		}else{
    			nextJSP = "/TraphacoHYERP/pages/Center/KhuVucUpdate.jsp";   						
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
