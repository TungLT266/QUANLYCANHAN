package geso.traphaco.erp.servlets.yeucaukythuat;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucaukythuat.IErpYeuCauKyThuat;
import geso.traphaco.erp.beans.yeucaukythuat.IErpYeuCauKyThuatList;
import geso.traphaco.erp.beans.yeucaukythuat.imp.ErpYeuCauKyThuat;
import geso.traphaco.erp.beans.yeucaukythuat.imp.ErpYeuCauKyThuatList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpYeuCauKyThuatSvl
 */
@WebServlet("/ErpYeuCauKyThuatSvl")
public class ErpYeuCauKyThuatSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpYeuCauKyThuatSvl() {
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
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId == null || userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpYeuCauKyThuatList obj = new ErpYeuCauKyThuatList();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    if(action.trim().equals("delete")) {
	    	String Id = util.getId(querystring);
	    	obj.delete(Id);
	    }
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuat.jsp";
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
	    	IErpYeuCauKyThuat obj = new ErpYeuCauKyThuat();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
		    obj.createRs();
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpYeucaukythuatNew.jsp");
	    } else if (action.equals("search")){
	    	IErpYeuCauKyThuatList obj = new ErpYeuCauKyThuatList();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
	    	String ma = util.antiSQLInspection(request.getParameter("ma"));
			if (ma == null)
				ma = "";
			obj.setMa(ma);
			
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			if (ten == null)
				ten = "";
			obj.setTen(ten);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			obj.init();
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuat.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

}
