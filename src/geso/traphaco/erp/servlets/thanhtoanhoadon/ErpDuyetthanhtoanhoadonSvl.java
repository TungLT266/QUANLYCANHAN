package geso.traphaco.erp.servlets.thanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.IDuyetthanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Duyetthanhtoanhoadon;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchiList;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpDuyetthanhtoanhoadonSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ErpDuyetthanhtoanhoadonSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    HttpSession session = request.getSession();
		
	    String ServerletName = this.getServletName();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    util.setSearchToHM(userId, session, ServerletName, "");
	    

	    	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));

	    IDuyetthanhtoanhoadon ddmhBean = new Duyetthanhtoanhoadon();
   	    ddmhBean.setCtyId((String)session.getAttribute("congtyId"));
   	    ddmhBean.setUserId(userId);
   	    ddmhBean.setnppdangnhap(util.getIdNhapp(userId));
   	    ddmhBean.init();
		// Data is saved into session
		session.setAttribute("dtthdBean", ddmhBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetThanhToanHoaDon.jsp";
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
	    
	    String khId = util.antiSQLInspection(request.getParameter("khId"));
	    
	    String nvId = util.antiSQLInspection(request.getParameter("nvId"));

	    String Id = util.antiSQLInspection(request.getParameter("Id"));
	    
	    String LoaiId = util.antiSQLInspection(request.getParameter("LoaiId"));
	    
	    String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
	    
	    String maTTHD = util.antiSQLInspection(request.getParameter("maTTHD"));
	    
	    String nccId = util.antiSQLInspection(request.getParameter("nccId"));
	    
	    String loaict = util.antiSQLInspection(request.getParameter("loaict"));
	    
	    String action = util.antiSQLInspection(request.getParameter("action")); 

	    String ServerletName = this.getServletName();
	    
	    IDuyetthanhtoanhoadon ddmhBean = new Duyetthanhtoanhoadon();
	    ddmhBean.setCtyId((String)session.getAttribute("congtyId"));
	    ddmhBean.setUserId(userId);
	    ddmhBean.setnppdangnhap(util.getIdNhapp(userId));
	    
	    ddmhBean.setCtyId(ctyId);
   	    ddmhBean.setNccId(nccId);
   	    ddmhBean.setMaTTHD(maTTHD);
   	    ddmhBean.setNgayghinhan(ngayghinhan);
   	    ddmhBean.setNvId(nvId);
   	    ddmhBean.setKhId(khId);
   	    ddmhBean.setLoaiCT(loaict);
   	 
   	    ddmhBean.setRequest(request);
   	    if(action.equals("duyet")){
   	    	ddmhBean.Duyetmuahang(Id, LoaiId);
   	    	//ddmhBean.setNxtApprSplitting(Integer.parseInt(request.getParameter("stt")));
   	    }
   	    ddmhBean.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
   	    
   	    
   	    ddmhBean.init();
   	    if(action.equals("view") || action.equals("next") || action.equals("prev"))
   	    {
 		
	    	
   	    	ddmhBean.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
   	    	ddmhBean.init();
   	    	
   	    	String querySearch = GiuDieuKienLoc.createParams(ddmhBean);
   	    	util.setSearchToHM(userId, session,ServerletName, querySearch);
   	    	
   	    	ddmhBean.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	//session.setAttribute("obj", obj);
	    	
	    }
		// Data is saved into session
   	  
     
   	  //  ddmhBean.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		session.setAttribute("dtthdBean", ddmhBean);
//		session.setAttribute("userId", userId);
		String querySearch = GiuDieuKienLoc.createParams(ddmhBean);
	    util.setSearchToHM(userId, session,ServerletName, querySearch);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetThanhToanHoaDon.jsp";
   		response.sendRedirect(nextJSP);

	}

}
