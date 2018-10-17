package geso.traphaco.distributor.servlets.dontrahang;

import geso.traphaco.distributor.beans.dontrahang.IDontrahang;
import geso.traphaco.distributor.beans.dontrahang.imp.Dontrahang;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DontrahangDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DontrahangDisplaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		session.setMaxInactiveInterval(1200);
//		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
//	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    String action = util.getAction(querystring);
//	    out.println(id);
	   
	    IDontrahang dthBean = (IDontrahang) new Dontrahang();
	    dthBean.setUserId(userId);
	    
    	dthBean.setId(id);
	    dthBean.initUpdate();
	    nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangDisplay.jsp";
	    	    
		session.setAttribute("dthBean", dthBean);			
	
		response.sendRedirect(nextJSP);
		}

	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
