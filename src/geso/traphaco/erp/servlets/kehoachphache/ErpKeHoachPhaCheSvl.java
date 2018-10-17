package geso.traphaco.erp.servlets.kehoachphache;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaChe;
import geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaCheList;
import geso.traphaco.erp.beans.kehoachphache.imp.ErpKeHoachPhaChe;
import geso.traphaco.erp.beans.kehoachphache.imp.ErpKeHoachPhaCheList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpKeHoachPhaCheSvl
 */
@WebServlet("/ErpKeHoachPhaCheSvl")
public class ErpKeHoachPhaCheSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpKeHoachPhaCheSvl() {
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
	    
	    IErpKeHoachPhaCheList obj = new ErpKeHoachPhaCheList();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    if(action.trim().equals("delete")) {
	    	String Id = util.getId(querystring);
	    	obj.delete(Id);
	    }
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphache.jsp";
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
	    	IErpKeHoachPhaChe obj = new ErpKeHoachPhaChe();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
		    obj.createRs();
		    
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKehoachphacheNew.jsp");
	    } else if (action.equals("search")){
	    	IErpKeHoachPhaCheList obj = new ErpKeHoachPhaCheList();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);
	    	
	    	String ngaykehoach = util.antiSQLInspection(request.getParameter("ngaykehoach"));
			if (ngaykehoach == null)
				ngaykehoach = "";
			obj.setNgayKeHoach(ngaykehoach);
			
			String bophanthuchien = util.antiSQLInspection(request.getParameter("bophanthuchien"));
			if (bophanthuchien == null)
				bophanthuchien = "";
			obj.setBoPhanThucHien(bophanthuchien);
			
			String loai = util.antiSQLInspection(request.getParameter("loai"));
			if (loai == null)
				loai = "";
			obj.setLoai(loai);
			
			String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
			if (sanpham == null)
				sanpham = "";
			obj.setSanPham(sanpham);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			obj.init();
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphache.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

}
