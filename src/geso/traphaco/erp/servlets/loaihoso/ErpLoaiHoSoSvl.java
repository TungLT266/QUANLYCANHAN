package geso.traphaco.erp.servlets.loaihoso;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSoList;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSoList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpLoaiHoSoSvl
 */
@WebServlet("/ErpLoaiHoSoSvl")
public class ErpLoaiHoSoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpLoaiHoSoSvl() {
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
	    
	    IErpLoaiHoSoList obj = new ErpLoaiHoSoList();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    if(action.trim().equals("delete")) {
	    	String Id = util.getId(querystring);
	    	obj.delete(Id);
	    }
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihoso.jsp";
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
	    	IErpLoaiHoSo obj = new ErpLoaiHoSo();
	    	
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLoaihosoNew.jsp");
	    } else if (action.equals("search")){
	    	IErpLoaiHoSoList obj = new ErpLoaiHoSoList();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
	    	String maloaihoso = util.antiSQLInspection(request.getParameter("maloaihoso"));
			if (maloaihoso == null)
				maloaihoso = "";
			obj.setMaLoaihoso(maloaihoso);
			
			String mabieumau = util.antiSQLInspection(request.getParameter("mabieumau"));
			if (mabieumau == null)
				mabieumau = "";
			obj.setMaBieumau(mabieumau);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			obj.setDiengiai(diengiai);
			
			String loaimauin = util.antiSQLInspection(request.getParameter("loaimauin"));
			if (loaimauin == null)
				loaimauin = "";
			obj.setLoaimauin(loaimauin);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			obj.init();
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihoso.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

}
