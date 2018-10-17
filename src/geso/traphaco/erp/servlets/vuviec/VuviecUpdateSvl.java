package geso.traphaco.erp.servlets.vuviec;
import geso.traphaco.erp.beans.vuviec.*;
import geso.traphaco.erp.beans.vuviec.imp.*;
import geso.traphaco.distributor.util.Utility;

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

public class VuviecUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public VuviecUpdateSvl() 
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
	    	userId = request.getParameter("userId");
	    
	    String id = util.getId(querystring);  	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IVuviec dmplBean = new Vuviec(id);	    
	    
        dmplBean.setUserId(userId);
        dmplBean.setCongty(ctyId);
        session.setAttribute("dmplBean", dmplBean);
        String nextJSP = "/TraphacoHYERP/pages/Erp/VuViecUpdate.jsp";
        if(querystring.contains("display"))
        	nextJSP = "/TraphacoHYERP/pages/Erp/VuViecDisplay.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IVuviec dmplBean;
		this.out = response.getWriter();
		
	    String id =  request.getParameter("id");
	    
	    if(id == null){  	
	    	dmplBean = new Vuviec("");
	    }else{
	    	dmplBean = new Vuviec(id);
	    }
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
		String userId = request.getParameter("userId");
		dmplBean.setUserId(userId);
		dmplBean.setCongty(ctyId);
	    
    	String ma = request.getParameter("ma");
		if (ma == null)
			ma = "";				
    	dmplBean.setMa(ma);
    	
    	String diengiai = request.getParameter("diengiai");
		if (diengiai == null)
			diengiai = "";
		dmplBean.setDiengiai(diengiai);


		String ngaysua = getDateTime();
    	dmplBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		dmplBean.setNguoisua(nguoisua);
    		
		boolean error = false;
				
		if (ma.trim().length()== 0){
			dmplBean.setMessage("Vui Long Nhap Ma Vu Viec");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			dmplBean.setMessage("Vui Long Nhap Ten Vu Viec");
			error = true;
		}
 		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(dmplBean.CreateKbh())){				
					session.setAttribute("dmplBean", dmplBean);
					dmplBean.setUserId(userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/VuViecNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IVuviecList obj = new VuviecList();
					obj.setUserId(userId);
					obj.setCongty(ctyId);
					
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Erp/VuViecList.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(dmplBean.UpdateKbh())){			
					session.setAttribute("dmplBean", dmplBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/VuViecUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IVuviecList obj = new VuviecList();
					obj.setUserId(userId);
					obj.setCongty(ctyId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Erp/VuViecList.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
			dmplBean.setUserId(userId);
			session.setAttribute("dmplBean", dmplBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Erp/VuViecNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Erp/VuViecUpdate.jsp";   						
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
