package geso.traphaco.erp.servlets.phieuphache;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaChe;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaCheList;
import geso.traphaco.erp.beans.phieuphache.imp.ErpPhieuPhaChe;
import geso.traphaco.erp.beans.phieuphache.imp.ErpPhieuPhaCheList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpPhieuPhaCheSvl
 */
@WebServlet("/ErpPhieuPhaCheSvl")
public class ErpPhieuPhaCheSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpPhieuPhaCheSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId == null || userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpPhieuPhaCheList obj = new ErpPhieuPhaCheList();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    if(action.trim().equals("delete")) {
	    	String Id = util.getId(querystring);
	    	obj.delete(Id);
	    } else if(action.trim().equals("chot")){
	    	String Id = util.getId(querystring);
	    	obj.chot(Id);
	    } else if(action.trim().equals("bochot")){
	    	String Id = util.getId(querystring);
	    	obj.bochot(Id);
	    }
	    obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphache.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    if(action.equals("new")) {
	    	IErpPhieuPhaChe obj = new ErpPhieuPhaChe();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
		    obj.createRs();
		    obj.setNguoiphache((String) session.getAttribute("userTen"));
		    
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhieuphacheNew.jsp");
	    } else if (action.equals("search")){
	    	IErpPhieuPhaCheList obj = new ErpPhieuPhaCheList();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
	    	String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
			if (ngaychungtu == null)
				ngaychungtu = "";
			obj.setNgaychungtu(ngaychungtu);
			
			String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
			if (sanpham == null)
				sanpham = "";
			obj.setSanpham(sanpham);
			
			String nguoiphache = util.antiSQLInspection(request.getParameter("nguoiphache"));
			if (nguoiphache == null)
				nguoiphache = "";
			obj.setNguoiphache(nguoiphache);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			obj.init();
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphache.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

}
