package geso.traphaco.distributor.servlets.nhaphang;

import geso.traphaco.distributor.beans.nhaphang.INhaphang;
import geso.traphaco.distributor.beans.nhaphang.imp.Nhaphang;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhaphangDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NhaphangDisplaySvl() {
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

		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	   
	    INhaphang nhaphang = (INhaphang) new Nhaphang();
	    nhaphang.setUserId(userId);
	    
    	nhaphang.setId(id);
    	nhaphang.init();
    	out.println(nhaphang.getMessage());
    	if(nhaphang.getNppId()==null){
    		nextJSP = "/TraphacoHYERP/pages/index.jsp";
    	}else
    	{
	    nextJSP = "/TraphacoHYERP/pages/Distributor/NhapHangDisplay.jsp";
    	}	    
		session.setAttribute("nhaphang", nhaphang);			
	
		response.sendRedirect(nextJSP);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
