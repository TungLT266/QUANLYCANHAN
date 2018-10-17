package geso.traphaco.erp.servlets.baocaochiphi;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.baocaochiphi.IBaoCaoChiPhiList;
import geso.traphaco.erp.beans.baocaochiphi.imp.BaoCaoChiPhiList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaoCaoChiPhiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	   
	   public BaoCaoChiPhiSvl() {
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
		    
		    IBaoCaoChiPhiList obj = new BaoCaoChiPhiList();
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    
		    String congTyId = (String)session.getAttribute("congtyId");
		    if (congTyId == null)
		    	congTyId = "0";
		    obj.setCongTyId(congTyId);

		    String action = util.getAction(querystring);
		    out.println(action);
		    
			obj.init();
			//obj.DBClose();
			
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/BaoCaoChiPhi.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    
			HttpSession session = request.getSession();
			IBaoCaoChiPhiList obj = new BaoCaoChiPhiList();
			 
		    String userId = request.getParameter("userId");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = util.getAction(querystring);
		    	if (action == null)
		    		action = "";
		    }
		      
		    String congTyId = (String)session.getAttribute("congtyId");
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    obj.setCongTyId(congTyId);
		    
		    String nam = request.getParameter("nam");
		    if (nam == null)
		    	nam = "";
		    obj.setNam(nam);
		    
		    String thangBatDau = request.getParameter("thangBatDau");
		    if (thangBatDau == null)
		    	thangBatDau = "";
		    obj.setThangBatDau(thangBatDau);
		    
		    String thangKeThuc = request.getParameter("thangKetThuc");
		    if (thangKeThuc == null)
		    	thangKeThuc = "";
		    obj.setThangKeThuc(thangKeThuc);
		    
		    String[] phongBanIds = request.getParameterValues("phongBanId");
		    obj.setPhongBanIds(phongBanIds);
		    
		    String nhomChiPhiId = request.getParameter("nhomChiPhiId");
		    if(nhomChiPhiId == null) nhomChiPhiId = "";
		    obj.setNhomChiPhiId(nhomChiPhiId);
		    
		    System.out.println("exportExcel: " + action);
		    if (action.equals("exportExcel"))
		    {
		    	response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiPhi_" + geso.traphaco.center.util.Utility.getCurrentDate()+ ".xlsm");
				String fileName = getServletContext().getInitParameter("path") + "\\BaoCaoChiPhi.xlsm";
				System.out.println("fileName:" + fileName); 
		    	obj.init();
		    	obj.exportExcel(response.getOutputStream(), fileName);
		    }
		    else
		    {
			    if (action.equals("search"))
			    {
			    	obj.init();
			    }
			    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
			    {
			    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.init();
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			    }
			    response.sendRedirect("/TraphacoHYERP/pages/Erp/BaoCaoChiPhi.jsp");	
		    }			    
		    session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		
		    obj.DBClose();
		}   
	}