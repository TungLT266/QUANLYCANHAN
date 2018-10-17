package geso.traphaco.erp.servlets.masclon;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLonList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_MaSCLonSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_MaSCLonSvl() {
			super();
		}   
	   
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	   {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    
	      
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    Erp_MaSCLonList obj = new Erp_MaSCLonList();
		    
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    obj.setCongTyId(ctyId);
		    String id = util.getId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    

		    if (action.equals("delete")){	   		  	    	
		    	Erp_MaSCLon maSCLon = new Erp_MaSCLon();
		    	maSCLon.setId(id);
		    	maSCLon.setCongTyId(ctyId);
		    	maSCLon.init();
		    	maSCLon.deleteMaSCLon();
		    	obj.setMsg(maSCLon.getMsg());
		    	maSCLon.DbClose();
		    }else
	    	if (action.equals("chot"))
	    	{
	    		Erp_MaSCLon maSCLon = new Erp_MaSCLon();
	    		maSCLon.setId(id);
	    		maSCLon.setCongTyId(ctyId);
	    		maSCLon.init();
	    		maSCLon.chotMASCLON();
		    	obj.setMsg(maSCLon.getMsg());
		    	maSCLon.DbClose();
	    	}if (action.equals("mochot"))
	    	{
	    		Erp_MaSCLon maSCLon = new Erp_MaSCLon();
	    		maSCLon.setId(id);
	    		maSCLon.setCongTyId(ctyId);
	    		//maSCLon.setNppId(nppId);
	    		maSCLon.init();
	    		maSCLon.mochot();
		    	obj.setMsg(maSCLon.getMsg());
		    	maSCLon.DbClose();
	    	}
	    	else if (action.equals("chuyen"))
	    	{
	    		Erp_MaSCLon maSCLon = new Erp_MaSCLon();
	    		maSCLon.setId(id);
	    		maSCLon.setCongTyId(ctyId);
	    		maSCLon.init();
	    		maSCLon.chuyenMASCLON();
		    	obj.setMsg(maSCLon.getMsg());
		    	maSCLon.DbClose();
	    	}
		   	
			obj.init();
			
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_MaSCLon.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = util.getAction(querystring);
		    	if (action == null)
		    		action = "";
		    }
		        
		    if (action.equals("new"))
		    {
		    	Erp_MaSCLon obj = new Erp_MaSCLon();
			    String ctyId = (String)session.getAttribute("congtyId");
			    
			    obj.setCongTyId(ctyId);
			    obj.init();
			    session.setAttribute("action", action);
		    	session.setAttribute("userId", userId);
		    	session.setAttribute("obj", obj);
	    		
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLonUpdate.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
		    }
		    
		    Erp_MaSCLonList obj = new Erp_MaSCLonList();
		    if (action.equals("search"))
		    {
			    String ctyId = (String)session.getAttribute("congtyId");

			    obj.setCongTyId(ctyId);
			    
			    String taiSanId = request.getParameter("taiSanId");
			    String taiKhoanId = request.getParameter("taiKhoanId");
			    obj.setTaiKhoanId(taiKhoanId);
			    obj.setTaiSanId(taiSanId);
			    System.out.println("tai san co dinh"+taiSanId);
			    
			    String soChungTu = request.getParameter("soChungTu");
			    obj.setSoChungTu(soChungTu);
			    
			    
			    String ngayBatDau = request.getParameter("ngayBatDau");
			    obj.setNgayBatDau(ngayBatDau);
			    
			    String ma = request.getParameter("ma");
			    obj.setMa(ma);
			    
			    String ngayKetThuc = request.getParameter("ngayKetThuc");
			    obj.setNgayKetThuc(ngayKetThuc);
			    
			    String trangThai = request.getParameter("trangThai");
			    obj.setTrangThai(trangThai);
			    
				obj.init();
				
		    	session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_MaSCLon.jsp");	    	
		    }
		    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    {
			    String ctyId = (String)session.getAttribute("congtyId");

			    obj.setCongTyId(ctyId);

		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.init();
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_MaSCLon.jsp");
		    } 
		}   
			
	}