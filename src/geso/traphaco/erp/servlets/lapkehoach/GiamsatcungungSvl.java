package geso.erp.servlets.lapkehoach;

import geso.erp.beans.giamsatcungung.IGiamsatcungung;
import geso.erp.beans.giamsatcungung.imp.Giamsatcungung;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GiamsatcungungSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public GiamsatcungungSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IGiamsatcungung gscuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	       
	    gscuBean = new Giamsatcungung();
	    
	    gscuBean.setUserId(userId);
	    
	    gscuBean.init();
        session.setAttribute("gscuBean", gscuBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamSatCungUng.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IGiamsatcungung gscuBean;

		Utility util = new Utility();
    	gscuBean = new Giamsatcungung();
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		gscuBean.setUserId(userId);	       
    			

		String ctyId = util.antiSQLInspection(request.getParameter("ctyId"));
		if (ctyId == null)
			ctyId = "";
		gscuBean.setCtyId(ctyId);

		String nhId = util.antiSQLInspection(request.getParameter("nhId"));
		if (nhId == null)
			nhId = "";
		gscuBean.setNhId(nhId);
		
		String clId = util.antiSQLInspection(request.getParameter("clId"));
		if (clId == null)
			clId = "";
		gscuBean.setClId(clId);
		
		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if (spId == null)
			spId = "";
		gscuBean.setSpId(spId);
		
		gscuBean.init();
		session.setAttribute("userId", userId);
		session.setAttribute("gscuBean", gscuBean);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamSatCungUng.jsp";
			
		response.sendRedirect(nextJSP);
	}
	
}
