package geso.traphaco.erp.servlets.SoTongHopChuTMotTaiKhoan;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoan.ISoTongHopChuTMotTaiKhoan;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoan.imp.SoTongHopChuTMotTaiKhoan;
import geso.traphaco.erp.beans.baocaosocaitaikhoan.IErp_BaoCaoSoCaiTaiKhoanList;
import geso.traphaco.erp.beans.baocaosocaitaikhoan.imp.Erp_BaoCaoSoCaiTaiKhoanList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SoTongHopChuTMotTaiKhoanSvl
 */
public class SoTongHopChuTMotTaiKhoanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoTongHopChuTMotTaiKhoanSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    ISoTongHopChuTMotTaiKhoan obj = new SoTongHopChuTMotTaiKhoan();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
		obj.initRs();
		
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/TraphacoHYERP/pages/Erp/SoTongHopChuTCuaMotTaiKhoan.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	      
	    String chiNhanh = (String)request.getParameter("chiNhanh");
	    if (chiNhanh == null)
	    	chiNhanh = "1";
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String tuNgay = request.getParameter("tuNgay");
	    if (tuNgay == null)
	    	tuNgay = "";
	    
	    String denNgay = request.getParameter("denNgay");
	    if (denNgay == null)
	    	denNgay = "";
	    
	    String taiKhoan = request.getParameter("taiKhoan");
	    if (taiKhoan == null)
	    	taiKhoan = "";
	    

		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "";
	    
	    ISoTongHopChuTMotTaiKhoan obj = new SoTongHopChuTMotTaiKhoan(tuNgay, denNgay, taiKhoan, chiNhanh, ctyId);
	    
	    if (action.equals("exportExcel"))
	    {
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=SoTongHopChuTCuaMotTaiKhoan_" + geso.traphaco.center.util.Utility.getCurrentDate()+ ".xlsm");
			String fileName = getServletContext().getInitParameter("path") + "\\SoTongHopChuTCuaMotTaiKhoan.xlsm";
	    	obj.initRs();
	    	obj.xuatExcel(response.getOutputStream(), fileName);
	    }
	    else if (action.equals("search"))
	    {
	    	obj.initRs();
	    	obj.initBC();
	    }
	   /* else if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.init();
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    } */
	    
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/SoTongHopChuTCuaMotTaiKhoan.jsp");	
		
	}

}
