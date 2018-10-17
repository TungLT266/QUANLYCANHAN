package geso.traphaco.erp.servlets.phieuthanhtoan;
import geso.traphaco.erp.beans.phieuthanhtoan.IErpGanmachiphi;
import geso.traphaco.erp.beans.phieuthanhtoan.imp.ErpGanmachiphi;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Erp_GanmachiphiSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Erp_GanmachiphiSvl() {
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

	    IErpGanmachiphi ddmhBean = new ErpGanmachiphi();
   	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
   	    ddmhBean.setnppdangnhap(util.getIdNhapp(userId));
   	    ddmhBean.setUserId(userId);
   	    
   	    ddmhBean.init();
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGanMaChiPhi.jsp";
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
	    	
	    
	    IErpGanmachiphi ddmhBean = new ErpGanmachiphi();
	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));	    
	    ddmhBean.setUserId(userId);
	    ddmhBean.setCtyId(ctyId);
   	    ddmhBean.setDvthId(dvthId);
   	    ddmhBean.setNccId(nccId);
   	    ddmhBean.setMaDMH(maDMH);
   	    ddmhBean.setNgaymua(ngaymua);
   	    ddmhBean.setLspId(lspId);
	    ddmhBean.setnppdangnhap(util.getIdNhapp(userId));
	    
   	    ddmhBean.setRequest(request);
   	    if(action.equals("duyet")){
   	    	ddmhBean.DuyetganMCP(Id);
   	    }
   	    ddmhBean.init();
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);
//		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGanMaChiPhi.jsp";
   		response.sendRedirect(nextJSP);

	}

}
