package geso.traphaco.erp.servlets.KeHoachThanhToan;

import java.io.IOException;
import java.io.PrintWriter;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.ThoiHanThanhToan.IerpKeHoachThanhToan;
import geso.traphaco.erp.beans.ThoiHanThanhToan.Imp.erpKeHoachThanhToan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class erpKeHoachThanhToanSvl
 */
@WebServlet("/erpKeHoachThanhToanSvl")
public class erpKeHoachThanhToanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public erpKeHoachThanhToanSvl() {
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
	    this.out  = response.getWriter();
	    HttpSession session = request.getSession();	
	    Utility util = new Utility();
	    out = response.getWriter();
	    String queryString =request.getQueryString();
	    String userId = util.getUserId(queryString);
	    out.println(userId);
	    if(userId.length() ==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    IerpKeHoachThanhToan obj = new erpKeHoachThanhToan();
	    String congTyId = (String)session.getAttribute("congTyId");
	    obj.setUserId(userId);
	    obj.setCongTyId(congTyId);
	    obj.init();
	    obj.createNccRs();
	    session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachThanhToan.jsp";
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
	    this.out  = response.getWriter();	    
	    HttpSession session = request.getSession();	
	    out = response.getWriter();
	    Utility util = new Utility();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String congTyId = (String)session.getAttribute("congTyId");
	    IerpKeHoachThanhToan obj = new erpKeHoachThanhToan();
	    obj.setUserId(userId);
	    obj.setCongTyId(congTyId);
	    String tuNgay = request.getParameter("tuNgay");
	    if(tuNgay == null )
	    	tuNgay = "";
	    obj.setTuNgay(tuNgay);
	    String denNgay = request.getParameter("denNgay");
	    if(denNgay == null)
	    	denNgay = "";
	    obj.setDenNgay(denNgay);
	    String soPO = request.getParameter("soPO");
	    if(soPO == null)
	    	soPO = "";
	    obj.setSoPO(soPO);
	    String ncc = request.getParameter("ncc");
	    if(ncc == null)
	    	ncc = "";
	    obj.setNCC(ncc);
	    String action = request.getParameter("action");
	    if(action == null){
	    	action = "";
	    }else
	    	if(action.equals("search")){
	    		obj.init();
	    		obj.createNccRs();
	    		session.setAttribute("obj", obj);
	    	}
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachThanhToan.jsp";
	    response.sendRedirect(nextJSP);
	}

}
