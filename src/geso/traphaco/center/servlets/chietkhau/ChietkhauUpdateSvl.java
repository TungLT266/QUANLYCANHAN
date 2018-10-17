package geso.traphaco.center.servlets.chietkhau;

import geso.traphaco.center.beans.mucchietkhau.IChietkhau;
import geso.traphaco.center.beans.mucchietkhau.IChietkhauList;
import geso.traphaco.center.beans.mucchietkhau.imp.Chietkhau;
import geso.traphaco.center.beans.mucchietkhau.imp.ChietkhauList;
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

public class ChietkhauUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  
       
    public ChietkhauUpdateSvl() {
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

	    IChietkhau ckBean = new Chietkhau(id);	
	    
        ckBean.setUserId(userId);
        session.setAttribute("ckBean", ckBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/ChietKhauUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IChietkhau ckBean;
		this.out = response.getWriter();
		
	    String id =  request.getParameter("id");
	    if(id == null){  	
	    	ckBean = new Chietkhau("");
	    }else{
	    	ckBean = new Chietkhau(id);
	    }
	    
	    
		String userId = request.getParameter("userId");
		ckBean.setUserId(userId);
	    
    	String loai = request.getParameter("loai");
		if (loai == null)
			loai = "";
    	ckBean.setLoai(loai);
    	
    	String diengiai = request.getParameter("diengiai");
		if (diengiai == null)
			diengiai = "";
		ckBean.setDiengiai(diengiai);
		
		String ptDoanhthu = request.getParameter("ptDoanhthu");
		if (ptDoanhthu == null)
			ptDoanhthu = "0";
		ckBean.setPhantramDS(ptDoanhthu);
		
		String gioihan = request.getParameter("gioihan");
		if (gioihan == null)
			gioihan = "0";
		ckBean.setGioihan(gioihan);
		
		String tongsotien = request.getParameter("tongsotien");
		if (tongsotien == null)
			tongsotien = "0";
		ckBean.setTongsotien(tongsotien);
		
		String hanmucno = request.getParameter("hanmucno");
		if (hanmucno == null)
			hanmucno = "0";
		ckBean.setHanmucno(hanmucno);
		
		String songayno = request.getParameter("songayno");
		if (songayno == null)
			songayno = "0";
		ckBean.setSongayno(songayno);
		
		String sotienno = request.getParameter("sotienno");
		if (sotienno == null)
			sotienno = "0";
		ckBean.setSotien(sotienno);
		
 		String action = request.getParameter("action");
		if(action.equals("save"))
		{
			if (!(ckBean.Update()))
			{			
				session.setAttribute("ckBean", ckBean);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChietKhauUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IChietkhauList obj = new ChietkhauList();
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
					
				String nextJSP = "/TraphacoHYERP/pages/Center/ChietKhau.jsp";
				response.sendRedirect(nextJSP);			    			    									
			}
		}
		else
		{
			ckBean.setUserId(userId);
			session.setAttribute("ckBean", ckBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Center/ChietKhauNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Center/ChietKhauUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}


}
