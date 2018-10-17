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

public class ErpKehoachnhanNVLSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKehoachnhanNVLSvl() {
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
	    
	    IErpKehoachnhanNVL obj = new ErpKehoachnhanNVL();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNhanNVL.jsp";
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
	    
	    IErpKehoachnhanNVL obj = new ErpKehoachnhanNVL();
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
	    
	    String po = request.getParameter("soPO");
	    po = po==null?"":po;
	    obj.setPo(po);
	    
	    String ncc = request.getParameter("ncc");
	    ncc = ncc==null?"":ncc;
	    obj.setNcc(ncc);
	    
	    String spID = request.getParameter("maSanPham");
	    spID = spID==null?"":spID;
	    obj.setspIds(spID);
	    
	    String quyCach = request.getParameter("quycach");
	    quyCach = quyCach==null?"":quyCach;
	    obj.setQuyCach(quyCach);
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }else    
	    if ("search".equals(action)) {
			obj.init();
			session.setAttribute("obj", obj);
		}
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNhanNVL.jsp";
		response.sendRedirect(nextJSP);
	    
	    
	    
	}
	

}