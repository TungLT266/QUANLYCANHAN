package geso.traphaco.erp.servlets.loaimaukiemnghiem;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaimaukiemnghiem.IErpLoaiMauKiemNghiem;
import geso.traphaco.erp.beans.loaimaukiemnghiem.imp.ErpLoaiMauKiemNghiem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpLoaiMauKiemNghiemUpdateSvl
 */
@WebServlet("/ErpLoaiMauKiemNghiemUpdateSvl")
public class ErpLoaiMauKiemNghiemUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpLoaiMauKiemNghiemUpdateSvl() {
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
	    
	    IErpLoaiMauKiemNghiem obj = new ErpLoaiMauKiemNghiem();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String Id = util.getId(querystring);
    	obj.setId(Id);
    	
    	obj.initCapnhat();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaimaukiemnghiemNew.jsp";
	    
	    if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaimaukiemnghiemDisplay.jsp";
        }
	    
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
	    
	    IErpLoaiMauKiemNghiem obj = new ErpLoaiMauKiemNghiem();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null)
	    	id = "";
	    obj.setId(id);
	    
	    String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		obj.setMa(ma);
		
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null || trangthai.length() == 0)
			trangthai = "0";
		obj.setTrangthai(trangthai);
	    
	    if(action.equals("save")) {
	    	if(id.length() > 0){
	    		if(obj.update()){
	    			obj = new ErpLoaiMauKiemNghiem();
	    			
	    			obj.setUserId(userId);
	    		    obj.setCongtyId(ctyId);
	    		    obj.init();
	    		    
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaimaukiemnghiem.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaimaukiemnghiemNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	} else {
	    		if(obj.create()){
	    			obj = new ErpLoaiMauKiemNghiem();
	    			
	    			obj.setUserId(userId);
	    		    obj.setCongtyId(ctyId);
	    		    obj.init();
	    		    
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaimaukiemnghiem.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaimaukiemnghiemNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	}

}
