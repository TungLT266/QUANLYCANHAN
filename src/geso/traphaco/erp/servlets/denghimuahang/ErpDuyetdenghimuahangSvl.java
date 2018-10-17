package geso.traphaco.erp.servlets.denghimuahang;
import geso.traphaco.erp.beans.denghimuahang.IErpDuyetdenghimuahang;
import geso.traphaco.erp.beans.denghimuahang.imp.ErpDuyetdenghimuahang;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpDuyetdenghimuahangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ErpDuyetdenghimuahangSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    HttpSession session = request.getSession();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));

	    IErpDuyetdenghimuahang ddmhBean = new ErpDuyetdenghimuahang();
   	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
   	    
   	    
   	    ddmhBean.setUserId(userId);
   	  
   	 
   	    ddmhBean.init();
   	   util.setSearchToHM(userId, session, this.getServletName(), "");
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHang.jsp";
   		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String ctyId = util.antiSQLInspection(request.getParameter("ctyId"));
	    
	    String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
	    
	    String lspId = util.antiSQLInspection(request.getParameter("lspId"));

	    String Id = util.antiSQLInspection(request.getParameter("Id"));
	    
	    String ngaymua = util.antiSQLInspection(request.getParameter("ngaymua"));
	    
	    String maDMH = util.antiSQLInspection(request.getParameter("maDMH"));
	    
	    String nccId = util.antiSQLInspection(request.getParameter("nccId"));
	    
	    String action = util.antiSQLInspection(request.getParameter("action")); 
	    
	    IErpDuyetdenghimuahang ddmhBean = new ErpDuyetdenghimuahang();
	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    ddmhBean.setUserId(userId);
	    ddmhBean.setCtyId(ctyId);
   	    ddmhBean.setDvthId(dvthId);
   	    ddmhBean.setNccId(nccId);
   	    ddmhBean.setMaDMH(maDMH);
   	    ddmhBean.setNgaymua(ngaymua);
   	    ddmhBean.setLspId(lspId);
   	    ddmhBean.setRequest(request);
   	    if(action.equals("duyet")){
   	    	
   	    	ddmhBean.Duyetdnmuahang(Id);
   	    	ddmhBean.init();
    	   
		// Data is saved into session
   	    	session.setAttribute("ddmhBean", ddmhBean);
//			session.setAttribute("userId", userId);
   	    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHang.jsp";
   	    	response.sendRedirect(nextJSP);
   	    }
   	    else{
   	    	ddmhBean.init();
   		   util.setSearchToHM(userId, session, this.getServletName(), "");
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);
//		session.setAttribute("userId", userId);
		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    GiuDieuKienLoc.setParamsToOject(ddmhBean, searchQuery);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHang.jsp";
   		response.sendRedirect(nextJSP);
   	 }
	}

}
