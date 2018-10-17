package geso.traphaco.erp.servlets.canhbaothieuhang;

import geso.traphaco.erp.beans.canhbaothieuhang.*;
import geso.traphaco.erp.beans.canhbaothieuhang.imp.*;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKehoachgiaohangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKehoachgiaohangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpKehoachgiaohang obj = new ErpKehoachgiaohang();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachGiaoHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpKehoachgiaohang obj = new ErpKehoachgiaohang();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String thangId = request.getParameter("thangId");
	    if (thangId == null)
	    	thangId = "";
	    obj.setThangId(thangId);
	    
	    String namId = request.getParameter("namId");
	    if (namId == null)
	    	namId = "";
	    obj.setNamId(namId);
	    
	    String tungay = request.getParameter("tungay");
	    if (tungay == null)
	    	tungay = "";
	    obj.setTuNgay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if (denngay == null)
	    	denngay = "";
	    obj.setDenNgay(denngay);
	    
	    String so = request.getParameter("soSO");
	    so = so==null?"":so;
	    obj.setSo(so);
	    
	    String maKH = request.getParameter("maKhachHang");
	    maKH = maKH==null?"":maKH;
	    obj.setMaKH(maKH);
	    
	    String spID = request.getParameter("maSanPham");
	    spID = spID==null?"":spID;
	    obj.setspIds(spID);
	    
	    String quyCach = request.getParameter("quycach");
	    quyCach = quyCach==null?"":quyCach;
	    obj.setQuyCach(quyCach);
	    
	    
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachGiaoHang.jsp";
		response.sendRedirect(nextJSP);
	}
}