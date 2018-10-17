package geso.traphaco.center.servlets.Nhomkenh;

import geso.traphaco.center.beans.nhomkenh.INhomkenh;
import geso.traphaco.center.beans.nhomkenh.INhomkenhList;
import geso.traphaco.center.beans.nhomkenh.imp.Nhomkenh;
import geso.traphaco.center.beans.nhomkenh.imp.NhomkenhList;
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

public class NhomkenhUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public NhomkenhUpdateSvl() 
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

	    INhomkenh nkBean = new Nhomkenh(id);	   
	    nkBean.setUserId(userId);
        session.setAttribute("nkBean", nkBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/NhomKenhUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		INhomkenh nkBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	nkBean = new Nhomkenh("");
	    }else{
	    	nkBean = new Nhomkenh(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nkBean.setUserId(userId);
	    
    	String Nhomkenh = util.antiSQLInspection(request.getParameter("nhomkenh"));
		if (Nhomkenh == null)
			Nhomkenh = "";				
		nkBean.setNhomkenh(Nhomkenh);
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		nkBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	nkBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
		nkBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		nkBean.setNguoisua(nguoisua);
    		
		boolean error = false;
				
		if (Nhomkenh.trim().length()== 0){
			nkBean.setMessage("Vui Long Nhap Ten Kenh Ban Hang");
			error = true;
		}

		if (diengiai.trim().length()== 0){
			nkBean.setMessage("Vui Long Nhap Dien Giai Kenh Cua Hang");
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
		nkBean.setKbhId(kbhId);
		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(nkBean.CreateNk())){				
					session.setAttribute("nkBean", nkBean);
					nkBean.setUserId(userId);
					nkBean.createKbhRs();
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomKenhNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					INhomkenhList obj = new NhomkenhList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomKenh.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(nkBean.UpdateNk())){			
					session.setAttribute("nkBean", nkBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomKenhUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					INhomkenhList obj = new NhomkenhList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomKenh.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
			nkBean.setUserId(userId);
			nkBean.createKbhRs();
			session.setAttribute("nkBean", nkBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Center/NhomKenhNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Center/NhomKenhUpdate.jsp";   						
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
