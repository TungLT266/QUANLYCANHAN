package geso.traphaco.erp.servlets.tailieuphachethuoc;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tailieuphachethuoc.IErpTaiLieuPhaCheThuoc;
import geso.traphaco.erp.beans.tailieuphachethuoc.IErpTaiLieuPhaCheThuocList;
import geso.traphaco.erp.beans.tailieuphachethuoc.imp.ErpTaiLieuPhaCheThuoc;
import geso.traphaco.erp.beans.tailieuphachethuoc.imp.ErpTaiLieuPhaCheThuocList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpTaiLieuPhaCheThuocSvl
 */
@WebServlet("/ErpTaiLieuPhaCheThuocSvl")
public class ErpTaiLieuPhaCheThuocSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpTaiLieuPhaCheThuocSvl() {
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
	    
	    IErpTaiLieuPhaCheThuocList obj = new ErpTaiLieuPhaCheThuocList();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    if(action.trim().equals("delete")) {
	    	String Id = util.getId(querystring);
	    	obj.delete(Id);
	    }
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuoc.jsp";
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
	    	IErpTaiLieuPhaCheThuoc obj = new ErpTaiLieuPhaCheThuoc();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
		    obj.createRs();
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTailieuphachethuocNew.jsp");
	    } else if (action.equals("search")){
	    	IErpTaiLieuPhaCheThuocList obj = new ErpTaiLieuPhaCheThuocList();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
	    	String ma = util.antiSQLInspection(request.getParameter("ma"));
			if (ma == null)
				ma = "";
			obj.setMa(ma);
			
			String noidung = util.antiSQLInspection(request.getParameter("noidung"));
			if (noidung == null)
				noidung = "";
			obj.setNoidung(noidung);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			obj.init();
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuoc.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

}
