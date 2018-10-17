package geso.traphaco.distributor.servlets.denghidathang;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.denghidathang.IDenghidathang;
import geso.traphaco.distributor.beans.denghidathang.IDenghidathangList;
import geso.traphaco.distributor.beans.denghidathang.imp.Denghidathang;
import geso.traphaco.distributor.beans.denghidathang.imp.DenghidathangList;
import geso.traphaco.distributor.db.sql.dbutils;

import java.sql.ResultSet;

 public class DenghidathangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
	public DenghidathangSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    IDenghidathangList obj;
	    	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);
			String querystring = request.getQueryString();
			String action = util.getAction(querystring);
	    
			String dndhId = util.getId(querystring);
	    
			if (action.equals("delete")){	   		  	    	
				Delete(dndhId);
			}
	    
			obj = new DenghidathangList();
	    
			obj.setUserId(userId);
			obj.createDndhlist(request,"1");
			session.setAttribute("obj", obj);
				
			String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiDatHang.jsp";
			response.sendRedirect(nextJSP);
		}

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    IDenghidathangList obj;
	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);
			IDenghidathang dndhBean = (IDenghidathang) new Denghidathang();
			userId = util.antiSQLInspection(request.getParameter("userId"));
	    
			String action = request.getParameter("action");
			if (action == null){
				action = "";
			}
	    
			out.print(action);
			if (action.equals("Tao moi")){	    		    	
				dndhBean.setUserId(userId);
				dndhBean.init0();
				session.setAttribute("dndhBean", dndhBean);
				out.println(dndhBean.getMessage());
	    	
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiDatHangParam.jsp";
				response.sendRedirect(nextJSP);    		

			}else if(action.equals("view") || action.equals("next") || action.equals("prev")){  
				
				obj = new DenghidathangList();
				obj.setUserId(userId);
				
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.createDndhlist(request, "2");
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiDatHang.jsp";	    	
				response.sendRedirect(nextJSP);
			}
			
			else{
				
				obj = new DenghidathangList();
				obj.setUserId(userId);

				obj.createDndhlist(request, "2");

				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiDatHang.jsp";	    	
				response.sendRedirect(nextJSP);
			}
			
		}
	}   	  	    
	
	private void Delete(String dndhId){
		dbutils db = new dbutils();
		try{
			ResultSet rs = db.get("select dadathang from denghidathang where pk_seq='" + dndhId + "'");
			rs.next();
			if(Double.valueOf(rs.getString("dadathang")).doubleValue() < 1000) {
				db.update("delete from denghidathang_sp where denghidathang_fk='" + dndhId + "'");
				db.update("delete from denghidathang where pk_seq ='" + dndhId + "'");
			}
			rs.close();
			db.shutDown();
		}catch(java.sql.SQLException e){}
	}
}